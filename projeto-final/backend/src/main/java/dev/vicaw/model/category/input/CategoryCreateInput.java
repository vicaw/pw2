package dev.vicaw.model.category.input;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CategoryCreateInput {
    @NotBlank
    String name;
}
