package dev.vicaw.service;

import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

import dev.vicaw.model.article.Article;
import dev.vicaw.model.article.input.ArticleInput;
import dev.vicaw.model.article.input.ArticleFormInput;
import dev.vicaw.model.article.output.FeedOutput;
import dev.vicaw.model.article.output.ArticleFeedOutput;
import dev.vicaw.model.article.output.ArticleOutput;

public interface ArticleService {

    public List<Article> list(int authorId);

    public Article getById(Long id);

    public Article create(InputStream coverImage, String coverImageName, @Valid ArticleInput article);

    public Article edit(ArticleFormInput body);

    public ArticleOutput getBySlug(String slug);

    public FeedOutput getFeedInfo(int pagesize, int page, String categorySlug);

    public List<ArticleFeedOutput> searchArticle(String query);

}
