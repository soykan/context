package com.soykan.context.Blog;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class BlogComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private BlogUser blogUser;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "article_id", nullable = false)
    private BlogArticle blogArticle;

    @Column(nullable = false)
    private String comment;

    public BlogComment() {

    }

    public BlogComment(Long id, BlogUser blogUser, BlogArticle blogArticle, String comment) {
        this.id = id;
        this.blogUser = blogUser;
        this.blogArticle = blogArticle;
        this.comment = comment;
    }

    public BlogComment(BlogUser blogUser, BlogArticle blogArticle, String comment) {
        this.blogUser = blogUser;
        this.blogArticle = blogArticle;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
