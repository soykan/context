package com.soykan.context.Services;

import com.soykan.context.Blog.BlogFollowingTags;
import com.soykan.context.Blog.BlogFollowingUsers;
import com.soykan.context.Blog.BlogTag;
import com.soykan.context.Blog.BlogUser;
import com.soykan.context.Repositories.BlogFollowingTagsRepository;
import com.soykan.context.Repositories.BlogFollowingUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogFollowingTagsService {
    @Autowired
    private BlogFollowingTagsRepository blogFollowingTagsRepository;

    public List<BlogFollowingTags> getAllRecords() {
        return blogFollowingTagsRepository.findAll();
    }

    private boolean isFollowed(BlogUser blogUser, BlogTag blogTag) {
        List<BlogFollowingTags> blogFollowingTags = getAllRecords();
        for (BlogFollowingTags followingTag: blogFollowingTags) {
            if (followingTag.getBlogTag().getId() == blogTag.getId() &&
                followingTag.getBlogUser().getUsername().equals(blogUser.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public void followOrUnfollow(BlogUser blogUser, BlogTag blogTag) {
        boolean isAlreadyFollowed = isFollowed(blogUser, blogTag);
        if (isAlreadyFollowed) {
            BlogFollowingTags blogFollowingTag = getRecord(blogUser, blogTag);
            blogFollowingTagsRepository.delete(blogFollowingTag);
        } else {
            BlogFollowingTags blogFollowingTags = new BlogFollowingTags(blogUser, blogTag);
            blogFollowingTagsRepository.save(blogFollowingTags);
        }
    }

    public BlogFollowingTags getRecord(BlogUser blogUser, BlogTag blogTag) {
        List<BlogFollowingTags> blogFollowingTags = getAllRecords();
        for (BlogFollowingTags blogFollowingTag: blogFollowingTags) {
            if (blogFollowingTag.getBlogUser().getUsername().equals(blogUser.getUsername()) &&
                blogFollowingTag.getBlogTag().getId() == blogTag.getId()) {
                return blogFollowingTag;
            }
        }
        return null;
    }

    public List<BlogFollowingTags> getFollowedTagsByUser(BlogUser blogUser) {
        List<BlogFollowingTags> blogFollowingTags = getAllRecords();
        List<BlogFollowingTags> blogFollowingTagsList = new ArrayList<>();
        for (BlogFollowingTags blogFollowingTag: blogFollowingTags) {
            if (blogFollowingTag.getBlogUser().getUsername().equals(blogUser.getUsername())) {
                blogFollowingTagsList.add(blogFollowingTag);
            }
        }
        return blogFollowingTagsList;
    }
}
