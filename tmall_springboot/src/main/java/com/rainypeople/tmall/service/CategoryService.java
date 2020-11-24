package com.rainypeople.tmall.service;

import com.rainypeople.tmall.dao.CategoryDao;
import com.rainypeople.tmall.pojo.Category;
import com.rainypeople.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    CategoryDao categoryDao;

    public Page4Navigator<Category> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable=new PageRequest(start,size,sort);
        Page pageFromJPA=categoryDao.findAll(pageable);

        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    public void add(Category bean) {
        categoryDao.save(bean);
    }

    public void delete(int id) {
        categoryDao.delete(id);
    }

    public Category get(int id) {
        return categoryDao.findOne(id);
    }

    public void updata(Category bean) {
        categoryDao.save(bean);
    }
}
