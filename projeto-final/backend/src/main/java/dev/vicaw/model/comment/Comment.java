package dev.vicaw.model.comment;

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
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.vicaw.model.noticia.Noticia;
import dev.vicaw.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "noticia_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Noticia.class, fetch = FetchType.EAGER)
    private Noticia noticia;

    @Column(name = "noticia_id")
    private Long articleId;

    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User author;

    @Column(name = "author_id")
    private Long authorId;

    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Comment.class, fetch = FetchType.EAGER)
    private Comment parentComment;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(columnDefinition = "TEXT")
    private String body;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> children;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
