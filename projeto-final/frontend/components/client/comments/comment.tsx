"use client";

import React, { use, useContext, useEffect, useState } from "react";
import moment, { LongDateFormatKey } from "moment";
import {
  ArrowUturnLeftIcon,
  ChevronDownIcon,
  ChevronRightIcon,
  PencilIcon,
  TrashIcon,
} from "@heroicons/react/20/solid";
import { CommentType } from "../../../types";
import CommentForm from "./commentform";
import { AuthContext } from "../../../contexts/AuthContext";
import { api } from "../../../services/api";

const deleteComment = async (commentId: string) => {
  console.log("tento deleta ", commentId);
  const rawResponse = await api.delete(
    `http://localhost:8080/api/comments/${commentId}`
  );
  const content = await rawResponse;

  return content;
};

interface PageProps {
  height: number;
  comment: CommentType;
  removeComment: (id: string) => void;
}

export default function Comment({ comment, height, removeComment }: PageProps) {
  const [open, setOpen] = useState(true);
  const [children, setChildren] = useState<CommentType[] | undefined>(
    comment.children
  );
  const [openForm, setOpenForm] = useState(false);

  const { user } = useContext(AuthContext);

  const addChildren = (comment: CommentType) => {
    if (user) comment.author = user;
    setChildren([...(children as []), comment]);
  };

  const removeChildren = (id: string) => {
    setChildren((children) =>
      children?.filter((comment) => {
        return comment.id !== id;
      })
    );
  };

  const handleDeleteComment = async () => {
    const res = await deleteComment(comment.id);
    console.log(res.status);
    if (res.status === 200) {
      removeComment(comment.id);
    }
  };

  return (
    <>
      {!open ? (
        <div className={(height !== 0 ? `ml-3 pt-5` : " py-5") + ""}>
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
        <div className={(height !== 0 ? `ml-3 pt-5` : " py-5") + ""}>
          <div className={(height !== 0 ? "divide-x-2" : "") + " flex w-full"}>
            <hr />
            <div className="flex gap-2 w-full">
              <div
                className="cursor-pointer pl-2"
                onClick={() => setOpen(!open)}
              >
                <ChevronDownIcon className="h-5" />
              </div>
              <div className="flex-grow">
                <div className="flex items-center gap-2">
                  <span className="leading-tight font-bold text-gray-600">
                    {comment.author?.name}
                  </span>
                  <span className="text-[11.2px] font-semibold text-gray-500">
                    {moment(comment.createdAt).fromNow()}
                  </span>
                  {user?.role === "ADMIN" ? (
                    <>
                      <PencilIcon
                        className="h-4 cursor-pointer hover:fill-red-500"
                        onClick={() => handleDeleteComment()}
                      />
                      <TrashIcon
                        className="h-4 cursor-pointer hover:fill-red-500"
                        onClick={() => handleDeleteComment()}
                      />
                    </>
                  ) : null}
                </div>
                <div className="py-2">{comment.body}</div>
                <div className="leading-tight font-semibold text-gray-600 text-xs flex gap-1 cursor-pointer mt-1">
                  <ArrowUturnLeftIcon className="h-3" />
                  <button onClick={() => setOpenForm(!openForm)}>
                    Responder
                  </button>
                </div>
                {openForm ? (
                  <CommentForm
                    articleId={comment.articleId}
                    parentId={comment.id}
                    addComment={addChildren}
                  />
                ) : null}
              </div>
            </div>
          </div>
          <div>
            {children?.map((child: any) => (
              <Comment
                key={child.id}
                comment={child}
                height={height + 1}
                removeComment={removeChildren}
              />
            ))}
          </div>
        </div>
      )}
    </>
  );
}
