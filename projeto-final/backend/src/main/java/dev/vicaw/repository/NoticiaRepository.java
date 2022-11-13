package dev.vicaw.repository;

import javax.enterprise.context.ApplicationScoped;

import dev.vicaw.model.Noticia;
import dev.vicaw.repository.entity.NoticiaEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class NoticiaRepository implements PanacheRepository<NoticiaEntity> {
    public Noticia save(Noticia noticia) {
        NoticiaEntity entity = new NoticiaEntity();
        entity.setTitulo(noticia.getTitulo());
        entity.setResumo(noticia.getResumo());
        entity.setBody(noticia.getBody());
        persist(entity);

        return noticia;

    }
}
