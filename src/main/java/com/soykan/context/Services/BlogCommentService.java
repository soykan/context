package com.soykan.context.Services;

import com.soykan.context.Blog.BlogArticle;
import com.soykan.context.Blog.BlogComment;
import com.soykan.context.Repositories.BlogCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogCommentService {

    @Autowired
    public BlogCommentRepository blogCommentRepository;


    public List<BlogComment> getCommentsByArticle(BlogArticle blogArticle) {
        return blogCommentRepository.findByBlogArticle(blogArticle);
    }

    public void saveComment(BlogComment blogComment) {
        blogCommentRepository.save(blogComment);
    }
}
