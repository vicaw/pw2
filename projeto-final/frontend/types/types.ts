
export type UserType = {
    id: string;
    name: string;
    role: string;
    email?: string;  
}

export type CommentType = {
    id: string;
    articleId: string;
    parentId?: string;
    author: UserType;
    body: string;
    createdAt: string;
    children?: CommentType[];
}

export type PostCommentType = {
    articleId: string;
    authorId?: string;
    parentId?: string;
    body: string;
  };