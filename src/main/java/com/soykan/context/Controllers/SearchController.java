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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    public BlogUserService blogUserService;

    @Autowired
    public BlogArticleService blogArticleService;


    @PostMapping("/search")
    public String searchResults(@RequestParam("searchText") String searchText, Model model) {
        System.out.println("HERE IS THE SEARCH TEXT " + searchText);
        model.addAttribute("users", findUsers(searchText));
        model.addAttribute("articles", findArticles(searchText));
        if (getUser() != null) {
            model.addAttribute("loggedIn", true);
        } else {
            model.addAttribute("loggedIn", false);
        }
        return "search-results";
    }

    @GetMapping("/search")
    public String searchGetMapping(Model model) {
        model.addAttribute("users", findUsers(""));
        model.addAttribute("articles", findArticles(""));
        if (getUser() != null) {
            model.addAttribute("loggedIn", true);
        } else {
            model.addAttribute("loggedIn", false);
        }
        return "search-results";
    }

    public List<BlogUser> findUsers(String searchText) {
        return blogUserService.searchUsersByUsername(searchText);
    }

    public List<BlogArticle> findArticles(String searchText) {
        return blogArticleService.searchArticlesByTitle(searchText);
    }

    private BlogUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return blogUserService.getUserByUsername(username);
    }
}
