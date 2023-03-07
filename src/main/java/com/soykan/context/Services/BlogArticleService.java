package com.soykan.context.Services;

import com.soykan.context.Blog.BlogArticle;
import com.soykan.context.Blog.BlogUser;
import com.soykan.context.Blog.BlogUserCategory;
import com.soykan.context.Repositories.BlogArticleRepository;
import net.bytebuddy.asm.Advice;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogArticleService {

    @Autowired
    public BlogArticleRepository blogArticleRepository;

    @Autowired
    public BlogUserService blogUserService;

    @Autowired
    public BlogArticlesAndTagsService blogArticlesAndTagsService;

    @Autowired
    public BlogUserCategoryService blogUserCategoryService;
    public BlogArticle createArticle(String title, String content) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        BlogUser user = blogUserService.getUserByUsername(username);
        LocalDate date = LocalDate.now();
        BlogUserCategory blogUserCategory = blogUserCategoryService.addNewCategory(user, "");
        BlogArticle blogArticle = new BlogArticle(title, content, date, user, user, blogUserCategory);
        return blogArticleRepository.save(blogArticle);
    }

    public BlogArticle createArticle(String title, String content, BlogUserCategory blogUserCategory) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        BlogUser user = blogUserService.getUserByUsername(username);
        LocalDate date = LocalDate.now();
        BlogArticle blogArticle = new BlogArticle(title, content, date, user, user, blogUserCategory);
        return blogArticleRepository.save(blogArticle);
    }

    public void updateArticle(BlogArticle blogArticle) {
        blogArticleRepository.save(blogArticle);
    }

    public List<BlogArticle> getArticlesFromUser(String username) {
        BlogUser blogUser = blogUserService.getUserByUsername(username);
        return blogArticleRepository.findAllByBlogUser(blogUser);
    }

    public BlogArticle getArticleById(Long id) {
        return blogArticleRepository.getById(id);
    }

    public void deleteArticleById(Long id) {
        BlogArticle blogArticle = blogArticleRepository.getById(id);
        blogArticleRepository.delete(blogArticle);
    }

    public List<BlogArticle> searchArticlesByTitle(String searchText) {
        List<BlogArticle> blogArticleList = blogArticleRepository.findAll();
        List<BlogArticle> results = new ArrayList<>();
        for (BlogArticle blogArticle: blogArticleList) {
            if (blogArticle.getTitle().contains(searchText)) {
                results.add(blogArticle);
            }
        }
        return results;
    }
}
