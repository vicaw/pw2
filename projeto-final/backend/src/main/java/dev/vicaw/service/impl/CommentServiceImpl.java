package dev.vicaw.service.impl;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.jwt.JsonWebToken;

import dev.vicaw.service.CommentService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import dev.vicaw.exception.ApiException;
import dev.vicaw.model.comment.Comment;
import dev.vicaw.model.comment.CommentMapper;
import dev.vicaw.model.comment.input.PostCommentInput;
import dev.vicaw.model.comment.output.ArticleCommentsOutput;
import dev.vicaw.model.comment.output.CommentOutput;

import dev.vicaw.repository.CommentRepository;

@RequestScoped
public class CommentServiceImpl implements CommentService {

    @Inject
    CommentRepository commentRepository;;

    @Inject
    CommentMapper commentMapper;

    @Inject
    JsonWebToken jwt;

    @Override
    public List<CommentOutput> list() {
        return commentMapper.toCommentOutputList(commentRepository.listAll());
    }

    @Override
    public ArticleCommentsOutput getArticleComments(Long id, int page) {

        PanacheQuery<Comment> commentsWithoutParent = commentRepository
                .find("noticia_id = ?1 AND parent_id IS NULL order by createdAt DESC", id);

        List<Comment> comments = commentsWithoutParent.page(Page.of(page, 10)).list();

        long count = commentsWithoutParent.count();
        List<CommentOutput> commentsMapped = commentMapper.toCommentOutputList(comments);

        return new ArticleCommentsOutput(count, commentsMapped);
    }

    @Override
    public CommentOutput getById(Long id) {
        Optional<Comment> comment = commentRepository.findByIdOptional(id);
        if (comment.isEmpty())
            throw new ApiException(404, "Não existe nenhum comentário com o ID informado.");

        CommentOutput commentMapped = commentMapper.toCommentOutput(comment.get());

        return commentMapped;
    }

    @Transactional
    @Override
    public CommentOutput create(PostCommentInput commentInput) {
        if (!jwt.getSubject().equals(commentInput.getAuthorId().toString()))
            throw new ApiException(400, "Erro ao processar o ID do autor.");

        if (commentInput.getParentId() != null) {
            CommentOutput parent = getById(commentInput.getParentId());
            if (parent.getArticleId() != commentInput.getArticleId())
                throw new ApiException(400, "Erro ao processar o ID do comentário pai.");
        }

        Comment comment = commentMapper.toModel(commentInput);
        commentRepository.persist(comment);
        return commentMapper.toCommentOutput(comment);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        boolean result = commentRepository.deleteById(id);
        if (!result)
            throw new ApiException(400, "Não existe nenhum comentário com o ID informado.");
    }

    /*
     * @Override
     * public List<Comment> getChildren(Long id) {
     * return new ArrayList<Comment>();
     * }
     */

}
