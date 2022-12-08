package dev.vicaw.model.article.output;

import java.time.LocalDateTime;

import dev.vicaw.model.category.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleFeedOutput {
    private Long id;
    private String slug;
    private String chapeu_feed;
    private String titulo_feed;
    private String resumo_feed;
    private LocalDateTime createdAt;
    private Category category;
}
