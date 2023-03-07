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
public class EditArticleController {

    @Autowired
    public BlogUserService blogUserService;

    @Autowired
    public BlogArticleService blogArticleService;

    @Autowired
    public BlogArticlesAndTagsService blogArticlesAndTagsService;

    @Autowired
    public BlogTagService blogTagService;

    @GetMapping("/edit-article")
    public String editArticleGetMapping(@RequestParam("id") String id,
                                          Model model) {
        BlogArticle blogArticle = blogArticleService.getArticleById(Long.parseLong(id));
        String currentUsersUsername = getUser().getUsername();
        String articleOwnersUsername = blogArticle.getBlogUser().getUsername();
        String categoryName = blogArticle.getBlogUserCategory().getCategory();
        if (!articleOwnersUsername.equals(currentUsersUsername)) {
            return "redirect:/";
        }
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("blogArticle", blogArticle);
        List<BlogUserCategory> blogUserCategoryList = blogUserCategoryService.getCategoriesByUser(getUser());
        model.addAttribute("categories", blogUserCategoryList);
        List<BlogTag> blogTagList = blogArticlesAndTagsService.getTagsByArticle(blogArticle);
        String tags = "";
        boolean first = true;
        for (BlogTag blogTag: blogTagList) {
            if (first) {
                tags += blogTag.getTag();
                first = false;
            } else {
                tags += ", " + blogTag.getTag();
            }
        }
        model.addAttribute("tags", tags);
        model.addAttribute("username", currentUsersUsername);
        return "edit-article";
    }

    @Autowired
    public BlogUserCategoryService blogUserCategoryService;

    @PostMapping("/edit-article")
    public String editArticlePostMapping(@RequestParam("title") String title,
                                         @RequestParam("content") String content,
                                         @RequestParam("tags") String tags,
                                         @RequestParam("id") String id,
                                         @RequestParam("categoryText") String categoryText) {
        String currentUsersUsername = getUser().getUsername();
        BlogArticle blogArticle = blogArticleService.getArticleById(Long.parseLong(id));
        String articleOwnersUsername = blogArticle.getBlogUser().getUsername();
        if (!articleOwnersUsername.equals(currentUsersUsername)) {
            return "redirect:/";
        }
        blogArticle.setTitle(title);
        blogArticle.setContent(content);
        BlogUserCategory blogUserCategory = blogUserCategoryService.addNewCategory(getUser(), categoryText);
        blogArticle.setBlogUserCategory(blogUserCategory);
        blogArticleService.updateArticle(blogArticle);
        blogArticlesAndTagsService.deleteTagsByArticle(blogArticle);
        for (String tag: tags.split(",")) {
            BlogTag blogTag = blogTagService.createTag(tag);
            blogArticlesAndTagsService.createBlogArticlesAndTags(blogArticle, blogTag);
        }
        return "redirect:/article?id=" + id;

    }

    private BlogUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return blogUserService.getUserByUsername(username);
    }
}
