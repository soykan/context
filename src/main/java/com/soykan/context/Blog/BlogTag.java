package com.soykan.context.Blog;

import javax.persistence.*;

@Entity
public class BlogTag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String tag;

    public BlogTag(Long id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public BlogTag() {

    }

    public BlogTag(String tag) {
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
