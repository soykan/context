package com.soykan.context.Blog;

import javax.persistence.*;

@Entity
public class BlogUserCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private BlogUser blogUser;
    //private Long userId;

    @Column(nullable = false)
    private String category;

    public BlogUserCategory() {

    }

    public BlogUserCategory(BlogUser blogUser, String category) {
        this.blogUser = blogUser;
        this.category = category;
    }

    public BlogUserCategory(Long id, BlogUser blogUser, String category) {
        this.id = id;
        this.blogUser = blogUser;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
