package com.soykan.context.Repositories;

import com.soykan.context.Blog.BlogUserCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogUserCategoryRepository extends JpaRepository<BlogUserCategory, Long> {
}
