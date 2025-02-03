import React from "react";
import "../styles/Button.css"

/**
 * a reusable button component
 * 
 * @param {string} name - label for the button
 * @param {function} onClick - function to execute by clicking
 * @param {boolean} disabled - determine if button is clickable or not
 * 
 * @returns {JSX.Element}
 */

function Button({name, onClick, disabled}){
    return(
        <div className="Button">
            <button onClick={onClick} disabled={disabled}>
                {name}
            </button>
        </div>
    );
}

export default Button;