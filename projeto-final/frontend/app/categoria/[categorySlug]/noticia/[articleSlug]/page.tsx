import type { GetStaticPaths, GetStaticProps, NextPage } from "next";
import { ParsedUrlQuery } from "querystring";

import { NoticiaType } from "../../../../../types/noticia";

import parse from "html-react-parser";
import moment from "moment";
import { notFound } from "next/navigation";
import CommentArea from "../../../../../components/comments/commentarea";

const fetchArticles = async (slug: string) => {
  const data: NoticiaType = await fetch(
    `http://localhost:8080/api/noticias/slugs/${slug}`,
    { cache: "force-cache" }
  )
    .then((res) => res.json())
    .catch(() => {
      null;
    });

  return data;
};

type PageProps = {
  params: {
    articleId: string;
    categorySlug: string;
    articleSlug: string;
  };
};

async function Noticia({
  params: { categorySlug, articleSlug, articleId },
}: PageProps) {
  try {
    const noticia: NoticiaType = await fetchArticles(articleSlug);

    return (
      <main className="max-w-4xl m-auto grid grid-cols-1 divide-y divide-gray-300 pt-32">
        <div>
          <h1 className="text-5xl font-bold tracking-tighter leading-[1.1]">
            {noticia.titulo}
          </h1>
          <h2 className="mt-6 text-xl text-gray-600 tracking-tight mr-6 leading-[1.5]">
            {noticia.subtitulo}
          </h2>
          <div className="mt-10 mb-10 text-gray-600">
            <p className="text-gray-700 font-bold">Por Autor</p>
            <p>
              {moment(noticia.createdAt).format("DD/MM/YYYY HH[h]mm ")}
              <span className="before:content-['\B7']">
                {noticia.createdAt == noticia.updatedAt
                  ? " Atualizado " + moment(noticia.updatedAt).fromNow()
                  : null}
              </span>
            </p>
          </div>
        </div>
        <hr />
        <article className="max-w-2xl m-auto mt-10">
          <img
            className="container col-span-4"
            src="https://random.imagecdn.app/672/378"
            alt=""
          />
          <div className="mt-10">{parse(noticia.body)}</div>
        </article>
        <hr />
        <CommentArea articleId={noticia.id} />
      </main>
    );
  } catch (error) {
    return notFound();
  }
}

export default Noticia;

export async function generateStaticParams() {
  const res = await fetch("http://localhost:8080/api/noticias/");
  const noticias: NoticiaType[] = await res.json();

  return noticias.map((noticia) => ({
    articleId: noticia.id,
    articleSlug: noticia.slug,
    categorySlug: noticia.category.slug,
  }));
}
