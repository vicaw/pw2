import styled, { css } from "styled-components";

export const NavWrapper = styled.div`
  @media screen and (max-width: 762px) {
    position: absolute;
    right: 0;
    top: 100%;
    flex-direction: column;
    margin: 0;
    filter: drop-shadow(0 2px 1px rgba(0, 0, 0, 0.2));
    pointer-events: ${({ open }) => (open ? "all" : "none")};
  }

  @media screen and (max-width: 426px) {
    width: 100%;
    padding: 0 0 1rem 0;
  }
`;

export const StyledNav = styled.nav`
  display: flex;
  font-family: "Questrial", sans-serif;
  grid-gap: 2rem;
  align-items: center;

  @media screen and (max-width: 762px) {
    background: rgb(222, 221, 222);
    flex-direction: column;
    padding: 0 1rem 1rem 1rem;
    align-items: flex-start;
    clip-path: polygon(0% 0%, 100% 0%, 100% 0%, 0% 0%);
    padding-top: 2px;
    transition: clip-path ease-in-out 0.3s;

    form:last-child {
      order: -1;
    }

    ${(props) =>
      props.open
        ? css`
            clip-path: polygon(0% 0%, 100% 0%, 100% 100%, 0% 100%);

            * {
              opacity: 1;
              transition: opacity 0.35s linear;
            }
          `
        : css`
            * {
              opacity: 0;
              transition: opacity 0.2s linear;
            }
          `}
  }

  @media screen and (max-width: 426px) {
    align-items: center;
  }
`;

export const StyledNavLink = styled.span`
  text-decoration: none;
  display: grid;
  place-items: center;
  color: #4d4c4d;
  cursor: pointer;
  overflow: hidden;
  font-size: 0.95rem;
  font-weight: bold;
  padding: 1em;
  border-radius: 4px;

  &:hover {
    background: #c5c3c5;
    transition: background 0.25s linear;
  }
`;

export const HamburgerButton = styled.button`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  width: 2rem;
  height: 2rem;
  background: transparent;
  border: none;
  cursor: pointer;
  padding: 0;
  z-index: 10;
  margin-left: auto;
  margin-right: 1.5rem;

  @media screen and (min-width: 762px) {
    display: none;
  }

  &:focus {
    outline: none;
  }

  div {
    width: 2rem;
    height: 3px;
    background: #4d4c4d;
    border-radius: 10px;
    transition: all 0.3s linear;
    position: relative;
    transform-origin: 1px;

    :first-child {
      transform: ${({ open }) => (open ? "rotate(45deg)" : "rotate(0)")};
    }

    :nth-child(2) {
      opacity: ${({ open }) => (open ? "0" : "1")};
      width: ${({ open }) => (open ? "0%" : "100%")};
    }

    :nth-child(3) {
      transform: ${({ open }) => (open ? "rotate(-45deg)" : "rotate(0)")};
    }
  }
`;
