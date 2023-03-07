package com.soykan.context.Blog;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class BlogArticlesAndTags {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "article_id", nullable = false)
    private BlogArticle blogArticle;

    @OneToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private BlogTag blogTag;

    public BlogArticlesAndTags() {

    }

    public BlogArticlesAndTags(Long id, BlogArticle blogArticle, BlogTag blogTag) {
        this.id = id;
        this.blogArticle = blogArticle;
        this.blogTag = blogTag;
    }

    public BlogArticlesAndTags(BlogArticle blogArticle, BlogTag blogTag) {
        this.blogArticle = blogArticle;
        this.blogTag = blogTag;
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

    public BlogTag getBlogTag() {
        return blogTag;
    }

    public void setBlogTag(BlogTag blogTag) {
        this.blogTag = blogTag;
    }
}
