package dev.vicaw.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.jwt.JsonWebToken;

import com.github.slugify.Slugify;

import dev.vicaw.service.NoticiaService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import dev.vicaw.model.noticia.Noticia;
import dev.vicaw.model.noticia.NoticiaMapper;
import dev.vicaw.model.noticia.output.NoticiaListFeedOutput;
import dev.vicaw.repository.NoticiaRepository;

@RequestScoped
public class NoticiaServiceImpl implements NoticiaService {

    @Inject
    NoticiaRepository noticiaRepository;

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
    public Noticia getById(long id) {
        // Noticia entity = noticiaRepository.findById(id);
        // Noticia noticia = noticiaMapper.toModel(entity);

        Noticia noticia = noticiaRepository.findById(id);

        if (jwt.getClaimNames() == null)
            noticia.setBody("Registre-se para ver esse conteúdo.");

        return noticia;
    }

    @Override
    public Noticia getBySlug(String slug) {
        Optional<Noticia> noticia = noticiaRepository.findBySlug(slug);
        // Noticia noticia = noticiaMapper.toModel(entity.get());

        return noticia.get();
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
    public List<NoticiaListFeedOutput> getAllFeedInfo() {
        return noticiaRepository.getAllFeedInfo();
    }

}
