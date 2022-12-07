"use client";
import { FieldValues, useForm } from "react-hook-form";
import {
  ChangeEvent,
  MouseEvent,
  useContext,
  useEffect,
  useState,
} from "react";
import { AuthContext } from "../../../contexts/AuthContext";
import { useGlobalModalContext } from "../../../contexts/ModalContext";
import {
  registrationRequest,
  RegistrationRequestData,
} from "../../../services/auth";
import { CheckCircleIcon } from "@heroicons/react/20/solid";

import Image from "next/image";
import { CategoryType } from "../../../types/category";
import { api } from "../../../services/api";
import { AxiosError } from "axios";
import { NoticiaType } from "../../../types/noticia";
import { PhotoIcon } from "@heroicons/react/24/outline";

const fetchCategories = async () => {
  const categories: CategoryType[] = await fetch(
    "http://localhost:8080/api/categories",
    { cache: "force-cache", next: { revalidate: 60 } }
  )
    .then((res) => res.json())
    .catch(() => {
      [{}];
    });
  console.log("fetch menu categories");

  return categories;
};

export type CreateArticleRequestData = {
  id: string | null;
  slug: string | null;
  titulo: string;
  subtitulo: string;
  body: string;
  chapeu_feed: string;
  titulo_feed: string;
  resumo_feed: string;
  categoryId: string;
  authorId: string;
};

