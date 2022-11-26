import React, { useState, useRef } from "react";
import { MagnifyingGlassIcon } from "@heroicons/react/20/solid";

function SearchBar() {
  const [input, setInput] = useState("");

  return (
    <fieldset className="flex bg-red-800 py-1">
      <MagnifyingGlassIcon className="h-5 w-5 t-[-3px] text-white " />
      <input
        className="bg-red-800 placeholder:text-white font-bold w-[129px] h-[25px]"
        name="pesquisa"
        type="text"
        placeholder="BUSCAR"
        value={input}
        onChange={(event) => {
          setInput(event.target.value);
        }}
      />
    </fieldset>
  );
}

export default SearchBar;
