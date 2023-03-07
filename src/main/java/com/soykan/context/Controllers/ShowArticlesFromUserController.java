package com.soykan.context.Controllers;

import com.soykan.context.Blog.BlogArticle;
import com.soykan.context.Blog.BlogUser;
import com.soykan.context.Services.BlogArticleService;
import com.soykan.context.Services.BlogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ShowArticlesFromUserController {

    @Autowired
    private BlogArticleService blogArticleService;

    @Autowired
    private BlogUserService blogUserService;

    @GetMapping("/articles")
    public String showArticlesFromUserGetMapping(@RequestParam("from") String username, Model model) {
        List<BlogArticle> articlesFromUser = blogArticleService.getArticlesFromUser(username);
        List<BlogArticle> visibleArticlesFromUser = new ArrayList<>();
        for (BlogArticle blogArticle: articlesFromUser) {
            if (blogArticle.getVisibility()) {
                visibleArticlesFromUser.add(blogArticle);
            }
        }
        if (getUser() != null) {
            model.addAttribute("loggedIn", true);
            model.addAttribute("username", getUser().getUsername());
        } else {
            model.addAttribute("loggedIn", false);
        }
        model.addAttribute("isNoArticle", visibleArticlesFromUser.size() == 0);
        model.addAttribute("articlesFromUser", visibleArticlesFromUser);
        model.addAttribute("username", username);
        return "articles-from-user";
    }

    private BlogUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return blogUserService.getUserByUsername(username);
    }

}
