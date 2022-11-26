package dev.vicaw.service;

import java.util.List;

import dev.vicaw.model.category.Category;
import dev.vicaw.model.category.input.CategoryCreateInput;
import dev.vicaw.model.noticia.Noticia;

public interface CategoryService {
    public List<Category> list();

    public Category create(CategoryCreateInput input);

    public List<Noticia> listCategoryArticlesById(Long categoryId);

    public List<Noticia> listCategoryArticlesBySlug(String categorySlug);
}
