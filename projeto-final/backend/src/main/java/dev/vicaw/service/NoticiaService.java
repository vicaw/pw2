package dev.vicaw.service;

import java.util.List;
import dev.vicaw.model.Noticia;

public interface NoticiaService {

    public List<Noticia> list();

    public Noticia getNoticiaById(long id);

    public Noticia save(Noticia noticia);

}
