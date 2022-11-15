package dev.vicaw.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import dev.vicaw.model.Noticia;
import dev.vicaw.repository.entity.NoticiaEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.stream.Collectors;

@ApplicationScoped
public class NoticiaRepository implements PanacheRepository<NoticiaEntity> {
    public List<Noticia> list() {
        return listAll().stream().map(e -> new Noticia(e.getId(), e.getTitulo(), e.getResumo(), e.getBody()))
                .collect(Collectors.toList());

    }

    public Noticia save(Noticia noticia) {
        NoticiaEntity entity = new NoticiaEntity();
        entity.setTitulo(noticia.getTitulo());
        entity.setResumo(noticia.getResumo());
        entity.setBody(noticia.getBody());
        persist(entity);

        return noticia;

    }
}
