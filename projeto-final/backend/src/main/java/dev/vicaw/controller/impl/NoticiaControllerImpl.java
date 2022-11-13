package dev.vicaw.controller.impl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import dev.vicaw.controller.NoticiaController;
import dev.vicaw.model.Noticia;
import dev.vicaw.repository.NoticiaRepository;

@RequestScoped
public class NoticiaControllerImpl implements NoticiaController {

    @Inject
    NoticiaRepository noticiaRepository;

    @Transactional
    @Override
    public Noticia save(Noticia noticia) {
        return noticiaRepository.save(noticia);
    }
}
