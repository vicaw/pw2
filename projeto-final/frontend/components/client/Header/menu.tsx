"use client";
import Link from "next/link";
import React, {
  useCallback,
  useEffect,
  useRef,
  useState,
  use,
  useContext,
} from "react";
import { ChevronRightIcon, ChevronLeftIcon } from "@heroicons/react/20/solid";
import { InferGetStaticPropsType } from "next";
import { CategoryType } from "../../../types/category";
import { AuthContext } from "../../../contexts/AuthContext";

//???????????????????????

interface Props {
  categories: CategoryType[];
}

function Menu({ categories }: Props) {
  const [layer, setLayer] = useState(0);
  const [isOpen, setIsOpen] = useState(false);
  const { signOut, isAuthenticated, user } = useContext(AuthContext);

  const navRef = useRef<HTMLDivElement>(null);

  const handleClickOutside = (event: MouseEvent) => {
    if (isOpen && !navRef?.current?.contains(event.target as HTMLDivElement)) {
      setIsOpen(false);
      setLayer(0);
    }
  };

  useEffect(() => {
    if (isOpen) {
      document.addEventListener("click", handleClickOutside);
    } else {
      document.removeEventListener("click", handleClickOutside);
    }

    return () => document.removeEventListener("click", handleClickOutside);
  }, [isOpen]);

  const nextLayer = () => {
    setLayer(layer + 1);
  };

  const prevLayer = () => {
    if (layer == 0) {
      return;
    }
    setLayer(layer - 1);
  };

  return (
    <>
      <div
        className={
          (isOpen ? "opacity-70" : "opacity-0") +
          " h-screen w-screen right-0 top-0 transition-all bg-black fixed pointer-events-none"
        }
      />

      <menu ref={navRef}>
        <div className="h-full font-['Open_Sans'] text-sm font-bold text-white cursor-pointer select-none">
          <div
            className="h-full items-center flex"
            onClick={() => setIsOpen(!isOpen)}
          >
            <div
              className="w-[20px] inline-block bg-white h-[2px] absolute z-[-1]
                       before:w-[20px] before:inline-block before:bg-white before:h-[2px] before:absolute before:top-[-6px]
                       after:w-[20px] after:inline-block after:bg-white after:h-[2px] after:absolute after:top-[6px]"
            />
            <span className="ml-[27px]">MENU</span>
          </div>
        </div>

        <nav
          className={
            (isOpen ? "left-0" : "left-[-272px]") +
            " fixed transition-all top-0 w-[272px] h-screen bg-yellow-300 overflow-clip"
          }
        >
          <div
            className={
              (layer == 0 ? "current " : "") +
              "transition-all select-none h-screen bg-[#fafafa] p-5 absolute w-[272px] left-[-100%] [&.current]:left-0 flex flex-col justify-between"
            }
          >
            <div
              onClick={nextLayer}
              className="flex hover:text-red-700 text-gray-500 h-fit w-full items-center justify-between cursor-pointer "
            >
              <span className="tracking-[-1.2px] pb-[3px] font-light text-xl flex justify-center transition-all cursor-pointer">
                categorias
              </span>
              <ChevronRightIcon className="h-5 w-5 text-red-700" />
            </div>

            <div>
              {isAuthenticated ? (
                <div>
                  <div className="flex">
                    <svg
                      width="20"
                      height="20"
                      viewBox="0 0 20 20"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <path
                        d="M10 0C4.48 0 0 4.48 0 10s4.48 10 10 10 10-4.48 10-10S15.52 0 10 0zm0 3c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm0 14.2c-2.5 0-4.71-1.28-6-3.22.03-1.99 4-3.08 6-3.08 1.99 0 5.97 1.09 6 3.08-1.29 1.94-3.5 3.22-6 3.22z"
                        fill-rule="nonzero"
                        fill="#333"
                      ></path>
                    </svg>
                    <span>{user?.name}</span>
                  </div>
                  <div className="flex">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      width="18"
                      height="18"
                      viewBox="0 0 18 18"
                    >
                      <g fill="none" fill-rule="evenodd">
                        <path d="M-2-2h20v20H-2z"></path>
                        <path
                          fill="#999"
                          fill-rule="nonzero"
                          d="M6.408 10.992l1.175 1.175L11.75 8 7.583 3.833 6.408 5.008l2.15 2.159H.5v1.666h8.058l-2.15 2.159zM13.833.5H2.167C1.242.5.5 1.25.5 2.167V5.5h1.667V2.167h11.666v11.666H2.167V10.5H.5v3.333c0 .917.742 1.667 1.667 1.667h11.666c.917 0 1.667-.75 1.667-1.667V2.167C15.5 1.25 14.75.5 13.833.5z"
                        ></path>
                      </g>
                    </svg>
                    <button onClick={signOut}>Sign Out</button>
                  </div>
                </div>
              ) : (
                <div className="flex">
                  <svg
                    width="20"
                    height="20"
                    viewBox="0 0 20 20"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      d="M10 0C4.48 0 0 4.48 0 10s4.48 10 10 10 10-4.48 10-10S15.52 0 10 0zm0 3c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm0 14.2c-2.5 0-4.71-1.28-6-3.22.03-1.99 4-3.08 6-3.08 1.99 0 5.97 1.09 6 3.08-1.29 1.94-3.5 3.22-6 3.22z"
                      fill-rule="nonzero"
                      fill="#999"
                    ></path>
                  </svg>
                  <Link href={"/login"} onClick={() => setIsOpen(!isOpen)}>
                    Faça Login
                  </Link>
                </div>
              )}
            </div>
          </div>

          <div
            className={
              (layer == 1 ? "current " : "") +
              "transition-all select-none h-screen bg-[#fafafa] divide-y absolute w-[272px]  text-white left-[100%] [&.current]:left-0"
            }
          >
            <div
              onClick={prevLayer}
              className="flex hover:text-red-700 text-black gap-1 pb-5 pt-5 h-fit w-full items-center p-5 cursor-pointer bg-white "
            >
              <ChevronLeftIcon className="h-6 w-6 text-red-700" />
              <span className=" font-bold  tracking-[-1.2px] text-xl pb-[2px] justify-center transition-all cursor-pointer">
                menu j1
              </span>
            </div>
            <div className="px-5">
              <span className="text-gray-700 pt-8 pb-3 font-bold tracking-[-1.2px] block">
                categorias
              </span>

              {categories.map((category) => (
                <Link
                  href={`/categoria/${category.slug}`}
                  key={category.name}
                  onClick={() => setIsOpen(false)}
                  className="text-gray-500 hover:text-red-700 tracking-[-1.2px] font-light text-xl block py-1 justify-center transition-all cursor-pointer"
                >
                  {category.name}
                </Link>
              ))}
            </div>
          </div>
        </nav>
      </menu>
    </>
  );
}

export default Menu;
