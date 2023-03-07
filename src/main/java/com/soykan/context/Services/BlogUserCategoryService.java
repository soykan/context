package com.soykan.context.Services;

import com.soykan.context.Blog.BlogUser;
import com.soykan.context.Blog.BlogUserCategory;
import com.soykan.context.Repositories.BlogUserCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogUserCategoryService {
    @Autowired
    public BlogUserCategoryRepository blogUserCategoryRepository;

    public List<BlogUserCategory> getCategoriesByUser(BlogUser blogUser) {
        List<BlogUserCategory> blogUserCategoryList = blogUserCategoryRepository.findAll();
        List<BlogUserCategory> categoriesRelatedToUser = new ArrayList<>();
        for (BlogUserCategory blogUserCategory: blogUserCategoryList) {
            if (blogUserCategory.getBlogUser().getUsername().equals(blogUser.getUsername())) {
                categoriesRelatedToUser.add(blogUserCategory);
            }
        }
        return categoriesRelatedToUser;
    }

    public BlogUserCategory addNewCategory(BlogUser blogUser, String categoryName) {
        List<BlogUserCategory> blogUserCategoryList = blogUserCategoryRepository.findAll();
        for (BlogUserCategory blogUserCategory: blogUserCategoryList) {
            if (blogUserCategory.getCategory().equals(categoryName) &&
                blogUserCategory.getBlogUser().getUsername().equals(blogUser.getUsername())) {
                return blogUserCategory;
            }
        }
        BlogUserCategory blogUserCategory = new BlogUserCategory();
        blogUserCategory.setBlogUser(blogUser);
        blogUserCategory.setCategory(categoryName);
        blogUserCategoryRepository.save(blogUserCategory);
        return blogUserCategory;
    }
}
