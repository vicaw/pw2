import React from "react";
import { NoticiaCardType } from "../types/noticia";
import NoticiaCard from "../components/server/ArticleFeed/articlecard";
import Header from "../components/client/header/header";
import { CategoryType } from "../types/category";
import { AuthProvider } from "../contexts/AuthContext";
import { api } from "../services/api";
import { cookies } from "next/headers";

const fetchFeed = async () => {
  // const nextCookies = cookies();
  // const token = nextCookies.get("nextauth.token");

  const feedinfo: NoticiaCardType[] = await fetch(
    "http://localhost:8080/api/noticias/feedinfo",
    {
      cache: "no-store",
      // headers: {
      //  Authorization: `Bearer ${token?.value}`,
      // },
    }
  )
    .then((res) => res.json())
    .catch(() => {
      [{}];
    });

  return feedinfo;
};

async function Home() {
  const noticias = await fetchFeed();

  return (
    <>
      <main className="container mx-auto px-10 mb-8 pt-28">
        <div className="grid grid-cols-4">
          <div className="flex flex-col divide-y col-span-3 gap-8">
            {noticias
              ? noticias.map((noticia) => (
                  <NoticiaCard key={noticia.titulo_feed} {...noticia} />
                ))
              : null}
            <span className="bg-red-700 pt-5 pb-5 text-white font-bold tracking-tight rounded text-center shadow-md cursor-pointer">
              VEJA MAIS
            </span>
          </div>

          <div className="">
            <p></p>
          </div>
        </div>
      </main>
    </>
  );
}

export default Home;
