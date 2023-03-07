package com.soykan.context.Services;

import com.soykan.context.Blog.BlogArticle;
import com.soykan.context.Blog.BlogUser;
import com.soykan.context.Blog.BlogUserList;
import com.soykan.context.Repositories.BlogUserListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogUserListService {

    @Autowired
    public BlogUserListRepository blogUserListRepository;

    public void addArticleToUsersReadingList(BlogUser blogUser, BlogArticle blogArticle) {
        BlogUserList blogUserList = new BlogUserList(blogUser, blogArticle);
        blogUserListRepository.save(blogUserList);
    }

    public List<BlogArticle> returnTheSavedArticles(BlogUser blogUser) {
        List<BlogUserList> records = blogUserListRepository.findAll();
        List<BlogArticle> savedArticles = new ArrayList<>();
        for (BlogUserList blogUserList: records) {
            if (blogUserList.getBlogUser().getUsername() == blogUser.getUsername()) {
                savedArticles.add(blogUserList.getBlogArticle());
            }
        }
        return savedArticles;
    }

    public boolean isSaved(BlogArticle blogArticle, BlogUser blogUser) {
        List<BlogUserList> records = blogUserListRepository.findAll();
        for (BlogUserList blogUserList: records) {
            if (blogUserList.getBlogUser().getUsername().equals(blogUser.getUsername()) &&
                blogUserList.getBlogArticle().getId() == blogArticle.getId()) {
                return true;
            }
        }
        return false;
    }

    public void removeFromSavedList(BlogArticle blogArticle, BlogUser blogUser) {
        List<BlogUserList> records = blogUserListRepository.findAll();
        for (BlogUserList blogUserList: records) {
            if (blogUserList.getBlogUser().getUsername().equals(blogUser.getUsername()) &&
                    blogUserList.getBlogArticle().getId() == blogArticle.getId()) {
                blogUserListRepository.delete(blogUserList);
            }
        }
    }
}
