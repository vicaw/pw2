"use client";

import moment from "moment";
import { useContext, useEffect, useRef, useState } from "react";
import { AuthContext } from "../../../contexts/AuthContext";
import { UserType } from "../../../types";
import { NoticiaCardType, NoticiaType } from "../../../types/noticia";
import ManageArticlesListItem from "./managearticleslistitem";

const columns = [
  { id: 1, title: "Título", accessor: "titulo" },
  { id: 2, title: "Última Edição", accessor: "updatedAt" },
  { id: 3, title: "Publicação", accessor: "createdAt" },
];

type Response = {
  hasMore: boolean;
  articles: NoticiaCardType[];
};

const fetchFeed = async (page: number, user: UserType) => {
  const url =
    user.role === "EDITOR"
      ? `http://localhost:8080/api/articles?authorId=${user.id}`
      : `http://localhost:8080/api/articles`;

  const feedinfo: NoticiaType[] = await fetch(url)
    .then((res) => res.json())
    .catch(() => {
      [{}];
    });

  console.log(feedinfo);
  return feedinfo;
};

export default function ManageArticlesList() {
  const [articles, setArticles] = useState<NoticiaType[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const { user } = useContext(AuthContext);

  const hasMore = useRef(false);
  const nextPage = useRef(0);

  const getMore = async () => {
    if (!user) return;

    setIsLoading(true);
    await fetchFeed(nextPage.current, user).then((data) => {
      setArticles((prev) => [...prev, ...data]);
      hasMore.current = false;
      nextPage.current++;
    });

    setIsLoading(false);
  };

  useEffect(() => {
    getMore();
  }, []);

  return (
    <section className="containter bg-white p-4 rounded h-full">
      <table className="containter w-full">
        <thead>
          <tr>
            {columns.map((col) => (
              <th key={col.id}>{col.title}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {articles.map((article) => (
            <ManageArticlesListItem key={article.id} article={article} />
          ))}
        </tbody>
      </table>
    </section>
  );
}

{
  /* <section className="containter bg-white p-4 rounded">
<div className="flex">
  <div>
    Pelé posta foto ao lado de Zito durante a Copa de 1958 e diz que vai
    assistir do hospital ao jogo do Brasil contra a Coreia do Sul
  </div>
  <div>São Paulo</div>
  <div>05/12/2022</div>
</div>
</section> */
}
