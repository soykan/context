package com.soykan.context.Controllers;

import com.soykan.context.Blog.BlogArticle;
import com.soykan.context.Blog.BlogUser;
import com.soykan.context.Services.BlogArticleService;
import com.soykan.context.Services.BlogFollowingUsersService;
import com.soykan.context.Services.BlogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserProfileController {

    @Autowired
    private BlogArticleService blogArticleService;

    @Autowired
    private BlogUserService blogUserService;

    @Autowired
    private BlogFollowingUsersService blogFollowingUsersService;

    @GetMapping("/profile/{username}")
    public String userProfileGetMapping(@PathVariable("username") String username, Model model) {
        List<BlogArticle> articlesFromUser = getBlogArticlesByUser(username);
        boolean isProfileViewerLooksOwnProfile = username.equals(getUser().getUsername());
        boolean isUserAlreadyFollowingTheUserOnTheProfilePage = blogFollowingUsersService.isUserFollowing(getUser(), blogUserService.getUserByUsername(username));
        model.addAttribute("isSameUser", isProfileViewerLooksOwnProfile);
        model.addAttribute("isAlreadyFollowing", isUserAlreadyFollowingTheUserOnTheProfilePage);
        model.addAttribute("articles", articlesFromUser);
        model.addAttribute("username", username);
        String name =  blogUserService.getUserByUsername(username).getName();
        if (name == null || name.strip().equals("")) {
            model.addAttribute("name", "User has no name");
        } else {
            model.addAttribute("name", name);
        }
        String bio = blogUserService.getUserByUsername(username).getBio();
        if (bio == null || bio.strip().equals("")) {
            model.addAttribute("bio", "User has no bio");
        } else {
            model.addAttribute("bio", bio);
        }
        model.addAttribute("userHasArticles", articlesFromUser.size() > 0);

        model.addAttribute("articleCount", articlesFromUser.size());
        return "user-profile";
    }

    @PostMapping("/profile/{username}")
    public String followOrUnfollowUser(@PathVariable("username") String username) {
        System.out.println("-----followUser METHOD WITH POST MAPPING EXECUTED!");
        BlogUser loggedInUser = getUser();
        BlogUser userOnProfilePage = blogUserService.getUserByUsername(username);
        boolean isUserAlreadyFollowingTheUserOnTheProfilePage = blogFollowingUsersService.isUserFollowing(loggedInUser, userOnProfilePage);
        if (isUserAlreadyFollowingTheUserOnTheProfilePage) {
            blogFollowingUsersService.unfollowUser(loggedInUser, userOnProfilePage);
        } else {
            blogFollowingUsersService.followUser(loggedInUser, userOnProfilePage);
        }
        return "redirect:/profile/" + username;
    }

    public List<BlogArticle> getBlogArticlesByUser(String username) {
        return blogArticleService.getArticlesFromUser(username);
    }

    private BlogUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return blogUserService.getUserByUsername(username);
    }


}
