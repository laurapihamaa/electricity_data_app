import './styles/App.css';
import Table from './components/Table';
import { useEffect, useState } from 'react';
import SearchBar from './components/SearchBar';
import Chart from './components/Chart';
import Button from './components/Button';

function App() {

  const [dailyToatals, setDailyTotals] = useState([]);
  const [page, setPage] = useState(1);
  const [allPages, setAllPages] = useState();
  const [sortBy, setSortBy] = useState('date');
  const [sortOrder, setSortOrder] = useState('asc');
  const [searchQuery, setSearchQuery] = useState("");

  const fetchData = (page) => {

    fetch(`http://localhost:8080/dailyTotals?page=${page}&size=10&sortBy=${sortBy}&sortOrder=${sortOrder}&search=${searchQuery}`)
      .then(response => response.json())
      .then(data => {
        setDailyTotals(data.content);
        console.log(data);
        setAllPages(data.totalPages)
      })
      .catch(error =>
        alert("Problem loading data. Please try again")
      )
  };

  useEffect(()=>{
    fetchData(page);
  }, [page, sortBy, sortOrder, searchQuery]);

  const handleSorting = (column) => {
    sortOrder === 'asc' ? setSortOrder('desc') : setSortOrder('asc');
    setSortBy(column);
  }

  return (
    <div className="App">
      <header className="App-header">
        Electricity Data
      </header>
      <SearchBar searchQuery={searchQuery} setSearchQuery={setSearchQuery}/>
      <Table data={dailyToatals} onSortClick={handleSorting}/>
      <div>
      <Button direction="Previous" 
              onClick={()=>setPage(page-1)}
              disabled={page==1}/>
      <span> Page {page} of {allPages} </span>
      <Button direction="Next" 
              onClick={() => setPage(page+1)} 
              disabled={page===allPages}/>
      </div>
      <Chart data={dailyToatals}/>
    </div>
  );
}

export default App;
