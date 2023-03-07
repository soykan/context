package com.soykan.context.Repositories;

import com.soykan.context.Blog.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogUserRepository extends JpaRepository<BlogUser, Long> {
    public BlogUser findByEmail(String email);
    public BlogUser findByUsername(String username);
}
