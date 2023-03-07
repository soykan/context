package com.soykan.context.Services;

import com.soykan.context.Blog.BlogUser;
import com.soykan.context.Repositories.BlogUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogUserService implements UserDetailsService {

    @Autowired
    private BlogUserRepository blogUserRepository;

    public void createUser(BlogUser blogUser) {
        blogUserRepository.save(blogUser);
    }

    public boolean checkUserCredentials(BlogUser blogUser) {
        try {
            BlogUser userAtDb = blogUserRepository.findByEmail(blogUser.getEmail());
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(blogUser.getPassword(), userAtDb.getPassword())) {
                return true;
            }
        } catch (NullPointerException ex) {
            System.out.println("NullPointerException in checkUserCrendentials on BlogUserService.java");;
        }
        return false;
    }

    public boolean checkUserExists(String username) {
        if (blogUserRepository.findByUsername(username) != null) {
            System.out.println("USER EXISTS!");
            return true;
        } else {
            System.out.println("USER DOES NOT EXIST!");
            return false;
        }

    }

    public BlogUser getUserByUsername(String username) {
        return blogUserRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return blogUserRepository.findByUsername(username);
    }

    public List<BlogUser> searchUsersByUsername(String searchText) {
        List<BlogUser> blogUserList = blogUserRepository.findAll();
        List<BlogUser> results = new ArrayList<>();
        for (BlogUser blogUser: blogUserList) {
            if (blogUser.getUsername().contains(searchText)) {
                results.add(blogUser);
            }
        }
        return results;
    }

    public BlogUser updateUser(BlogUser blogUser) {
        blogUserRepository.save(blogUser);
        return blogUser;
    }
}
