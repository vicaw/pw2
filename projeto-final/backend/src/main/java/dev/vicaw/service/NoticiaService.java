package dev.vicaw.service;

import java.util.List;

import dev.vicaw.model.noticia.Noticia;
import dev.vicaw.model.noticia.output.FeedOutput;
import dev.vicaw.model.noticia.output.NoticiaFeedOutput;
import dev.vicaw.model.noticia.output.NoticiaOutput;

public interface NoticiaService {

    public List<Noticia> list();

    public Noticia getById(Long id);

    public Noticia create(Noticia noticia);

    public NoticiaOutput getBySlug(String slug);

    public FeedOutput getFeedInfo(int pagesize, int page, String categorySlug);

    public List<NoticiaFeedOutput> searchArticle(String query);

}
