package com.soykan.context.Controllers;

import com.soykan.context.Blog.BlogArticle;
import com.soykan.context.Blog.BlogFollowingTags;
import com.soykan.context.Blog.BlogUser;
import com.soykan.context.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogUserService blogUserService;

    @Autowired
    private BlogFollowingUsersService blogFollowingUsersService;

    @Autowired
    private BlogArticleService blogArticleService;

    @Autowired
    private BlogFollowingTagsService blogFollowingTagsService;

    @Autowired
    private BlogArticlesAndTagsService blogArticlesAndTagsService;

    @GetMapping("")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null ) {
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String currentUserName = authentication.getName();
                List<BlogArticle> blogArticles = getArticlesByFollowingTagsAndUsers();
                model.addAttribute("username", currentUserName);
                model.addAttribute("articleList", blogArticles);
                model.addAttribute("loggedIn", true);
                model.addAttribute("isArticleListEmpty", blogArticles.size() == 0);
            }
        }
        return "index";
    }

    private List<BlogArticle> getArticlesByFollowingTagsAndUsers() {
        List<BlogArticle> articlesFromFollowingUsers = new ArrayList<>();
        List<BlogArticle> articlesFromFollowingTags = new ArrayList<>();
        articlesFromFollowingUsers = getArticlesByFollowedUsers();
        articlesFromFollowingTags = getArticlesByFollowingTags();
        List<BlogArticle> articles = new ArrayList<>();
        articles.addAll(articlesFromFollowingUsers);
        articles.addAll(articlesFromFollowingTags);
        return articles;
    }

    private List<BlogArticle> getArticlesByFollowedUsers() {
        List<BlogUser> userList = blogFollowingUsersService.getFollowedUsersByUser(getUser());
        List<BlogArticle> articleList = new ArrayList<>();
        for (BlogUser blogUser: userList) {
            List<BlogArticle> articlesListFromUser = blogArticleService.getArticlesFromUser(blogUser.getUsername());
            articleList.addAll(articlesListFromUser);
        }
        return articleList;
    }

    private List<BlogArticle> getArticlesByFollowingTags() {
        List<BlogFollowingTags> blogFollowingTagsList = blogFollowingTagsService.getFollowedTagsByUser(getUser());
        List<BlogArticle> articleList = new ArrayList<>();
        for (BlogFollowingTags blogFollowingTag: blogFollowingTagsList) {
            Long blogTagId = blogFollowingTag.getBlogTag().getId();
            articleList.addAll(blogArticlesAndTagsService.getArticleByTagId(blogTagId));
        }
        return articleList;
    }

    private BlogUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return blogUserService.getUserByUsername(username);
    }

}
