package dev.vicaw.service;

import java.util.List;

import dev.vicaw.model.comment.input.PostCommentInput;
import dev.vicaw.model.comment.output.ArticleCommentsOutput;
import dev.vicaw.model.comment.output.CommentOutput;

public interface CommentService {

    public List<CommentOutput> list();

    public ArticleCommentsOutput getArticleComments(Long id, int page);

    public CommentOutput getById(Long id);

    public CommentOutput create(PostCommentInput commentInput);

    public void delete(Long id);

    // public List<Comment> getChildren(Long id);

}
