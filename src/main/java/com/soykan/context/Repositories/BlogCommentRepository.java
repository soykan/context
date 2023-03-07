package com.soykan.context.Repositories;

import com.soykan.context.Blog.BlogArticle;
import com.soykan.context.Blog.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogCommentRepository extends JpaRepository<BlogComment, Long> {
    public List<BlogComment> findByBlogArticle(BlogArticle blogArticle);
}
