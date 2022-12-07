package dev.vicaw.model.noticia.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleInput {
    private Long id;
    private String slug;
    private String titulo;
    private String subtitulo;
    private String body;
    private String chapeu_feed;
    private String titulo_feed;
    private String resumo_feed;
    private Long categoryId;
    private Long authorId;
}
