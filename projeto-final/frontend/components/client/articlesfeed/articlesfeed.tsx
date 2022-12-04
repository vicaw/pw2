import React, { Suspense } from "react";
import { NoticiaCardType } from "../../../types/noticia";
import NoticiaCard from "./articlecard";

interface Props {
  articles: NoticiaCardType[];
}

function ArticlesFeed({ articles }: Props) {
  return (
    <>
      {articles.map((article) => (
        <NoticiaCard key={article.titulo_feed} {...article} />
      ))}
    </>
  );
}

export default ArticlesFeed;
