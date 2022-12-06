package dev.vicaw.service;

import java.util.List;

import dev.vicaw.model.category.Category;
import dev.vicaw.model.category.input.CategoryCreateInput;

public interface CategoryService {
    public List<Category> list();

    public Category create(CategoryCreateInput input);

    public Category getBySlug(String slug);

    public Category getById(Long id);
}
