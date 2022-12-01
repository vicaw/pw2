package dev.vicaw.service.impl;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.jwt.Claims;

import com.github.slugify.Slugify;

import dev.vicaw.service.CategoryService;
import dev.vicaw.service.UserService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import dev.vicaw.exception.ApiException;
import dev.vicaw.model.category.Category;
import dev.vicaw.model.category.CategoryMapper;
import dev.vicaw.model.category.input.CategoryCreateInput;
import dev.vicaw.model.noticia.Noticia;
import dev.vicaw.model.user.Role;
import dev.vicaw.model.user.User;
import dev.vicaw.model.user.UserMapper;
import dev.vicaw.model.user.input.UserCreateInput;
import dev.vicaw.model.user.input.UserLoginInput;
import dev.vicaw.model.user.output.UserLoginOutput;
import dev.vicaw.repository.CategoryRepository;
import dev.vicaw.repository.UserRepository;

@RequestScoped
public class CategoryServiceImpl implements CategoryService {

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    CategoryMapper categoryMapper;

    @Override
    public List<Category> list() {
        return categoryRepository.listAll();
        // return categoryMapper.toModelList(categoryRepository.listAll());
    }

    @Override
    public List<Noticia> listCategoryArticlesById(Long categoryId) {
        return categoryRepository.findById(categoryId).getNoticias();
    }

    @Override
    public List<Noticia> listCategoryArticlesBySlug(String categorySlug) {
        Optional<Category> category = categoryRepository.findBySlug(categorySlug);
        if (!category.isPresent())
            throw new ApiException(404, "Essa categoria não existe.");

        return category.get().getNoticias();
    }

    @Transactional
    @Override
    public Category create(CategoryCreateInput input) {
        Category category = categoryMapper.toModel(input);

        Slugify slugify = Slugify.builder().build();
        String slug = slugify.slugify(category.getName());
        category.setSlug(slug);

        if (categoryRepository.findBySlug(category.getSlug()).isPresent())
            throw new ApiException(409, "A categoria com o nome '" + category.getName() + "' já existe.");

        categoryRepository.persist(category);

        return category;

    }

}
