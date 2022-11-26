package dev.vicaw.service;

import java.util.List;

import dev.vicaw.model.comment.Comment;
import dev.vicaw.model.comment.input.PostCommentInput;
import dev.vicaw.model.comment.output.CommentOutput;
import dev.vicaw.model.noticia.Noticia;
import dev.vicaw.model.noticia.output.NoticiaListFeedOutput;

public interface CommentService {

    public List<Comment> list();

    public List<CommentOutput> getArticleComments(Long id);

    public Comment getById(Long id);

    public CommentOutput create(PostCommentInput commentInput);

    public List<Comment> getChildren(Long id);

}
