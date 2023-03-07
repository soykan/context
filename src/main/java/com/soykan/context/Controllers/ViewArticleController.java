package com.soykan.context.Controllers;

import com.soykan.context.Blog.*;
import com.soykan.context.Repositories.BlogFollowingTagsRepository;
import com.soykan.context.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ViewArticleController {



    @Autowired
    private BlogArticleService blogArticleService;

    @Autowired
    private BlogArticlesAndTagsService blogArticlesAndTagsService;

    @Autowired
    private BlogUserService blogUserService;

    @Autowired
    private BlogLikeService blogLikeService;

    @Autowired
    private BlogCommentService blogCommentService;

    @Autowired
    private BlogUserListService blogUserListService;

    @Autowired
    private BlogFollowingTagsService blogFollowingTagsService;

    @Autowired
    private BlogTagService blogTagService;

    @GetMapping("/article")
    public String viewArticle(@RequestParam("id") Optional<String> id, Model model) {
        if (!isIdParamExists(id)) {
            return "redirect:/";
        }
        BlogUser blogUser = getUser();
        if (blogUser == null) {
            Long articleId = Long.parseLong(id.get());
            BlogArticle blogArticle = getArticleById(articleId);
            if (blogArticle.getVisibility() == false) {
                return "redirect:/";
            }
            String category = "";
            if (blogArticle.getBlogUserCategory().getCategory().strip().equals("")) {
                category = "Uncategorized";
            } else {
                category = blogArticle.getBlogUserCategory().getCategory();
            }
            model.addAttribute("loggedIn", false);
            model.addAttribute("isAnyComments", getComments(blogArticle).size() > 0);
            model.addAttribute("comments", getComments(blogArticle));
            model.addAttribute("blogTagList", getTags(blogArticle));
            model.addAttribute("blogArticle", blogArticle);
            model.addAttribute("articleReleaseDate", getArticleReleaseDate(blogArticle));
            model.addAttribute("likeCount", String.valueOf(getLikeCount(blogArticle)));
            model.addAttribute("category", category);
            return "article";
        }

        Long articleId = Long.parseLong(id.get());
        BlogArticle blogArticle = getArticleById(articleId);
        String currentUsersUsername = blogUser.getUsername();
        String articleOwnersUsername = blogArticle.getBlogUser().getUsername();
        if (blogArticle.getVisibility() == false &&
            !currentUsersUsername.equals(articleOwnersUsername)) {
            return "redirect:/";
        }
        boolean owner = false;
        if (currentUsersUsername.equals(articleOwnersUsername)) {
            owner = true;
        }
        String visibility = "";
        if (blogArticle.getVisibility()) {
            visibility = "Hide";
        } else {
            visibility = "Show";
        }
        String category = "";
        if (blogArticle.getBlogUserCategory().getCategory().strip().equals("")) {
            category = "Uncategorized";
        } else {
            category = blogArticle.getBlogUserCategory().getCategory();
        }
        String saved = "";
        if (blogUserListService.isSaved(blogArticle, getUser())) {
            saved = "Remove from my reading list";
        } else {
            saved = "Add to reading list";
        }
        List<BlogTag> followingTags = getFollowingTagsInTheArticleByActiveUser(blogArticle);
        List<BlogTag> notFollowedTags = getNotFollowedTags(blogArticle, followingTags);

        model.addAttribute("loggedIn", true);
        model.addAttribute("username", getUser().getUsername());
        model.addAttribute("isAnyComments", getComments(blogArticle).size() > 0);
        model.addAttribute("comments", getComments(blogArticle));
        model.addAttribute("notFollowedTags", notFollowedTags);
        model.addAttribute("followingTags", followingTags);
        model.addAttribute("blogArticle", blogArticle);
        model.addAttribute("articleReleaseDate", getArticleReleaseDate(blogArticle));
        model.addAttribute("liked", isArticleLiked(blogUser, blogArticle));
        model.addAttribute("likeCount", String.valueOf(getLikeCount(blogArticle)));
        model.addAttribute("visibility", visibility);
        model.addAttribute("owner", owner);
        model.addAttribute("saved", saved);
        model.addAttribute("category", category);
        return "article";
    }

    private List<BlogTag> getFollowingTagsInTheArticleByActiveUser(BlogArticle blogArticle) {
        List<BlogTag> blogTagList = getTags(blogArticle);
        List<BlogFollowingTags> blogFollowingTags = blogFollowingTagsService.getAllRecords();
        List<BlogTag> followingTags = new ArrayList<>();
        for (BlogTag blogTag: blogTagList) {
            for (BlogFollowingTags blogFollowingTag: blogFollowingTags) {
                if (blogTag.getId() == blogFollowingTag.getBlogTag().getId() &&
                    blogFollowingTag.getBlogUser().getUsername().equals(getUser().getUsername())) {
                    followingTags.add(blogTag);
                }
            }
        }
        return followingTags;
    }

    private List<BlogTag> getNotFollowedTags(BlogArticle blogArticle, List<BlogTag> blogFollowingTags) {
        List<BlogTag> blogTagList = getTags(blogArticle);

        // If not followed any tags return all tags
        if (blogFollowingTags.size() == 0) {
            return blogTagList;
        }
        for (BlogTag blogTag: blogFollowingTags) {
            blogTagList.remove(blogTag);
        }
        return blogTagList;
    }


    private int getLikeCount(BlogArticle blogArticle) {
        return blogLikeService.getLikeCount(blogArticle);
    }

    private BlogArticle getArticleById(Long articleId) {
        return blogArticleService.getArticleById(articleId);
    }

    private boolean isIdParamExists(Optional<String> id) {
        return id.isPresent();
    }

    private String getArticleReleaseDate(BlogArticle blogArticle) {
        LocalDate articleDateWithoutFormat = blogArticle.getDate();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd, YYYY");
        String date = articleDateWithoutFormat.format(dateTimeFormatter);
        return date;
    }

    private boolean isArticleLiked(BlogUser blogUser, BlogArticle blogArticle) {
        return blogLikeService.isLiked(blogUser, blogArticle);
    }

    private BlogUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return blogUserService.getUserByUsername(username);
    }

    private List<BlogTag> getTags(BlogArticle blogArticle) {
        return blogArticlesAndTagsService.getTagsByArticle(blogArticle);
    }

    private List<BlogComment> getComments(BlogArticle blogArticle) {
        return blogCommentService.getCommentsByArticle(blogArticle);
    }

    @PostMapping(value = "/article", params = "like")
    public String likeArticle(@RequestParam("articleId") String id) {
        BlogArticle blogArticle = blogArticleService.getArticleById(Long.parseLong(id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        BlogUser blogUser = blogUserService.getUserByUsername(username);
        BlogLike blogLike = blogLikeService.getBlogLikeIfExists(blogUser, blogArticle);
        if (blogLike == null) {
            blogLikeService.likeArticle(blogUser, blogArticle);
        } else {
            blogLikeService.deleteBlogLike(blogLike);
        }
        return "redirect:/article?id=" + id;
    }

    @PostMapping(value = "/article", params = "comment")
    public String commentToArticle(@RequestParam("articleId") String id,
                                   @RequestParam("commentText") String commentText) {
        if (commentText.strip().equals("")) {
            return "redirect:/article?id=" + id;
        }
        BlogUser blogUser = getUser();
        BlogArticle blogArticle = getArticleById(Long.parseLong(id));
        BlogComment blogComment = new BlogComment(blogUser, blogArticle, commentText.strip());
        blogCommentService.saveComment(blogComment);
        return "redirect:/article?id=" + id;
    }

    @PostMapping(value = "/article", params = "delete")
    public String deleteArticle(@RequestParam("articleId") String id) {
        System.out.println("DELETE METHOD INVOKED FOR " + id);
        blogArticleService.deleteArticleById(Long.parseLong(id));
        return "redirect:/";
    }

    @PostMapping(value = "/article", params = "edit")
    public String editArticle(@RequestParam("articleId") String id) {
        System.out.println("EDIT METHOD INVOKED FOR " + id);
        return "redirect:/edit-article?id=" + id;
    }

    @PostMapping(value = "/article", params = "hide")
    public String hideArticle(@RequestParam("articleId") String id) {
        System.out.println("HIDE METHOD INVOKED FOR " + id);
        BlogArticle blogArticle = blogArticleService.getArticleById(Long.parseLong(id));
        if (blogArticle.getVisibility()) {
            blogArticle.setVisibility(false);
        } else {
            blogArticle.setVisibility(true);
        }
        blogArticleService.updateArticle(blogArticle);
        return "redirect:/";
    }

    @PostMapping(value = "/article", params = "add-to-reading-list")
    public String addArticleToReadingList(@RequestParam("articleId") String id) {
        System.out.println("ADD ARTICLE TO READING LIST FORM SUBMITTED");
        BlogArticle blogArticle = blogArticleService.getArticleById(Long.parseLong(id));
        BlogUser blogUser = getUser();
        if (blogUserListService.isSaved(blogArticle, blogUser)) {
            blogUserListService.removeFromSavedList(blogArticle, blogUser);
        } else {
            blogUserListService.addArticleToUsersReadingList(blogUser, blogArticle);
        }

        return "redirect:/article?id=" + id;
    }

    @PostMapping(value = "/article", params = "follow")
    public String followOrUnfollowTag(@RequestParam("articleId") String id,
                                      @RequestParam("follow") String tagId) {
        System.out.println("TAG ID: " + tagId);
        BlogUser activeUser = getUser();
        BlogTag blogTag = blogTagService.getTagById(Long.parseLong(tagId));
        blogFollowingTagsService.followOrUnfollow(activeUser, blogTag);
        return "redirect:/article?id=" + id;
    }
}
