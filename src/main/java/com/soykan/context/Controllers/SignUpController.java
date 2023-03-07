package com.soykan.context.Controllers;

import com.soykan.context.Blog.BlogUser;
import com.soykan.context.Services.BlogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SignUpController {

    @Autowired
    private BlogUserService blogUserService;

    @GetMapping("/signup-with-oauth2")
    public String signUpWithOauth(OAuth2AuthenticationToken token) {
        if (token != null) {
            String provider = token.getAuthorizedClientRegistrationId();
            if (provider.equals("google") || provider.equals("facebook")) {
                String username = token.getPrincipal().getName();
                String email = token.getPrincipal().getAttribute("email").toString();
                if (!blogUserService.checkUserExists(username)) {
                    BlogUser oauthUser = new BlogUser(username, email);
                    blogUserService.createUser(oauthUser);
                }
            }
        }
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        /*
        boolean isAuthenticated = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (!authentication.isAuthenticated()) {
                model.addAttribute("blogUser", new BlogUser());
                return "signup";
            }
        }
        return "redirect:/"; */
        model.addAttribute("blogUser", new BlogUser());
        return "signup";

    }

    @PostMapping("/signup")
    public String signUpPostMapping(@ModelAttribute BlogUser blogUser) {
        System.out.println("SIGN UP POST MAPPING");
        String pass1 = blogUser.getPassword().trim();
        String pass2 = blogUser.getRetypePassword().trim();
        System.out.println(pass1);
        System.out.println(pass2);
        if (pass1.equals(pass2) && !pass1.equals("")) {
            System.out.println("passwords are equal");
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            blogUser.setPassword(passwordEncoder.encode(pass1));
            blogUserService.createUser(blogUser);
        }
        return "redirect:/login";
    }

}
