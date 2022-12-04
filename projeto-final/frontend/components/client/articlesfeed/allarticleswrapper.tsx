"use client";
import React, { Suspense, useEffect, useRef, useState } from "react";
import { NoticiaCardType } from "../../../types/noticia";
import NoticiaCard from "./articlecard";
import ArticleCardSkeleton from "./articlecardskeleton";
import ArticlesFeed from "./articlesfeed";

type Response = {
  hasMore: boolean;
  articles: NoticiaCardType[];
};

const fetchFeed = async (page: number, category?: string) => {
  const url = category
    ? `http://localhost:8080/api/noticias/feedinfo?category=${category}&page=${page}&pagesize=10`
    : `http://localhost:8080/api/noticias/feedinfo?page=${page}&pagesize=10`;

  const feedinfo: Response = await fetch(url)
    .then((res) => res.json())
    .catch(() => {
      [{}];
    });

  console.log(feedinfo);
  return feedinfo;
};

interface Props {
  category?: string;
}
function AllArticlesWrapper({ category }: Props) {
  const [articles, setArticles] = useState<NoticiaCardType[]>([]);
  const [isLoading, setIsLoading] = useState(false);

  const hasMore = useRef(false);
  const nextPage = useRef(0);

  const getMore = async () => {
    setIsLoading(true);
    await fetchFeed(nextPage.current, category).then((data) => {
      setArticles((prev) => [...prev, ...data.articles]);
      hasMore.current = data.hasMore;
      nextPage.current++;
    });

    setIsLoading(false);
  };

  useEffect(() => {
    getMore();
  }, []);

  return (
    <div className="grid grid-cols-4">
      <div className="flex flex-col divide-y col-span-3 gap-8">
        <ArticlesFeed articles={articles} />

        {hasMore.current ? (
          <button
            type="submit"
            disabled={isLoading}
            onClick={getMore}
            className="group relative shadow-md w-full flex justify-center p-5 border border-transparent font-bold rounded text-white tracking-tight bg-red-600 hover:bg-red-700 disabled:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500"
          >
            {!isLoading ? (
              "VEJA MAIS"
            ) : (
              <svg
                aria-hidden="true"
                className="w-6 h-6 text-gray-300 animate-spin dark:text-gray-600 fill-red-700"
                viewBox="0 0 100 101"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
                  fill="currentColor"
                />
                <path
                  d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
                  fill="currentFill"
                />
              </svg>
            )}
          </button>
        ) : null}
      </div>
    </div>
  );
}

export default AllArticlesWrapper;
