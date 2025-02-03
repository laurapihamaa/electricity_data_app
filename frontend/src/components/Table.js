import React from "react";
import '../styles/Table.css';

/**
 * 
 * a not so very reusable component of a table
 * 
 * @param {data} map - the data to display on the table 
 * @param {onSortClick} function - function to sort the columns when clicking the header
 * 
 * @returns {JSX.Element}
 */

function Table({data, onSortClick}){
    return(
        <div className="Table">
            <table>
                <thead>
                    <tr>
                        <th onClick={() => onSortClick('date')}>Date ↑↓</th>
                        <th onClick={() => onSortClick('productionamount')}>Daily Production in total ↑↓</th>
                        <th onClick={() => onSortClick('consumptionamount')}>Daily Consumption in total ↑↓</th>
                        <th onClick={() => onSortClick('hourlyprice')}>Daily price (average) ↑↓</th>
                        <th onClick={() => onSortClick('negativehours')}>Longest concecutive negative hours ↑↓</th>
                    </tr>
                </thead>
                <tbody>
                    {data.map((row, index) =>(
                        <tr key={index}>
                            <td>{row.date}</td>
                            <td>{row.dailyProduction}</td>
                            <td>{row.dailyConsumption}</td>
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