package com.soykan.context.Controllers;

import com.soykan.context.Blog.BlogUser;
import com.soykan.context.Services.BlogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.image.ByteLookupTable;
import java.util.Optional;

@Controller
public class SettingsController {

    @Autowired
    public BlogUserService blogUserService;

    @GetMapping("/settings")
    public String settingsGetMapping(Model model) {
        BlogUser blogUser = getUser();
        boolean isPasswordPresent = false;
        try {
            String pass = getUser().getPassword();
            if (pass.strip().equals("")){
                isPasswordPresent = false;
            } else {
                isPasswordPresent = true;
            }
        } catch (NullPointerException e) {
            isPasswordPresent = false;
        }
        model.addAttribute("blogUser", blogUser);
        model.addAttribute("username", blogUser.getUsername());
        model.addAttribute("isPasswordPresent", isPasswordPresent);
        return "settings";
    }

    /*
    @PostMapping(value = "/settings", params = "updateProfilePhotoSubmitButton")
    public String profilePhotoSubmit() {
        return "redirect:/profile/" + getUser().getUsername();
    }
    */

    @PostMapping(value = "/settings", params = "nameAndBioSubmitButton")
    public String nameAndBioSubmit(@RequestParam("name") String name,
                                   @RequestParam("bio") String bio) {
        BlogUser blogUser = getUser();
        blogUser.setName(name);
        blogUser.setBio(bio);
        blogUserService.updateUser(blogUser);
        return "redirect:/profile/" + getUser().getUsername();
    }

    @PostMapping(value = "/settings", params = "newPassSubmitButton")
    public String newPassSubmit(@RequestParam("pass1") String pass1,
                                @RequestParam("pass2") String pass2,
                                @RequestParam("currentPassword") String currentPassword) {

        BlogUser blogUser = getUser();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (pass1.equals(pass2) && bCryptPasswordEncoder.matches(currentPassword, blogUser.getPassword()) ) {
            blogUser.setPassword(bCryptPasswordEncoder.encode(pass1));
            blogUserService.updateUser(blogUser);
            return "redirect:/logout";
        } else {
            return "redirect:/settings";
        }
    }

    private BlogUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return blogUserService.getUserByUsername(username);
    }
}
