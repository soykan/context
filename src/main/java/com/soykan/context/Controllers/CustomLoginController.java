package com.soykan.context.Controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class CustomLoginController {
    @GetMapping("/login")
    public String viewLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.isAuthenticated()
            && !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) ) {
                return "redirect:/";
            }
        }
        return "login";
    }
}
