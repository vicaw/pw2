package dev.vicaw.model.noticia.output;

import java.time.LocalDateTime;

import dev.vicaw.model.category.Category;

public class NoticiaFeedOutput {
    private Long id;
    private String slug;
    private String chapeu_feed;
    private String titulo_feed;
    private String resumo_feed;
    private LocalDateTime createdAt;
    private Category category;
}
