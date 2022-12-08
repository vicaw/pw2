import { AxiosError } from 'axios';
import ApiError from '../models/ApiError';
import Article from '../models/Article';
import { api } from './axios/api';

export async function createArticleRequest(data: FormData): Promise<any> {
  try {
    const res = await api.post(`http://localhost:8080/api/articles`, data, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    const dados = await res.data;
    console.log(dados);
    return dados;
  } catch (err) {
    const errors = err as AxiosError<any>;
    throw errors?.response?.data.message;
  }
}

export async function editArticleRequest(data: FormData): Promise<any> {
  console.log(data);
  try {
    const res = await api.put(`http://localhost:8080/api/articles`, data, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    const dados = await res.data;

    return dados;
  } catch (err) {
    const errors = err as AxiosError<any>;
    throw errors?.response?.data.message;
  }
}

async function getArticleBySlug(slug: string): Promise<Article> {
  const response = await fetch(`http://localhost:8080/api/articles/slugs/${slug}`, {
    next: { revalidate: 600 },
  });
  const data = await response.json();

  if (response.ok) return data as Article;

  console.log('[ArticleServices] getArticleBySlug() Error:', data.message);
  const error = new ApiError('Get Article By Slug Error', data.message, data.code);
  throw error;
}

const articleService = {
  getArticleBySlug,
};

export default articleService;
