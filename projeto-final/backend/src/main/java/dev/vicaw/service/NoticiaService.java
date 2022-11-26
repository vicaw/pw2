package dev.vicaw.service;

import java.util.List;

import dev.vicaw.model.noticia.Noticia;
import dev.vicaw.model.noticia.output.NoticiaListFeedOutput;

public interface NoticiaService {

    public List<Noticia> list();

    public Noticia getById(Long id);

    public Noticia create(Noticia noticia);

    public Noticia getBySlug(String slug);

    public List<NoticiaListFeedOutput> getAllFeedInfo();

}
