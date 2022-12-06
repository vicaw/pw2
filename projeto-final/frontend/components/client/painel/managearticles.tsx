"use client";
import { PlusIcon } from "@heroicons/react/20/solid";
import { useRouter } from "next/navigation";
import { useContext, useEffect, useRef } from "react";
import { AuthContext } from "../../../contexts/AuthContext";
import {
  MODAL_TYPES,
  useGlobalModalContext,
} from "../../../contexts/ModalContext";
import ManageArticlesList from "./managearticleslist";

export default function ManageArticles() {
  const { showModal } = useGlobalModalContext();
  const { isAuthenticated, user } = useContext(AuthContext);
  const router = useRouter();

  const createModal = () => {
    showModal(MODAL_TYPES.CREATEARTICLE_MODAL);
  };

  //useEffect(() => {
  // if (user == null) {
  // router.push("/");
  //}
  //}, [user]);

  return (
    <>
      {!isAuthenticated ? null : (
        <main className="p-6 sm:p-10 space-y-6">
          <div className="flex flex-col space-y-6 md:space-y-0 md:flex-row justify-between">
            <div className="mr-6">
              <h1 className="text-4xl font-semibold mb-2">Notícias</h1>
              <h2 className="text-gray-600 ml-0.5">Gerencie suas notícias</h2>
            </div>
            <div className="flex flex-wrap items-start justify-end -mb-3">
              <button
                onClick={createModal}
                className="inline-flex px-5 py-3 text-white bg-red-600 hover:bg-red-700 focus:bg-red-700 rounded-md ml-6 mb-3"
              >
                <PlusIcon className="flex-shrink-0 h-6 w-6 text-white -ml-1 mr-2" />
                Criar nova notícia
              </button>
            </div>
          </div>
          <ManageArticlesList />
        </main>
      )}
    </>
  );
}
