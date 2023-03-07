package com.soykan.context.Blog;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class BlogFollowingTags {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private BlogUser blogUser;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tag_id", nullable = false)
    private BlogTag blogTag;

    public BlogFollowingTags() {

    }

    public BlogFollowingTags(Long id, BlogUser blogUser, BlogTag blogTag) {
        this.id = id;
        this.blogUser = blogUser;
        this.blogTag = blogTag;
    }

    public BlogFollowingTags(BlogUser blogUser, BlogTag blogTag) {
        this.blogUser = blogUser;
        this.blogTag = blogTag;
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

    public BlogTag getBlogTag() {
        return blogTag;
    }

    public void setBlogTag(BlogTag blogTag) {
        this.blogTag = blogTag;
    }
}
