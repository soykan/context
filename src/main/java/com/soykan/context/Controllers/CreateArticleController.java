package com.soykan.context.Controllers;

import com.soykan.context.Blog.BlogArticle;
import com.soykan.context.Blog.BlogTag;
import com.soykan.context.Blog.BlogUser;
import com.soykan.context.Blog.BlogUserCategory;
import com.soykan.context.Services.*;
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
public class CreateArticleController {

    @Autowired
    private BlogArticleService blogArticleService;

    @Autowired
    private BlogTagService blogTagService;

    @Autowired
    private BlogArticlesAndTagsService blogArticlesAndTagsService;

    @Autowired
    private BlogUserService blogUserService;

    @Autowired
    private BlogUserCategoryService blogUserCategoryService;

    @GetMapping("/create-article")
    public String createArticleGetMapping(Model model) {
        try {
            getUser();
        } catch(NullPointerException e) {
            return "redirect:/login";
        }
        BlogUser blogUser = getUser();
        List<BlogUserCategory> blogUserCategoryList = blogUserCategoryService.getCategoriesByUser(blogUser);
        model.addAttribute("categories", blogUserCategoryList);
        model.addAttribute("username", getUser().getUsername());
        return "create-article";
    }

    @PostMapping("/create-article")
    public String createArticlePostMapping(@RequestParam("title") String title,
                                           @RequestParam("content") String content,
                                           @RequestParam("tags") String tags,
                                           @RequestParam("categoryText") String categoryText) {
        if (title.strip().equals("") || content.strip().equals("")) {
            return "redirect:/create-article";
        }
        BlogArticle blogArticle;
        if (categoryText.equals("")) {
            blogArticle = blogArticleService.createArticle(title, content);
        } else {
            BlogUserCategory blogUserCategory = blogUserCategoryService.addNewCategory(getUser(), categoryText);
            blogArticle = blogArticleService.createArticle(title, content, blogUserCategory);
        }

        System.out.println("TITLE: " + title);
        System.out.println("CONTENT:" + content);
        System.out.println("TAGS: " + tags);
        System.out.println("NEW CATEGORY TEXT: " + categoryText);
        String[] tagArray = tags.split(",");
        for (String tag: tagArray) {
            BlogTag blogTag = blogTagService.createTag(tag.strip());
            System.out.println("TAG: " + tag.strip());
            blogArticlesAndTagsService.createBlogArticlesAndTags(blogArticle, blogTag);
        }
        return "redirect:/";
    }

    private BlogUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return blogUserService.getUserByUsername(username);
    }

}