async function createArticleRequest(data: FormData): Promise<any> {
  try {
    const res = await api.post(`http://localhost:8080/api/noticias`, data, {
      headers: {
        "Content-Type": "multipart/form-data",
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

async function editArticleRequest(data: FormData): Promise<any> {
  console.log(data);
  try {
    const res = await api.put(`http://localhost:8080/api/noticias`, data, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    const dados = await res.data;

    return dados;
  } catch (err) {
    const errors = err as AxiosError<any>;
    throw errors?.response?.data.message;
  }
}

interface Props {
  article?: NoticiaType;
}

export default function CreateArticleForm({ article }: Props) {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");
  const [registred, SetRegistred] = useState(false);

  const { user } = useContext(AuthContext);

  const { register, handleSubmit } = useForm();

  const [file, setFile] = useState<File | null>(null);
  const [previewUrl, setPreviewUrl] = useState<string | null>(
    article ? `http://localhost:8081/images/articles/${article.id}` : null
  );

  const [categories, setCategories] = useState<CategoryType[]>([]);
  const [currentCategory, setCurrentCategory] = useState<
    string | number | undefined
  >(0);

  async function getCategories() {
    setIsLoading(true);
    await fetchCategories().then((c) => setCategories(c));
    setIsLoading(false);
    setCurrentCategory(article?.category.id);
  }

  useEffect(() => {
    getCategories();
  }, []);

  const onFileUploadChange = (e: ChangeEvent<HTMLInputElement>) => {
    const fileInput = e.target;

    if (!fileInput.files) {
      alert("No file was chosen");
      return;
    }

    if (!fileInput.files || fileInput.files.length === 0) {
      alert("Files list is empty");
      return;
    }

    const file = fileInput.files[0];

    /** File validation */
    if (!file.type.startsWith("image")) {
      alert("Please select a valide image");
      return;
    }

    /** Setting file state */
    setFile(file); // we will use the file state, to send it later to the server
    setPreviewUrl(URL.createObjectURL(file)); // we will use this to show the preview of the image

    /** Reset file input */
    e.currentTarget.type = "text";
    e.currentTarget.type = "file";
  };

  const onCancelFile = (e: MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    if (!previewUrl && !file) {
      return;
    }
    setFile(null);
    setPreviewUrl(null);
  };

  async function handleRegistration(data: FieldValues) {
    console.log("Slug: ");

    if (!user) return;

    if (!file && !article) return;

    let reqData = data as CreateArticleRequestData;
    reqData.authorId = user.id;
    reqData.id = article ? article.id : null;
    reqData.slug = article ? article.slug : null;

    var formData = new FormData();
    if (file) {
      formData.append("file", file);
      formData.append("fileName", file.name);
    }
    formData.append("article", JSON.stringify(reqData));

    try {
      setIsLoading(true);
      const res = !article
        ? await createArticleRequest(formData)
        : await editArticleRequest(formData);
      SetRegistred(true);
    } catch (err) {
      setError(err as string);
    } finally {
      setError("");
      setIsLoading(false);
    }
  }

  return (
    <>
      {registred ? (
        <div className="mt-4">
          <CheckCircleIcon className="fill-green-700 w-16 h-16 m-auto" />
          <span>Notícia publicada com sucesso.</span>
        </div>
      ) : (
        <form
          className="mt-8 space-y-6"
          onSubmit={handleSubmit(handleRegistration)}
        >
          <div className="tracking-tight flex flex-col gap-5">
            <div className="grid grid-cols-2 gap-5">
              <fieldset className="flex border p-4 text-gray-600 text-sm text-left rounded-sm">
                <legend className="px-2 text-gray-400 font-bold">Capa</legend>
                <div className="flex flex-col w-full md:flex-row gap-1.5 md:py-4">
                  <div className="flex-grow">
                    {previewUrl ? (
                      <div className="mx-auto w-80">
                        <img
                          alt="file uploader preview"
                          //objectFit="cover"
                          src={previewUrl}
                          width={320}
                          height={218}
                          // layout="fixed"
                        />
                      </div>
                    ) : (
                      <label className="flex flex-col items-center justify-center h-full py-3 transition-colors duration-150 cursor-pointer hover:text-red-600">
                        <PhotoIcon className="w-14 h-14" />
                        <strong className="text-sm font-semibold">
                          Selecione uma imagem
                        </strong>
                        <input
                          className="block w-0 h-0"
                          name="file"
                          type="file"
                          onChange={onFileUploadChange}
                        />
                      </label>
                    )}
                  </div>
                  <div className="flex mt-4 md:mt-0 md:flex-col justify-center gap-1.5">
                    <button
                      disabled={!previewUrl}
                      onClick={onCancelFile}
                      className="w-1/2 px-4 py-3 text-sm font-bold tracking-tighter text-white transition-colors duration-300 bg-red-600 rounded-sm md:w-auto disabled:bg-gray-400 hover:bg-red-700 disabled:cursor-not-allowed"
                    >
                      CANCELAR
                    </button>
                  </div>
                </div>
              </fieldset>
              <fieldset className=" flex flex-col gap-5 text-gray-600 text-sm text-left border p-4 rounded-sm">
                <legend className="px-2 text-gray-400 font-bold">Feed</legend>
                <div>
                  <label htmlFor="chapeu">Chapéu</label>
                  <input
                    {...register("chapeu_feed")}
                    type="text"
                    disabled={isLoading}
                    required
                    defaultValue={article?.chapeu_feed}
                    className="appearance-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-sm focus:outline-none focus:ring-red-600 focus:border-red-600 focus:z-10 sm:text-sm"
                  />
                </div>
                <div>
                  <label htmlFor="tituloFeed">Título</label>
                  <input
                    {...register("titulo_feed")}
                    type="text"
                    disabled={isLoading}
                    defaultValue={article?.titulo_feed}
                    required
                    className="appearance-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-sm focus:outline-none focus:ring-red-600 focus:border-red-600 focus:z-10 sm:text-sm"
                  />
                </div>
                <div>
                  <label htmlFor="resumo">Resumo</label>
                  <input
                    {...register("resumo_feed")}
                    type="text"
                    disabled={isLoading}
                    defaultValue={article?.resumo_feed}
                    required
                    className="appearance-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-sm focus:outline-none focus:ring-red-600 focus:border-red-600 focus:z-10 sm:text-sm"
                  />
                </div>
              </fieldset>
            </div>

            <fieldset className="flex flex-col gap-5 border p-4 text-gray-600 text-sm text-left  rounded-sm">
              <legend className="px-2 text-gray-400 font-bold">Notícia</legend>

              <div>
                <label htmlFor="category">Categoria</label>

                <select
                  {...register("categoryId", { required: true })}
                  className="border relative block w-full px-3 py-2 rounded focus-visible:ring-red-600 focus:border-red-600 "
                  value={currentCategory}
                  defaultValue={currentCategory}
                  onChange={(e) => setCurrentCategory(e.currentTarget.value)}
                >
                  {categories.map((category) => (
                    <option key={category.id} value={category.id}>
                      {category.name}
                    </option>
                  ))}
                </select>
              </div>

              <div>
                <label htmlFor="titulo">Título</label>
                <input
                  {...register("titulo")}
                  type="text"
                  disabled={isLoading}
                  required
                  defaultValue={article?.titulo}
                  className="appearance-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-sm focus:outline-none focus:ring-red-600 focus:border-red-600 focus:z-10 sm:text-sm"
                />
              </div>
              <div>
                <label htmlFor="subtitulo">Subtítulo</label>
                <textarea
                  {...register("subtitulo")}
                  disabled={isLoading}
                  required
                  defaultValue={article?.subtitulo}
                  className="appearance-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-sm focus:outline-none focus:ring-red-600 focus:border-red-600 focus:z-10 sm:text-sm"
                />
              </div>
              <div>
                <label htmlFor="body">Corpo</label>
                <textarea
                  {...register("body")}
                  disabled={isLoading}
                  defaultValue={article?.body}
                  required
                  className="min-h-[500px] appearance-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-sm focus:outline-none focus:ring-red-600 focus:border-red-600 focus:z-10 sm:text-sm"
                />
              </div>
            </fieldset>
          </div>

          <div>
            <button
              type="submit"
              disabled={isLoading}
              className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-extrabold rounded-sm text-white tracking-tighter bg-red-600 hover:bg-red-700 disabled:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500"
            >
              {!isLoading ? (
                !article ? (
                  "PUBLICAR NOTÍCIA"
                ) : (
                  "SALVAR MODIFICAÇÕES"
                )
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
          </div>

          {error !== "" ? (
            <div>
              <span className="text-sm tracking-tighter text-red-600">
                {error}
              </span>
            </div>
          ) : null}
        </form>
      )}
    </>
  );
}