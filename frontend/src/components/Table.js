import React from "react";
import '../styles/Table.css';

function Table({data, onSortClick}){
    return(
        <div className="Table">
            <table>
                <thead>
                    <tr>
                        <th onClick={() => onSortClick('date')}>Date ↑↓</th>
                        <th onClick={() => onSortClick('consumptionamount')}>Daily Production in total ↑↓</th>
                        <th onClick={() => onSortClick('productionamount')}>Daily Consumption in total ↑↓</th>
                        <th onClick={() => onSortClick('hourlyprice')}>Daily price (average) ↑↓</th>
                        <th>Longest concecutive negative hours</th>
                    </tr>
                </thead>
                <tbody>
                    {data.map((row, index) =>(
                        <tr key={index}>
                            <td>{row.date}</td>
                            <td>{row.dailyConsumption}</td>
                            <td>{row.dailyProduction}</td>
                            <td>{row.dailyPrice}</td>
                            <td>{row.negativeHours}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default Table;