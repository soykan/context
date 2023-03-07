package com.soykan.context.Blog;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class BlogArticleView {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "article_id", nullable = false)
    private BlogArticle blogArticle;

    @Column(nullable = false)
    private LocalDate date;

    public BlogArticleView(Long id, BlogArticle blogArticle, LocalDate date) {
        this.id = id;
        this.blogArticle = blogArticle;
        this.date = date;
    }

    public BlogArticleView(BlogArticle blogArticle, LocalDate date) {
        this.blogArticle = blogArticle;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BlogArticle getBlogArticle() {
        return blogArticle;
    }

    public void setBlogArticle(BlogArticle blogArticle) {
        this.blogArticle = blogArticle;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
