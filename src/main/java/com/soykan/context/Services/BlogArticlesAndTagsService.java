package com.soykan.context.Services;

import com.soykan.context.Blog.BlogArticle;
import com.soykan.context.Blog.BlogArticlesAndTags;
import com.soykan.context.Blog.BlogTag;
import com.soykan.context.Repositories.BlogArticlesAndTagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogArticlesAndTagsService {

    @Autowired
    private BlogArticlesAndTagsRepository blogArticlesAndTagsRepository;


    public BlogArticlesAndTags createBlogArticlesAndTags(BlogArticle blogArticle, BlogTag blogTag) {
        return blogArticlesAndTagsRepository.save(new BlogArticlesAndTags(blogArticle, blogTag));
    }

    public List<BlogTag> getTagsByArticle(BlogArticle blogArticle) {
        List<BlogArticlesAndTags> allBlogArticlesAndTags = blogArticlesAndTagsRepository.findAll();
        List<BlogTag> blogTags = new ArrayList<>();
        for (BlogArticlesAndTags blogArticlesAndTags: allBlogArticlesAndTags) {
            if (blogArticlesAndTags.getBlogArticle().getId() == blogArticle.getId()) {
                blogTags.add(blogArticlesAndTags.getBlogTag());
            }
        }
        return blogTags;
    }

    public void deleteTagsByArticle(BlogArticle blogArticle) {
        List<BlogArticlesAndTags> blogArticlesAndTagsList = blogArticlesAndTagsRepository.findAll();
        for (BlogArticlesAndTags blogArticlesAndTags: blogArticlesAndTagsList) {
            if (blogArticlesAndTags.getBlogArticle().getId() == blogArticle.getId()) {
                blogArticlesAndTagsRepository.delete(blogArticlesAndTags);
            }
        }
    }

    public List<BlogArticle> getArticleByTagId(Long tagId) {
        List<BlogArticlesAndTags> blogArticlesAndTagsList = blogArticlesAndTagsRepository.findAll();
        List<BlogArticle> blogArticleList = new ArrayList<>();
        for (BlogArticlesAndTags blogArticlesAndTags: blogArticlesAndTagsList) {
            if (blogArticlesAndTags.getBlogTag().getId() == tagId) {
                blogArticleList.add(blogArticlesAndTags.getBlogArticle());
            }
        }
        return blogArticleList;
    }


}
