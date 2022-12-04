import "../styles/globals.css";
import { CategoryType } from "../types/category";
import Header from "../components/client/header/header";
import Providers from "./providers";
import { Suspense } from "react";

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html>
      <head />
      <body>
        <Providers>
          <Header />
          <Suspense
            fallback={
              <div className="absolute right-1/2 bottom-1/2">loading...</div>
            }
          >
            {children}
          </Suspense>
        </Providers>
      </body>
    </html>
  );
}

//<Header categories={categories} />
