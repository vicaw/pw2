package dev.vicaw.model.category.input;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CategoryInput {
    @Size(min = 3, max = 200, message = "O nome da categoria deve ter entre 3 e 200 caracteres")
    String name;
}
