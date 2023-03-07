package com.soykan.context.Blog;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class BlogUserList {
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


    public BlogUserList() {

    }
    public BlogUserList(Long id, BlogUser blogUser, BlogArticle blogArticle) {
        this.id = id;
        this.blogUser = blogUser;
        this.blogArticle = blogArticle;
    }

    public BlogUserList(BlogUser blogUser, BlogArticle blogArticle) {
        this.blogUser = blogUser;
        this.blogArticle = blogArticle;
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
}
