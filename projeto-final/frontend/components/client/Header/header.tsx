"use client";

import { GetServerSideProps } from "next";
import Link from "next/link";
import React, { useEffect } from "react";
import { useScrollPosition } from "../../../hooks/useScrollPosition";
import { CategoryType } from "../../../types/category";
import Menu from "./menu";
import { usePathname } from "next/navigation";

function classNames(...classes: String[]) {
  return classes.filter(Boolean).join(" ");
}

interface Props {
  categories: CategoryType[];
}

function Header({ categories }: Props) {
  const scrollPosition = useScrollPosition();

  return (
    <header
      className={classNames(
        scrollPosition > 0 ? "" : "pb-2 pt-2",
        "flex bg-red-700 items-center  place-content-around fixed top-0 right-0 left-0 transition-spacing duration-200"
      )}
    >
      <Menu categories={categories} />
      <Link href="/" className="text-[2.5rem] font-bold text-white">
        j1
      </Link>
      <input name="pesquisa" type="text" />
    </header>
  );
}

export default Header;
