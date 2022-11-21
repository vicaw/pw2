import React, { useState, useRef } from "react";
import styled from "styled-components";
import { ReactComponent as LupaSVG } from "../../assets/img/lupa.svg";

const SyledForm = styled.form`
  background: white;
  display: flex;
  padding: 0.1rem 0.5rem 0.1rem 1rem;
  border-radius: 15px;

  :focus-within,
  :hover {
    box-shadow: 0px 0px 0px 2px #9e9e9e;
  }

  input,
  button {
    all: unset;
  }

  button {
    display: grid;
    place-items: center;
    color: #4d4c4d;
  }

  svg {
    fill: #4d4c4d;
  }
`;

const SearchInput = ({ setIsOpen }) => {
  const [input, setInput] = useState("");

  return (
    <SyledForm>
      <input
        name="pesquisa"
        type="text"
        value={input}
        onChange={(event) => {
          setInput(event.target.value);
        }}
      />
    </SyledForm>
  );
};

export default SearchInput;
