import { NoticiaCardType, NoticiaType } from "../../types/noticia";

import parse from "html-react-parser";
import moment from "moment";
import NoticiaCard from "../../components/server/ArticleFeed/articlecard";
import { CategoryType } from "../../types/category";
import { notFound } from "next/navigation";

const fetchFeed = async (slug: string) => {
  const feedinfo: NoticiaCardType[] = await fetch(
    `http://localhost:8080/api/categories/slugs/${slug}/articles`,
    { cache: "force-cache" }
  ).then((res) => res.json());

  return feedinfo;
};

type PageProps = {
  params: {
    categorySlug: string;
  };
};

async function Category({ params: { categorySlug } }: PageProps) {
  try {
    const noticias = await fetchFeed(categorySlug);
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
  } catch (error) {
    return notFound();
  }
}

export default Category;

export async function generateStaticParams() {
  const res = await fetch("http://localhost:8080/api/categories/");
  const categories: CategoryType[] = await res.json();
  return categories.map((category) => ({
    categorySlug: category.slug,
  }));
}
