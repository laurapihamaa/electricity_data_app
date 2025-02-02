import React from "react";

function SearchBar({searchQuery, setSearchQuery}){
    return(
        <div>
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