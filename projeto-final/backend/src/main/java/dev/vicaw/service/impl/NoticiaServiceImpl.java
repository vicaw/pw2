package dev.vicaw.service.impl;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import dev.vicaw.service.NoticiaService;
import dev.vicaw.model.Noticia;
import dev.vicaw.repository.NoticiaRepository;
import dev.vicaw.repository.entity.NoticiaEntity;

@RequestScoped
public class NoticiaServiceImpl implements NoticiaService {

    @Inject
    NoticiaRepository noticiaRepository;

    @Transactional
    @Override
    public List<Noticia> list() {
        return noticiaRepository.list();
    }

    @Transactional
    @Override
    public Noticia getNoticiaById(long id) {
        NoticiaEntity e = noticiaRepository.findById(id);
        Noticia noticia = new Noticia(e.getId(), e.getTitulo(), e.getResumo(), e.getBody());

        return noticia;
    }

    @Transactional
    @Override
    public Noticia save(Noticia noticia) {
        return noticiaRepository.save(noticia);
    }

}
