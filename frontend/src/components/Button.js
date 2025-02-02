import React from "react";
import "../styles/Button.css"

function Button({direction, onClick, disabled}){
    return(
        <div className="Button">
            <button onClick={onClick} disabled={disabled}>
                {direction}
            </button>
        </div>
    );
}

export default Button;