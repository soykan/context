package com.soykan.context.Controllers;

import com.soykan.context.Blog.BlogArticle;
import com.soykan.context.Blog.BlogUser;
import com.soykan.context.Services.BlogUserListService;
import com.soykan.context.Services.BlogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReadingListController {

    @Autowired
    public BlogUserListService blogUserListService;

    @Autowired
    public BlogUserService blogUserService;

    @GetMapping("/reading-list")
    public String readingList(Model model) {
        List<BlogArticle> blogArticleList = blogUserListService.returnTheSavedArticles(getUser());
        model.addAttribute("articleList", blogArticleList);
        model.addAttribute("username", getUser().getUsername());
        model.addAttribute("isArticleListEmpty", blogArticleList.size() == 0);
        return "reading-list";
    }

    private BlogUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return blogUserService.getUserByUsername(username);
    }
}
