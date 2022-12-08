package dev.vicaw.model.article;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.vicaw.model.category.Category;
import dev.vicaw.model.comment.Comment;
import dev.vicaw.model.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String subtitulo;

    @Column(columnDefinition = "TEXT")
    private String body;

    private String chapeu_feed;

    private String titulo_feed;

    private String resumo_feed;

    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
    private Category category;

    @Column(name = "category_id")
    private Long categoryId;

    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User author;

    @Column(name = "author_id")
    private Long authorId;

    @JsonIgnore
    @OneToMany(mappedBy = "noticia", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @CreationTimestamp
    public LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}