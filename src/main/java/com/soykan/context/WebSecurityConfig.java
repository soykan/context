package com.soykan.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().csrfTokenRepository(csrfTokenRepository()).ignoringAntMatchers("/signup").and()
                .authorizeRequests()
                .antMatchers("/", "/search", "/article", "/css/**", "/js/**").permitAll()
                .antMatchers("/signup", "/login").anonymous()
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                .loginPage("/login")
                    .defaultSuccessUrl("/", true)
                .permitAll()
                .and()
                    .oauth2Login()
                    .loginPage("/login")
                    .defaultSuccessUrl("/signup-with-oauth2", true)
                    .permitAll()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"));
    }

    private CsrfTokenRepository csrfTokenRepository()
    {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }
}
