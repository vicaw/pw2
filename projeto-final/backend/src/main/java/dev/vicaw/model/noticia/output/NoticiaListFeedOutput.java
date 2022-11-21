package dev.vicaw.model.noticia.output;

import java.time.LocalDateTime;

import javax.persistence.Transient;

import dev.vicaw.model.category.Category;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
// @RegisterForReflection
public class NoticiaListFeedOutput {
    private long id;
    private String slug;
    private String chapeu_feed;
    private String titulo_feed;
    private String resumo_feed;
    private LocalDateTime createdAt;
    private Category category;
}
