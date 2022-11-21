"use client";
import Link from "next/link";
import React, { useCallback, useEffect, useRef, useState, use } from "react";
import { ChevronRightIcon, ChevronLeftIcon } from "@heroicons/react/20/solid";
import { InferGetStaticPropsType } from "next";
import { CategoryType } from "../../../types/category";

//import { ChevronLeftIcon } from "@heroicons/react/24/solid";

//???????????????????????

interface Props {
  categories: CategoryType[];
}

function Menu({ categories }: Props) {
  const [layer, setLayer] = useState(0);
  const [isOpen, setIsOpen] = useState(false);

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
      <div ref={navRef}>
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

        <div
          className={
            (isOpen ? "left-0" : "left-[-272px]") +
            " fixed transition-all top-0 w-[272px] h-screen bg-yellow-300 overflow-clip"
          }
        >
          <div
            className={
              (layer == 0 ? "current " : "") +
              "transition-all select-none h-screen bg-[#fafafa] p-5 absolute w-[272px] left-[-100%] [&.current]:left-0 flex"
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
                  href={`/${category.slug}`}
                  key={category.name}
                  onClick={() => setIsOpen(false)}
                  className="text-gray-500 hover:text-red-700 tracking-[-1.2px] font-light text-xl block py-1 justify-center transition-all cursor-pointer"
                >
                  {category.name}
                </Link>
              ))}
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default Menu;
