import React, { useEffect, useState } from "react";
import "../styles/Graph.css"
import { Line } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from 'chart.js';

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
  );

  function Chart({data}){

    const [consumptionData, setConsumptionData]=useState(null);
    const [productionnData, setProductionData]=useState(null);
    const [priceData, setPriceData]=useState(null);

    useEffect(() => {
        setConsumptionData({
            labels: data.map(item => item.date),
            datasets:[
                {
                    label: "Daily production",
                    data: data.map(item=>item.dailyConsumption),
                    borderColor: 'red'
                }
            ]
        });
        setProductionData({
            labels: data.map(item => item.date),
            datasets:[
                {
                    label: "Daily consumption",
                    data: data.map(item=>item.dailyProduction),
                    borderColor: 'blue'
                    
                }
            ]
        });
        setPriceData({
            labels: data.map(item => item.date),
            datasets:[
                {
                    label: "Daily price",
                    data: data.map(item=>item.dailyPrice),
                    borderColor: 'green'
                }
            ]
        })
    }, [data])

    return(
        <div className="Graph-container">
            <div className="graph">
            <h2>Electricity Production</h2>
            {consumptionData && <Line data={consumptionData}/>}
            </div>
            <div className="graph">
            <h2>Electricity Consumption</h2>
            {productionnData && <Line data={productionnData}/>}
            </div>
            <div className="graph">
            <h2>Daily price (average)</h2>
            {priceData && <Line data={priceData}/>}
            </div>
        </div>
    )
  }

  export default Chart;