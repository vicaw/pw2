import { NoticiaCardType, NoticiaType } from "../../../../types/noticia";

import parse from "html-react-parser";
import moment from "moment";
import NoticiaCard from "../../../../components/client/articlesfeed/articlecard";
import { CategoryType } from "../../../../types/category";
import { notFound } from "next/navigation";
import AllArticlesWrapper from "../../../../components/client/articlesfeed/allarticleswrapper";

type Response = {
  hasMore: boolean;
  articles: NoticiaCardType[];
};

const fetchCategories = async (slug: string) => {
  const res = await fetch(
    `http://localhost:8080/api/categories/slugs/${slug}`,
    { cache: "force-cache", next: { revalidate: 60 } }
  );
  if (!res.ok) throw "NotFound";
};

type PageProps = {
  params: {
    categorySlug: string;
  };
};

async function Category({ params: { categorySlug } }: PageProps) {
  console.log(categorySlug);
  try {
    await fetchCategories(categorySlug);
    return (
      <main className="container mx-auto px-10 mb-8 pt-28">
        <AllArticlesWrapper category={categorySlug} />
      </main>
    );
  } catch (error) {
    return notFound();
  }
}

export default Category;
