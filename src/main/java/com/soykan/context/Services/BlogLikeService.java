package com.soykan.context.Services;

import com.soykan.context.Blog.BlogArticle;
import com.soykan.context.Blog.BlogLike;
import com.soykan.context.Blog.BlogUser;
import com.soykan.context.Repositories.BlogLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BlogLikeService {

    @Autowired
    private BlogLikeRepository blogLikeRepository;

    public BlogLike likeArticle(BlogUser blogUser, BlogArticle blogArticle) {
        LocalDate localDate = LocalDate.now();
        BlogLike blogLike = new BlogLike(blogUser, blogArticle, localDate);
        return blogLikeRepository.save(blogLike);
    }

    public BlogLike getBlogLikeIfExists(BlogUser blogUser, BlogArticle blogArticle) {
        List<BlogLike> blogLikeList = blogLikeRepository.findAll();
        for (BlogLike blogLike: blogLikeList) {
            if (blogLike.getBlogUser().getId() == blogUser.getId()
                    && blogLike.getBlogArticle().getId() == blogArticle.getId()) {
                return blogLike;
            }
        }
        return null;
    }

    public int getLikeCount(BlogArticle blogArticle) {
        return blogLikeRepository.findAll().size();
    }

    public boolean isLiked(BlogUser blogUser, BlogArticle blogArticle) {
        List<BlogLike> blogLikeList = blogLikeRepository.findAll();
        for (BlogLike blogLike: blogLikeList) {
            if (blogLike.getBlogUser().getId() == blogUser.getId()
                    && blogLike.getBlogArticle().getId() == blogArticle.getId()) {
                return true;
            }
        }
        return false;
    }

    public void deleteBlogLike(BlogLike blogLike) {
        blogLikeRepository.delete(blogLike);
    }
}
