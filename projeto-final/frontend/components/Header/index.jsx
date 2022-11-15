import React from "react";
import styled from "styled-components";
import Navbar from "../Navbar";

const SyledHeader = styled.header`
  background: rgb(222, 221, 222);
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 0.8em 0 0.8em 0;
  box-shadow: 0px 0px 2px 0px #00000036;
  transform-style: preserve-3d;

  @media screen and (max-width: 762px) {
    display: grid;
    grid-template-columns: 1fr auto 1fr;
    grid-column-gap: 5px;
    justify-items: center;
  }
`;

const Logo = styled.a`
  font-family: "Quattrocento Sans", sans-serif;
  font-size: 1.5rem;
  color: rgb(77, 76, 77);
  font-weight: bold;
  cursor: default;

  @media screen and (max-width: 762px) {
    grid-column-start: 2;
    z-index: 10;
  }
`;

const Header = () => {
  return (
    <SyledHeader>
      <Logo>LOGO</Logo>
      <Navbar />
    </SyledHeader>
  );
};

export default Header;
