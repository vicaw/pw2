"use client";

import React, { use, useContext, useEffect, useRef, useState } from "react";
import moment, { LongDateFormatKey } from "moment";
import {
  ArrowUturnLeftIcon,
  ChevronDownIcon,
  ChevronRightIcon,
} from "@heroicons/react/20/solid";
import { AuthContext } from "../../../contexts/AuthContext";
import { CommentType, PostCommentType } from "../../../types";
import axios from "axios";
import { api } from "../../../services/api";
import Comment from "./comment";
import CommentForm from "./commentform";
import Link from "next/link";
import {
  MODAL_TYPES,
  useGlobalModalContext,
} from "../../../contexts/ModalContext";

const fetchComments = async (articleId: string, page: number) => {
  const rawResponse = await api.get(
    `http://localhost:8080/api/comments/article/${articleId}?page=${page}`
  );
  const content = await rawResponse;

  console.log("Fetch Comments");
  return content;
};

interface PageProps {
  articleId: string;
}

export default function CommentArea({ articleId }: PageProps) {
  const [comments, setComments] = useState<CommentType[]>([]);
  const [showComments, setShowComments] = useState(false);
  const [hasMore, setHasMore] = useState(true);
  const maxPages = useRef(0);

  const { isAuthenticated, signOut } = useContext(AuthContext);

  const { showModal } = useGlobalModalContext();

  const loginModal = () => {
    showModal(MODAL_TYPES.LOGIN_MODAL);
  };

  const addComment = (comment: CommentType) => {
    setComments([comment, ...comments]);
  };

  const removeComment = (id: string) => {
    setComments((comments) =>
      comments.filter((comment) => {
        return comment.id !== id;
      })
    );
  };

  const getMoreComments = async () => {
    const currPage = Math.ceil(comments.length / 10);
    console.log(currPage);

    fetchComments(articleId, currPage)
      .then((res) => {
        setComments([...comments, ...res.data.comments]);
        maxPages.current = Math.ceil(res.data.count / 10);
        console.log(maxPages.current);
        if (currPage + 1 === maxPages.current) setHasMore(false);
      })
      .catch((err) => {
        if (err.response.status === 401) {
          signOut();
        }
      });
  };

  useEffect(() => {
    if (showComments) getMoreComments();
  }, [showComments]);

  return (
    <section className="container max-w-2xl m-auto pt-10 mt-10 border-none text-gray-700 text-sm pb-[200px]">
      <div>
        <span
          className={
            showComments
              ? "text-lg mb-2 block"
              : "text-2xl font-bold tracking-tight text-black mb-4 block"
          }
        >
          Comentários (1)
        </span>

        {showComments && isAuthenticated ? (
          <p className="text-xs tracking-tight">
            Os comentários são de responsabilidade exclusiva de seus autores e
            não representam a opinião deste site. Se achar algo que viole os{" "}
            <b>termos de uso</b>, denuncie. Leia as{" "}
            <b>perguntas mais frequentes</b> para saber o que é impróprio ou
            ilegal.
          </p>
        ) : null}
      </div>

      {showComments && isAuthenticated ? (
        <div>
          <CommentForm articleId={articleId} addComment={addComment} />
          <div className="flex flex-col divide-y">
            {comments?.map((comment: any) => (
              <Comment
                key={comment.id}
                comment={comment}
                height={0}
                removeComment={removeComment}
              />
            ))}
          </div>
          {hasMore ? (
            <button
              className="m-auto mt-5 block border rounded-sm p-4 border-gray-300 text-base font-semibold hover:bg-gray-200"
              onClick={getMoreComments}
            >
              Mostrar Mais
            </button>
          ) : null}
        </div>
      ) : (
        <div className="flex flex-col border items-center border-gray-300 py-6 gap-3">
          {isAuthenticated ? (
            <button
              className="border rounded-sm p-4 border-gray-300 text-base font-semibold hover:bg-gray-200"
              onClick={() => setShowComments(true)}
            >
              Ver Comentários
            </button>
          ) : (
            <>
              <div>Acesse sua Conta e participe da conversa</div>
              <button
                className="border rounded-sm p-4 border-gray-300 text-base font-semibold hover:bg-gray-200"
                onClick={loginModal}
              >
                Clique aqui para fazer Login
              </button>
            </>
          )}
        </div>
      )}
    </section>
  );
}
