import React from "react";

function Table({data, onSortClick}){
    return(
        <div>
            <table>
                <thead>
                    <tr>
                        <th onClick={() => onSortClick('date')}>Date ↑↓</th>
                        <th onClick={() => onSortClick('consumptionamount')}>Daily consumpiton in total ↑↓</th>
                        <th onClick={() => onSortClick('productionamount')}>Daily Production in total ↑↓</th>
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