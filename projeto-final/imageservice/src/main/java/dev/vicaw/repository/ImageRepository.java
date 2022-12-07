package dev.vicaw.repository;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import dev.vicaw.model.Image;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ImageRepository implements PanacheRepository<Image> {
    public Optional<Image> findByName(String name) {
        return find("name", name).firstResultOptional();
    }
}
