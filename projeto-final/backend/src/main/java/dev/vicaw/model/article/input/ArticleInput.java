package dev.vicaw.model.article.input;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleInput {
    // private String slug;

    @Size(min = 3, max = 280, message = "O título deve ter entre 3 e 280 caracteres")
    private String titulo;

    @Size(min = 3, max = 280, message = "O subtítulo deve ter entre 3 e 280 caracteres")
    private String subtitulo;

    @Min(value = 10, message = "O corpo da notícia")
    private String body;

    @Size(min = 3, max = 280, message = "O chapéu (Feed) deve ter entre 3 e 280 caracteres")
    private String chapeu_feed;

    @Size(min = 3, max = 280, message = "O título (Feed) deve ter entre 3 e 280 caracteres")
    private String titulo_feed;

    @Size(min = 3, max = 280, message = "O resumo (Feed) deve ter entre 3 e 280 caracteres")
    private String resumo_feed;

    @NotNull(message = "A notícia precisa estar vinculada à uma categoria.")
    private Long categoryId;

    @NotNull(message = "A notícia precisa estar vinculada à um autor.")
    private Long authorId;
}
