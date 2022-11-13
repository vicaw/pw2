package dev.vicaw.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Noticia {
    private long id;
    private String titulo;
    private String resumo;
    private String body;
}
