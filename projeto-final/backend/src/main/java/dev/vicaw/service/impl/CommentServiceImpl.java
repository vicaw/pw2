package dev.vicaw.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.jwt.JsonWebToken;

import com.github.slugify.Slugify;

import dev.vicaw.service.CommentService;
import dev.vicaw.service.NoticiaService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import dev.vicaw.model.comment.Comment;
import dev.vicaw.model.comment.CommentMapper;
import dev.vicaw.model.comment.input.PostCommentInput;
import dev.vicaw.model.comment.output.CommentOutput;
import dev.vicaw.model.noticia.Noticia;
import dev.vicaw.model.noticia.NoticiaMapper;
import dev.vicaw.model.noticia.output.NoticiaListFeedOutput;
import dev.vicaw.repository.CommentRepository;
import dev.vicaw.repository.NoticiaRepository;

@RequestScoped
public class CommentServiceImpl implements CommentService {

    @Inject
    CommentRepository commentRepository;;

    @Inject
    CommentMapper commentMapper;

    @Inject
    JsonWebToken jwt;

    @Override
    public List<Comment> list() {
        return commentRepository.listAll();
        // return noticiaMapper.toModelList(noticiaRepository.listAll());
    }

    @Override
    public List<CommentOutput> getArticleComments(Long id) {
        return commentMapper.toCommentOutputList(commentRepository.list("noticia_id = ?1 AND parent_id IS NULL", id));
    }

    @Override
    public Comment getById(Long id) {
        // Noticia entity = noticiaRepository.findById(id);
        // Noticia noticia = noticiaMapper.toModel(entity);

        Comment noticia = commentRepository.findById(id);

        return noticia;
    }

    @Override
    public List<Comment> getChildren(Long id) {
        // Optional<Noticia> noticia = commentRepository.findBySlug(slug);
        // Noticia noticia = noticiaMapper.toModel(entity.get());

        return new ArrayList<Comment>();
    }

    @Transactional
    @Override
    public CommentOutput create(PostCommentInput commentInput) {
        Comment comment = commentMapper.toModel(commentInput);
        System.out.println("ar: " + comment.getArticleId());
        commentRepository.persist(comment);
        return commentMapper.toCommentOutput(comment);
    }

}
