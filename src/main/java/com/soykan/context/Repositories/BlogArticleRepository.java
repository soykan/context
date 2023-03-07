package com.soykan.context.Repositories;

import com.soykan.context.Blog.BlogArticle;
import com.soykan.context.Blog.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogArticleRepository extends JpaRepository<BlogArticle, Long> {
    public List<BlogArticle> findAllByBlogUser(BlogUser blogUser);
}
