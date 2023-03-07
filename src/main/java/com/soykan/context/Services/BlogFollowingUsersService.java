package com.soykan.context.Services;

import com.soykan.context.Blog.BlogFollowingUsers;
import com.soykan.context.Blog.BlogUser;
import com.soykan.context.Repositories.BlogFollowingUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogFollowingUsersService {
    @Autowired
    private BlogFollowingUsersRepository blogFollowingUsersRepository;

    public void followUser(BlogUser loggedInUser, BlogUser userToFollow) {
        BlogFollowingUsers blogFollowingUsers = new BlogFollowingUsers(loggedInUser, userToFollow);
        blogFollowingUsersRepository.save(blogFollowingUsers);
    }

    public boolean isUserFollowing(BlogUser loggedInUser, BlogUser userToFollow) {
        List<BlogFollowingUsers> blogFollowingUsersList = blogFollowingUsersRepository.findAll();
        for (BlogFollowingUsers blogFollowingUsers: blogFollowingUsersList) {
            String loggedInUsername = blogFollowingUsers.getBlogUser().getUsername();
            String anotherUser = blogFollowingUsers.getFollowedBlogUser().getUsername();
            if (loggedInUsername.equals(loggedInUser.getUsername()) && anotherUser.equals(userToFollow.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public void unfollowUser(BlogUser loggedInUser, BlogUser userToFollow) {
        List<BlogFollowingUsers> blogFollowingUsersList = blogFollowingUsersRepository.findAll();
        for (BlogFollowingUsers blogFollowingUsers: blogFollowingUsersList) {
            String loggedInUsername = blogFollowingUsers.getBlogUser().getUsername();
            String anotherUser = blogFollowingUsers.getFollowedBlogUser().getUsername();
            if (loggedInUsername.equals(loggedInUser.getUsername()) && anotherUser.equals(userToFollow.getUsername())) {
                blogFollowingUsersRepository.delete(blogFollowingUsers);
            }
        }
    }

    public List<BlogUser> getFollowedUsersByUser(BlogUser blogUser) {
        List<BlogFollowingUsers> blogFollowingUsersList = blogFollowingUsersRepository.findAll();
        List<BlogUser> followedUsersByUser = new ArrayList<>();
        for (BlogFollowingUsers blogFollowingUser: blogFollowingUsersList) {
            if (blogFollowingUser.getBlogUser().getUsername().equals(blogUser.getUsername())) {
                followedUsersByUser.add(blogFollowingUser.getFollowedBlogUser());
            }
        }
        return followedUsersByUser;
    }
}
