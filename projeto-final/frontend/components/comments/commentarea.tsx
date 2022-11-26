"use client";

import React, { use, useContext, useEffect, useState } from "react";
import moment, { LongDateFormatKey } from "moment";
import {
  ArrowUturnLeftIcon,
  ChevronDownIcon,
  ChevronRightIcon,
} from "@heroicons/react/20/solid";
import { AuthContext } from "../../contexts/AuthContext";
import { NoticiaType, UserType } from "../../types/noticia";
import { PostCommentType } from "../../types/types";

const fetchComments = async (articleId: string) => {
  // const nextCookies = cookies();
  // const token = nextCookies.get("nextauth.token");

  const comments: any = await fetch(
    `http://localhost:8080/api/comments/article/${articleId}`,
    {
      cache: "default",
      // headers: {
      //  Authorization: `Bearer ${token?.value}`,
      // },
    }
  )
    .then((res) => res.json())
    .catch(() => {
      [{}];
    });

  console.log(comments);

  return comments;
};

const postComment = async (comment: any) => {
  console.log(comment);
  const rawResponse = await fetch("http://localhost:8080/api/comments/", {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
    body: JSON.stringify(comment),
  });
  const content = await rawResponse.json();

  console.log(content);
};

function CommentForm({ comment, articleId }: any) {
  const { user } = useContext(AuthContext);
  const [commentText, setCommentText] = useState("");

  const onChange = (e: React.FormEvent<HTMLTextAreaElement>) => {
    setCommentText(e.currentTarget.value);
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const postContent: PostCommentType = {
      articleId: articleId,
      authorId: user?.id,
      parentId: comment ? comment.id : null,
      body: commentText,
    };

    postComment(postContent);
  };

  return (
    <form onSubmit={handleSubmit}>
      <textarea value={commentText} onChange={onChange} rows={5} />
      <div className="k-form-buttons k-justify-content-end">
        <button type="submit">Add comment</button>
      </div>
    </form>
  );
}

interface Props {
  categories: string;
}

function Comment({ comment, height, articleId }: any) {
  const [open, setOpen] = useState(true);
  const [openForm, setOpenForm] = useState(false);

  console.log(articleId);

  return (
    <>
      {!open ? (
        <div className={(height !== 0 ? `mx-3 ` : " ") + "pt-5 pb-2 "}>
          <div
            className={(height !== 0 ? "px-5 " : "") + "flex gap-3 divide-none"}
          >
            <div className="cursor-pointer" onClick={() => setOpen(!open)}>
              <ChevronRightIcon className="h-5" />
            </div>
            <div className={height !== 0 ? "" : ""}>
              <div className="flex items-center gap-2">
                <span className="leading-tight font-bold text-gray-600">
                  {comment.author?.name}
                </span>
                <span className="text-[11.2px] font-semibold text-gray-500">
                  {moment(comment.createdAt).fromNow()}
                </span>
              </div>
            </div>
          </div>
        </div>
      ) : (
        <div className={(height !== 0 ? `mx-3 ` : " ") + "pt-5 pb-2 "}>
          <div className={(height !== 0 ? "divide-x-2" : "") + " flex gap-3 "}>
            <hr />
            <div className="flex gap-3">
              <div
                className="cursor-pointer pl-3"
                onClick={() => setOpen(!open)}
              >
                <ChevronDownIcon className="h-5" />
              </div>
              <div className={height !== 0 ? "" : ""}>
                <div className="flex items-center gap-2">
                  <span className="leading-tight font-bold text-gray-600">
                    {comment.author?.name}
                  </span>
                  <span className="text-[11.2px] font-semibold text-gray-500">
                    {moment(comment.createdAt).fromNow()}
                  </span>
                </div>
                <div className="py-2">{comment.body}</div>
                <div className="leading-tight font-semibold text-gray-600 text-xs flex gap-1 cursor-pointer">
                  <ArrowUturnLeftIcon className="h-3" />
                  <button onClick={() => setOpenForm(!openForm)}>
                    Responder
                  </button>
                  {openForm ? (
                    <CommentForm articleId={articleId} comment={comment} />
                  ) : null}
                </div>
              </div>
            </div>
          </div>
          <div>
            {comment.children?.map((ch: any) => (
              <Comment comment={ch} height={height + 1} articleId={articleId} />
            ))}
          </div>
        </div>
      )}
    </>
  );
}

interface PageProps {
  articleId: string;
}

export default function CommentArea({ articleId }: PageProps) {
  const [comments, setComments] = useState([{}]);
  console.log(articleId);
  useEffect(() => {
    fetchComments(articleId).then((r) => {
      setComments(r);
    });
  }, []);

  return (
    <section className="max-w-2xl m-auto pt-10 mt-10 px-4 border-none text-gray-700 text-sm">
      <div>
        <span className="text-lg">Comentários ({comments.length})</span>
        <p className="text-xs tracking-tight">
          Os comentários são de responsabilidade exclusiva de seus autores e não
          representam a opinião deste site. Se achar algo que viole os{" "}
          <b>termos de uso</b>, denuncie. Leia as{" "}
          <b>perguntas mais frequentes</b> para saber o que é impróprio ou
          ilegal.
        </p>
      </div>
      <CommentForm articleId={articleId} />
      <div className="full pb-[200px] flex flex-col divide-y">
        {comments?.map((c: any) => (
          <Comment comment={c} height={0} articleId={articleId} />
        ))}
      </div>
    </section>
  );
}
