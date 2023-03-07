package com.soykan.context.Repositories;

import com.soykan.context.Blog.BlogUserList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogUserListRepository extends JpaRepository<BlogUserList, Long> {
}
