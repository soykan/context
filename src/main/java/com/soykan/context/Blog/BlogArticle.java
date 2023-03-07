package com.soykan.context.Blog;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class BlogArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private BlogUser blogUser;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 10000)
    private String content;

    @Column(nullable = false)
    private Boolean visibility = true;

    @OneToOne
    @JoinColumn(name = "category_id")
    private BlogUserCategory blogUserCategory;

    @Column(nullable = false)
    private LocalDate date;

    /*
    To specify which users page the article will show up
     */
    @OneToOne
    @JoinColumn(name = "id_of_pages_owner_user", nullable = false)
    private BlogUser page;

    public BlogArticle() {

    }

    public BlogArticle(String title, String content, LocalDate date, BlogUser user, BlogUser page) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.blogUser = user;
        this.page = page;
    }

    public BlogArticle(String title, String content, LocalDate date, BlogUser user, BlogUser page, BlogUserCategory blogUserCategory) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.blogUser = user;
        this.page = page;
        this.blogUserCategory = blogUserCategory;
    }

    public BlogArticle(BlogUser blogUser, String title, String content, Boolean visibility, BlogUserCategory blogUserCategory, LocalDate date, BlogUser page) {
        this.blogUser = blogUser;
        this.title = title;
        this.content = content;
        this.visibility = visibility;
        this.blogUserCategory = blogUserCategory;
        this.date = date;
        this.page = page;
    }

    public BlogArticle(Long id, BlogUser blogUser, String title, String content, Boolean visibility, BlogUserCategory blogUserCategory, LocalDate date, BlogUser page) {
        this.id = id;
        this.blogUser = blogUser;
        this.title = title;
        this.content = content;
        this.visibility = visibility;
        this.blogUserCategory = blogUserCategory;
        this.date = date;
        this.page = page;
    }

    public BlogArticle(BlogUser blogUser, String title, String content, Boolean visibility, LocalDate date, BlogUser page) {
        this.blogUser = blogUser;
        this.title = title;
        this.content = content;
        this.visibility = visibility;
        this.date = date;
        this.page = page;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public BlogUserCategory getBlogUserCategory() {
        return blogUserCategory;
    }

    public void setBlogUserCategory(BlogUserCategory blogUserCategory) {
        this.blogUserCategory = blogUserCategory;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BlogUser getPage() {
        return page;
    }

    public void setPage(BlogUser page) {
        this.page = page;
    }


}
