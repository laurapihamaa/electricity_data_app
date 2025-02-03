import React from "react";
import "../styles/Searchbar.css"

/**
 * a reusable component to create a search bar
 * 
 * @param {searchQuery} string - the query the user inputs
 * @param {setSearchQuery} string - send the search query to backend
 * @param {type} string - the input type
 * @param {placeholder} string - default display text
 * @param {name} string - name of the component
 * @returns {JSX.Element}
 */

function SearchBar({searchQuery, setSearchQuery, type, placeholder, name}){
    return(
        <div className="Searchbar">
            <form>
                <input
                    value={searchQuery}
                    onInput={e=> setSearchQuery(e.target.value)}
                    type={type}
                    placeholder={placeholder}
                    name={name}/>
            </form>
        </div>
    );
}

export default SearchBar;