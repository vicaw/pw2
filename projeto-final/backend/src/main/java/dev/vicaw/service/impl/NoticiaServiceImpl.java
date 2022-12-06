package dev.vicaw.service.impl;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.jwt.JsonWebToken;

import com.github.slugify.Slugify;

import dev.vicaw.service.CategoryService;
import dev.vicaw.service.NoticiaService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import dev.vicaw.exception.ApiException;
import dev.vicaw.model.category.Category;
import dev.vicaw.model.noticia.Noticia;
import dev.vicaw.model.noticia.NoticiaMapper;
import dev.vicaw.model.noticia.input.CreateArticleInput;
import dev.vicaw.model.noticia.output.FeedOutput;
import dev.vicaw.model.noticia.output.NoticiaFeedOutput;
import dev.vicaw.model.noticia.output.NoticiaOutput;
import dev.vicaw.repository.CategoryRepository;
import dev.vicaw.repository.NoticiaRepository;

@RequestScoped
public class NoticiaServiceImpl implements NoticiaService {

    @Inject
    NoticiaRepository noticiaRepository;

    @Inject
    CategoryService categoryService;

    @Inject
    NoticiaMapper noticiaMapper;

    @Inject
    JsonWebToken jwt;

    @Override
    public List<Noticia> list(int authorId) {
        if (authorId != 0) {
            return noticiaRepository.list("author_id", authorId);
        }

        return noticiaRepository.listAll();
    }

    @Override
    public Noticia getById(Long id) {
        Optional<Noticia> article = noticiaRepository.findByIdOptional(id);
        if (article.isEmpty())
            throw new ApiException(404, "Não existe nenhuma notícia com o ID informado.");

        return article.get();
    }

    @Override
    public NoticiaOutput getBySlug(String slug) {
        Optional<Noticia> noticia = noticiaRepository.findBySlug(slug);

        // Noticia noticia = noticiaMapper.toModel(entity.get());

        return noticiaMapper.toCommentOutput(noticia.get());
    }

    @Transactional
    @Override
    public Noticia create(CreateArticleInput articleInput) {
        Noticia article = noticiaMapper.toModel(articleInput);

        if (!jwt.getSubject().equals(article.getAuthorId().toString()))
            throw new ApiException(400, "Erro ao processar o ID do autor.");

        categoryService.getById(article.getCategoryId()); // Verifica se categoria existe (Vai ter exceção caso não
                                                          // exista.)

        Slugify slugify = Slugify.builder().build();
        String slug = slugify.slugify(article.getTitulo());
        article.setSlug(slug);

        if (noticiaRepository.findBySlug(article.getSlug()).isPresent())
            throw new ApiException(409, "Já existe uma notícia com o título informado.");

        noticiaRepository.persist(article);

        return article;
    }

    @Transactional
    @Override
    public Noticia edit(CreateArticleInput articleInput) {
        Noticia article = noticiaMapper.toModel(articleInput);

        // Também verifica se a notícia existe (Exceção se não existir)
        Noticia original = getById(article.getId());

        // Verifica se o usuário é administrador
        boolean isAdmin = jwt.getGroups().contains("ADMIN");

        // Se não for ADMIN
        if (!isAdmin) {
            // Verifica se o ID do usuário que fez a requisição é realmente o autor da
            // notícia.
            if (!jwt.getSubject().equals(original.getAuthorId().toString()))
                throw new ApiException(400, "Erro ao processar o ID do autor.");
        }

        // Verifica se categoria informada existe (Lança exceção caso não exista)
        categoryService.getById(article.getCategoryId());

        // Se o título foi alterado, gera uma nova Slug.
        if (!article.getTitulo().equals(original.getTitulo())) {
            Slugify slugify = Slugify.builder().build();
            String slug = slugify.slugify(article.getTitulo());
            article.setSlug(slug);

            if (noticiaRepository.findBySlug(article.getSlug()).isPresent())
                throw new ApiException(409, "Já existe uma notícia com o título informado.");
        }

        // Manter o autor caso a edição tenha sido feita por um Administrador.
        if (isAdmin)
            article.setAuthorId(original.getAuthorId());

        // Manter a data de criação
        article.setCreatedAt(original.getCreatedAt());

        article = noticiaRepository.getEntityManager().merge(article);
        noticiaRepository.persist(article);

        return article;
    }

    @Override
    public FeedOutput getFeedInfo(int pagesize, int pagenumber, String categorySlug) {

        PanacheQuery<NoticiaFeedOutput> queryResult;

        if (categorySlug != null) {
            Category category = categoryService.getBySlug(categorySlug);
            queryResult = noticiaRepository.getAllFeedInfo(category.getId());

        } else {
            queryResult = noticiaRepository.getAllFeedInfo();
        }

        PanacheQuery<NoticiaFeedOutput> page = queryResult.page(Page.of(pagenumber, pagesize));

        List<NoticiaFeedOutput> articles = page.list();

        boolean hasMore = page.hasNextPage();

        return new FeedOutput(hasMore, articles);

    }

    @Override
    public List<NoticiaFeedOutput> searchArticle(String query) {
        return noticiaRepository.search(query);
    }

}
