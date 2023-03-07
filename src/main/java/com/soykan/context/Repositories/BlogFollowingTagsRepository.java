package com.soykan.context.Repositories;

import com.soykan.context.Blog.BlogFollowingTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogFollowingTagsRepository extends JpaRepository<BlogFollowingTags, Long> {
}
