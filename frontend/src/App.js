import './styles/App.css';
import Table from './components/Table';
import { useEffect, useState } from 'react';
import SearchBar from './components/SearchBar';
import Chart from './components/Chart';
import Button from './components/Button';

/**
 * 
 * Main component of the app
 * Responsible for fecthing and displaying the electricity data.
 * 
 */

function App() {

  const [dailyToatals, setDailyTotals] = useState([]);
  const [page, setPage] = useState(1);
  const [allPages, setAllPages] = useState();
  const [sortBy, setSortBy] = useState('date');
  const [sortOrder, setSortOrder] = useState('asc');
  const [searchQuery, setSearchQuery] = useState("");


  /**
   * function for fetching the data of the daily totals
   * uses provided pagination, sorting and search query
   * 
   * @param {number} page - the page number to fetch
   */

  const fetchData = (page) => {

    fetch(`http://localhost:8080/dailyTotals?page=${page}&size=10&sortBy=${sortBy}&sortOrder=${sortOrder}&search=${searchQuery}`)
      .then(response => response.json())
      .then(data => {
        setDailyTotals(data.content);
        setAllPages(data.totalPages)
      })
      .catch(error =>
        alert("Problem loading data. Please try again")
      )
  };

  /**
   * Hook to trigger fetch data function
   * Triggers the function if the page, sorting values or search query changes
   */

  useEffect(()=>{
    fetchData(page);
  }, [page, sortBy, sortOrder, searchQuery]);


  /**
   * function for handling the sorting of the table with the specified column
   * sort order is toggeles either ascending or descending
   * 
   * @param {string} column - the column to sort by
   */

  const handleSorting = (column) => {
    sortOrder === 'asc' ? setSortOrder('desc') : setSortOrder('asc');
    setSortBy(column);
  }

  return (
    <div className="App">
      <header className="App-header">
        Electricity Data
      </header>
      <SearchBar 
              searchQuery={searchQuery} 
              setSearchQuery={setSearchQuery}
              type="text"
              placeholder="Search for something"
              name="s"
              />
      <Table data={dailyToatals} onSortClick={handleSorting}/>
      <div>
      <Button name="Previous" 
              onClick={()=>setPage(page-1)}
              disabled={page===1}/>
      <span> Page {page} of {allPages} </span>
      <Button name="Next" 
              onClick={() => setPage(page+1)} 
              disabled={page===allPages}/>
      </div>
      <div className='Graph-container'>
      <Chart data={dailyToatals}
            title="Daily Production"
            color="red"
            xValues="date"
            yValues="dailyConsumption"/>
      <Chart data={dailyToatals}
            title="Daily Consumption"
            color="blue"
            xValues="date"
            yValues="dailyProduction"/>
      <Chart data={dailyToatals}
            title="Price per day (average)"
            color="green"
            xValues="date"
            yValues="dailyPrice"/>
      </div>
    </div>
  );
}

export default App;
