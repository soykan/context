package com.soykan.context.Blog;

import com.soykan.context.Services.BlogArticleService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class BlogUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String email;

    private String password;

    @Transient
    private String retypePassword;

    @Column(unique = true, nullable = false)
    private String username;

    private String name;

    private String bio;

    private String photo;

    public BlogUser() {
    }

    public BlogUser(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public BlogUser(Long id, String email, String password, String retypePassword, String username, String name, String bio, String photo) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.retypePassword = retypePassword;
        this.username = username;
        this.name = name;
        this.bio = bio;
        this.photo = photo;
    }

    public BlogUser(String email, String password, String retypePassword, String username, String name, String bio, String photo) {
        this.email = email;
        this.password = password;
        this.retypePassword = retypePassword;
        this.username = username;
        this.name = name;
        this.bio = bio;
        this.photo = photo;
    }

    public BlogUser(String email, String password, String retypePassword) {
        this.email = email;
        this.password = password;
        this.retypePassword = retypePassword;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRetypePassword() {
        return retypePassword;
    }

    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
