import Category from "./Category";
import User from "./User";


export default interface Article {
    id: string;
    slug: string;
    titulo: string;
    subtitulo: string;
    resumo: string;
    body: string;
    createdAt: string;
    updatedAt: string;
    chapeu_feed: string;
    titulo_feed: string;
    resumo_feed: string;
    category: Category;
    author: User;
};

export interface NewArticle {
    titulo: string;
    subtitulo: string;
    body: string;
    chapeu_feed: string;
    titulo_feed: string;
    resumo_feed: string;
    categoryId: string;
    authorId: string;
  };
  

export interface ArticleCard {
    id: string;
    slug: string;
    chapeu_feed: string;
    titulo_feed: string;
    resumo_feed: string;
    createdAt: string;
    category: Category;
};
