package dev.vicaw.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.slugify.Slugify;

import dev.vicaw.service.CategoryService;
import dev.vicaw.service.ArticleService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import dev.vicaw.client.ImageServiceClient;
import dev.vicaw.client.ImageInput;
import dev.vicaw.exception.ApiException;
import dev.vicaw.model.article.Article;
import dev.vicaw.model.article.ArticleMapper;
import dev.vicaw.model.article.input.ArticleInput;
import dev.vicaw.model.article.input.ArticleFormInput;
import dev.vicaw.model.article.output.FeedOutput;
import dev.vicaw.model.article.output.ArticleFeedOutput;
import dev.vicaw.model.article.output.ArticleOutput;
import dev.vicaw.model.category.Category;

import dev.vicaw.repository.ArticleRepository;

@RequestScoped
public class ArticleServiceImpl implements ArticleService {

    @Inject
    ArticleRepository articleRepository;

    @Inject
    CategoryService categoryService;

    @Inject
    @RestClient
    ImageServiceClient imageService;

    @Inject
    ArticleMapper articleMapper;

    @Inject
    JsonWebToken jwt;

    @Override
    public List<Article> list(int authorId) {
        if (authorId != 0) {
            return articleRepository.list("author_id", authorId);
        }

        return articleRepository.listAll();
    }

    @Override
    public Article getById(Long id) {
        Optional<Article> article = articleRepository.findByIdOptional(id);
        if (article.isEmpty())
            throw new ApiException(404, "Não existe nenhuma notícia com o ID informado.");

        return article.get();
    }

    @Override
    public ArticleOutput getBySlug(String slug) {
        Optional<Article> article = articleRepository.findBySlug(slug);

        if (article.isEmpty())
            throw new ApiException(404, "Não existe nenhuma notícia com a slug informada.");

        return articleMapper.toArticleOutput(article.get());
    }

    @Transactional
    @Override
    public Article create(InputStream coverImage, String coverImageName, @Valid ArticleInput articleInput) {
        Article article = articleMapper.toModel(articleInput);

        if (!jwt.getSubject().equals(article.getAuthorId().toString()))
            throw new ApiException(400, "Erro ao processar o ID do autor.");

        // Verifica se categoria existe (Lança exceção caso não exista.)
        categoryService.getById(article.getCategoryId());

        Slugify slugify = Slugify.builder().build();
        String slug = slugify.slugify(article.getTitulo());
        article.setSlug(slug);

        if (articleRepository.findBySlug(article.getSlug()).isPresent())
            throw new ApiException(409, "Já existe uma notícia com o título informado.");

        // if (res.getStatus() != 200)
        // throw new ApiException(400, "Erro ao fazer upload da imagem.");

        articleRepository.persist(article);

        imageService.upload(new ImageInput(coverImage, coverImageName, article.getId().toString()));

        return article;
    }

    @Transactional
    @Override
    public Article edit(ArticleFormInput body) {
        ObjectMapper mapper = new ObjectMapper();
        ArticleInput articleInput = null;

        try {
            articleInput = mapper.readValue(body.article, ArticleInput.class);
        } catch (IOException e) {
            throw new ApiException(401, "Erro ao processar informações.");
        }

        Article article = articleMapper.toModel(articleInput);

        // Também verifica se a notícia existe (Exceção se não existir)
        Article original = getById(article.getId());

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

            if (articleRepository.findBySlug(article.getSlug()).isPresent())
                throw new ApiException(409, "Já existe uma notícia com o título informado.");
        }

        // Manter o autor caso a edição tenha sido feita por um Administrador.
        if (isAdmin)
            article.setAuthorId(original.getAuthorId());

        // Manter a data de criação
        article.setCreatedAt(original.getCreatedAt());

        article = articleRepository.getEntityManager().merge(article);
        articleRepository.persist(article);

        // Atualiza a imagem da capa, caso uma nova tenha sido enviada.
        // if (body.file != null)
        // imageService.update(new ImageInput(body.getFile(), body.getFileName(),
        // article.getId().toString()));

        return article;
    }

    @Override
    public FeedOutput getFeedInfo(int pagesize, int pagenumber, String categorySlug) {

        PanacheQuery<ArticleFeedOutput> queryResult;

        if (categorySlug != null) {
            Category category = categoryService.getBySlug(categorySlug);
            queryResult = articleRepository.getAllFeedInfo(category.getId());

        } else {
            queryResult = articleRepository.getAllFeedInfo();
        }

        PanacheQuery<ArticleFeedOutput> page = queryResult.page(Page.of(pagenumber, pagesize));

        List<ArticleFeedOutput> articles = page.list();

        boolean hasMore = page.hasNextPage();

        return new FeedOutput(hasMore, articles);

    }

    @Override
    public List<ArticleFeedOutput> searchArticle(String query) {
        return articleRepository.search(query);
    }

}
