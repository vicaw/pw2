package dev.vicaw.service.impl;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.jwt.JsonWebToken;

import com.github.slugify.Slugify;

import dev.vicaw.service.NoticiaService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import dev.vicaw.exception.ApiException;
import dev.vicaw.model.category.Category;
import dev.vicaw.model.noticia.Noticia;
import dev.vicaw.model.noticia.NoticiaMapper;
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
    CategoryRepository categoryRepository;

    @Inject
    NoticiaMapper noticiaMapper;

    @Inject
    JsonWebToken jwt;

    @Override
    public List<Noticia> list() {
        return noticiaRepository.listAll();
        // return noticiaMapper.toModelList(noticiaRepository.listAll());
    }

    @Override
    public Noticia getById(Long id) {
        // Noticia entity = noticiaRepository.findById(id);
        // Noticia noticia = noticiaMapper.toModel(entity);

        Noticia noticia = noticiaRepository.findById(id);

        return noticia;
    }

    @Override
    public NoticiaOutput getBySlug(String slug) {
        Optional<Noticia> noticia = noticiaRepository.findBySlug(slug);

        // Noticia noticia = noticiaMapper.toModel(entity.get());

        return noticiaMapper.toCommentOutput(noticia.get());
    }

    @Transactional
    @Override
    public Noticia create(Noticia noticia) {
        Slugify slugify = Slugify.builder().build();
        String slug = slugify.slugify(noticia.getTitulo());
        noticia.setSlug(slug);

        // Noticia entity = noticiaMapper.toEntity(noticia);

        noticiaRepository.persist(noticia);

        // noticiaMapper.updateModelFromEntity(entity, noticia);

        return noticia;
    }

    @Override
    public FeedOutput getFeedInfo(int pagesize, int pagenumber, String categorySlug) {

        PanacheQuery<NoticiaFeedOutput> queryResult;

        if (categorySlug != null) {
            Optional<Category> category = categoryRepository.findBySlug(categorySlug);
            if (category.isEmpty())
                throw new ApiException(404, "Essa categoria não existe.");

            queryResult = noticiaRepository.getAllFeedInfo(category.get().getId());

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
