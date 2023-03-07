package com.soykan.context.Blog;
import javax.persistence.*;

@Entity
public class BlogUsersAndPages {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id1")
    private BlogUser blogUser1;

    @OneToOne
    @JoinColumn(name = "user_id2")
    private BlogUser blogUser2;

    @Column(nullable = false)
    private Boolean isApproved = false;

    public BlogUsersAndPages(BlogUser blogUser1, BlogUser blogUser2, Boolean isApproved) {
        this.blogUser1 = blogUser1;
        this.blogUser2 = blogUser2;
        this.isApproved = isApproved;
    }

    public BlogUsersAndPages(Long id, BlogUser blogUser1, BlogUser blogUser2, Boolean isApproved) {
        this.id = id;
        this.blogUser1 = blogUser1;
        this.blogUser2 = blogUser2;
        this.isApproved = isApproved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BlogUser getBlogUser1() {
        return blogUser1;
    }

    public void setBlogUser1(BlogUser blogUser1) {
        this.blogUser1 = blogUser1;
    }

    public BlogUser getBlogUser2() {
        return blogUser2;
    }

    public void setBlogUser2(BlogUser blogUser2) {
        this.blogUser2 = blogUser2;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }
}
