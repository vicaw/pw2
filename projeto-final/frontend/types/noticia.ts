import { CategoryType } from "./category";

export type NoticiaType = {
    id: string;
    slug: string;
    titulo: string;
    subtitulo: string;
    resumo: string;
    body: string;
    createdAt: string;
    updatedAt: string;
    category: CategoryType;
  };

  export type NoticiaCardType = {
    slug: string;
    chapeu_feed: String;
    titulo_feed: string;
    resumo_feed: string;
    createdAt: string;
    category: CategoryType;
  };

  export type UserType = {
    id: string;
    name: string;
    email: string;
  };