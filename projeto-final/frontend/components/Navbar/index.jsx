import React, { useState, useCallback, useEffect, useRef } from "react";
import { HamburgerButton, StyledNav, StyledNavLink, NavWrapper } from "./style";
import SearchInput from "../SearchInput";

const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false);
  const navRef = useRef(null);

  const handleClickOutside = (event) => {
    if (isOpen && !navRef.current.contains(event.target)) {
      setIsOpen(false);
    }
  };

  useEffect(() => {
    if (isOpen) {
      document.addEventListener("click", handleClickOutside);
    } else {
      document.removeEventListener("click", handleClickOutside);
    }

    return () => document.removeEventListener("click", handleClickOutside);
  }, [isOpen, handleClickOutside]);

  return (
    <>
      <NavWrapper open={isOpen}>
        <StyledNav open={isOpen} ref={navRef}>
          <StyledNavLink to="/" onClick={() => setIsOpen(!isOpen)}>
            CADASTRAR NOTÍCIAS
          </StyledNavLink>
          <StyledNavLink to="/" onClick={() => setIsOpen(!isOpen)}>
            EXIBIR NOTÍCIAS
          </StyledNavLink>
          <SearchInput setIsOpen={setIsOpen} />
        </StyledNav>
      </NavWrapper>

      <HamburgerButton open={isOpen} onClick={() => setIsOpen(!isOpen)}>
        <div />
        <div />
        <div />
      </HamburgerButton>
    </>
  );
};

export default Navbar;
