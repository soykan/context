package com.soykan.context.Blog;

import javax.persistence.*;

@Entity
public class BlogFollowingUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private BlogUser blogUser;

    @OneToOne
    @JoinColumn(name = "followed_id", nullable = false)
    private BlogUser followedBlogUser;

    public BlogFollowingUsers() {

    }

    public BlogFollowingUsers(Long id, BlogUser blogUser, BlogUser followedBlogUser) {
        this.id = id;
        this.blogUser = blogUser;
        this.followedBlogUser = followedBlogUser;
    }

    public BlogFollowingUsers(BlogUser blogUser, BlogUser followedBlogUser) {
        this.blogUser = blogUser;
        this.followedBlogUser = followedBlogUser;
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

    public BlogUser getFollowedBlogUser() {
        return followedBlogUser;
    }

    public void setFollowedBlogUser(BlogUser followedBlogUser) {
        this.followedBlogUser = followedBlogUser;
    }
}
