package dev.vicaw.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Noticia {
    private long id;
    private String titulo;
    private String resumo;
    private String body;
}
