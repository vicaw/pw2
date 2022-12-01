import "../styles/globals.css";
import { CategoryType } from "../types/category";
import Header from "../components/client/header/header";
import Providers from "./providers";

const fetchCategories = async () => {
  const categories: CategoryType[] = await fetch(
    "http://localhost:8080/api/categories"
  )
    .then((res) => res.json())
    .catch(() => {
      [{}];
    });

  return categories;
};

export default async function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const categories = await fetchCategories();

  return (
    <html>
      <head />
      <body>
        <Providers>
          <Header categories={categories} />
          {children}
        </Providers>
      </body>
    </html>
  );
}

//<Header categories={categories} />
