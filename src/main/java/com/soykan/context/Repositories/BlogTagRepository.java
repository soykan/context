package com.soykan.context.Repositories;

import com.soykan.context.Blog.BlogTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogTagRepository extends JpaRepository<BlogTag, Long> {
}
