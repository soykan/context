package com.soykan.context.Repositories;

import com.soykan.context.Blog.BlogFollowingUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogFollowingUsersRepository extends JpaRepository<BlogFollowingUsers, Long> {
}
