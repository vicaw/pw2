package dev.vicaw.repository;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import dev.vicaw.model.noticia.Noticia;
import dev.vicaw.model.noticia.output.NoticiaFeedOutput;
import dev.vicaw.model.noticia.output.NoticiaOutput;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class NoticiaRepository implements PanacheRepository<Noticia> {

    public Optional<Noticia> findBySlug(String slug) {
        return find("slug", slug).firstResultOptional();
    }

    public PanacheQuery<NoticiaFeedOutput> getAllFeedInfo() {
        return find("from Noticia n ORDER BY n.createdAt DESC").project(NoticiaFeedOutput.class);
    }

    public PanacheQuery<NoticiaFeedOutput> getAllFeedInfo(Long category_id) {
        return find("from Noticia n WHERE n.categoryId=?1 ORDER BY n.createdAt DESC", category_id)
                .project(NoticiaFeedOutput.class);
    }

    public List<NoticiaFeedOutput> search(String query) {
        return find(
                "from Noticia n where CONCAT_WS(body, titulo, subtitulo, chapeu_feed, resumo_feed, titulo_feed) LIKE CONCAT('%',?1,'%') ORDER BY n.createdAt DESC",
                query).project(NoticiaFeedOutput.class).list();
    }

}
