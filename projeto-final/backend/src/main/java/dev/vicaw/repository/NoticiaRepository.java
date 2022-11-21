package dev.vicaw.repository;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import dev.vicaw.model.noticia.Noticia;
import dev.vicaw.model.noticia.output.NoticiaListFeedOutput;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class NoticiaRepository implements PanacheRepository<Noticia> {

    public Optional<Noticia> findBySlug(String slug) {
        return find("slug", slug).firstResultOptional();
    }

    public List<NoticiaListFeedOutput> getAllFeedInfo() {
        return findAll().project(NoticiaListFeedOutput.class).list();
    }

}
