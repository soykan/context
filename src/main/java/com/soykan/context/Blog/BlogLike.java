package com.soykan.context.Blog;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class BlogLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private BlogUser blogUser;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "article_id")
    private BlogArticle blogArticle;

    @Column(nullable = false)
    private LocalDate date;

    public BlogLike() {

    }

    public BlogLike(Long id, BlogUser blogUser, BlogArticle blogArticle, LocalDate date) {
        Id = id;
        this.blogUser = blogUser;
        this.blogArticle = blogArticle;
        this.date = date;
    }

    public BlogLike(BlogUser blogUser, BlogArticle blogArticle, LocalDate date) {
        this.blogUser = blogUser;
        this.blogArticle = blogArticle;
        this.date = date;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public BlogUser getBlogUser() {
        return blogUser;
    }

    public void setBlogUser(BlogUser blogUser) {
        this.blogUser = blogUser;
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
