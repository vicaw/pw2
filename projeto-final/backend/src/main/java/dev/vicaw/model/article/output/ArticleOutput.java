package dev.vicaw.model.article.output;

import java.time.LocalDateTime;

import dev.vicaw.model.category.Category;
import dev.vicaw.model.user.output.UserOutput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleOutput {
    private Long id;
    private String titulo;
    private String subtitulo;
    private String body;
    private Category category;
    private UserOutput author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
