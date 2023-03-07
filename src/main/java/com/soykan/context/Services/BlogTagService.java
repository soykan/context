package com.soykan.context.Services;

import com.soykan.context.Blog.BlogTag;
import com.soykan.context.Repositories.BlogTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogTagService {

    @Autowired
    private BlogTagRepository blogTagRepository;

    public BlogTag createTag(String tag) {
        List<BlogTag> blogTagList = blogTagRepository.findAll();
        for (BlogTag blogTag: blogTagList) {
            if (tag.equals(blogTag.getTag())) {
                return blogTag;
            }
        }
        return blogTagRepository.save(new BlogTag(tag));
    }

    public BlogTag getTagById(Long id) {
        return blogTagRepository.getById(id);
    }

}
