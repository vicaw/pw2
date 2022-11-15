package dev.vicaw.service;

import java.util.List;

import dev.vicaw.model.Noticia;

public interface NoticiaService {
    public Noticia save(Noticia noticia);

    public List<Noticia> list();
}
