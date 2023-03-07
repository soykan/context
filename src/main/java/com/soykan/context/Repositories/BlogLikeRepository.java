package com.soykan.context.Repositories;

import com.soykan.context.Blog.BlogLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogLikeRepository extends JpaRepository<BlogLike, Long> {
}
