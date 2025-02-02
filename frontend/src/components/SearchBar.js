import React from "react";
import "../styles/Searchbar.css"

function SearchBar({searchQuery, setSearchQuery}){
    return(
        <div className="Searchbar">
            <form>
                <input
                    value={searchQuery}
                    onInput={e=> setSearchQuery(e.target.value)}
                    type="text"
                    placeholder="Search for something"
                    name="s"/>
            </form>
        </div>
    );
}

export default SearchBar;