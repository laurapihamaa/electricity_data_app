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

  /**
   * 
   * A reusable component representing the charts
   * 
   * @param {data} map - a map containing data for the charts
   * @param {title} string - a title for the chart
   * @param {color} string - a line color 
   * @param {xValues} string - xValues to pick from dataset
   * @param {yValues} string - yValues to pick from dataset
   * @returns {JSX.Element}
   */

  function Chart({data, title, color, xValues, yValues}){

    const [dataSet, setDataSet]=useState(null);

    /**
     * a hook creating a dataset to draw the chart from
     * triggers whenever the data changes
     */

    useEffect(() => {
        setDataSet({
            labels: data.map(item => item[xValues]),
            datasets:[
                {
                    label: title,
                    data: data.map(item=>item[yValues]),
                    borderColor: color
                }
            ]
        });
    }, [data])

    return(
            <div className="graph">
            <h2>{title}</h2>
            {dataSet && <Line data={dataSet}/>}
            </div>
    )
  }

  export default Chart;