package com.rainypeople.tmall.service;

import com.rainypeople.tmall.dao.PropertyDao;
import com.rainypeople.tmall.pojo.Category;
import com.rainypeople.tmall.pojo.Property;
import com.rainypeople.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    @Autowired
    PropertyDao propertyDao;
    @Autowired
    CategoryService categoryService;

    public void add(Property bean){
        propertyDao.save(bean);
    }

    public void delete(int id){
        propertyDao.delete(id);
    }

    public void updata(Property bean){
        propertyDao.save(bean);
    }

    public Property get(int id){
        Property bean = propertyDao.findOne(id);
        return bean;
    }

    public Page4Navigator<Property> list(int cid,int start, int size, int navigatePages){
        Category category=categoryService.get(cid);

        Sort sort=new Sort(Sort.Direction.DESC,"id");
        Pageable pageable=new PageRequest(start,size,sort);

        Page<Property> pageFromJPA=propertyDao.findByCategory(category,pageable);

        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    public List<Property> listByCategory(Category category) {
        return propertyDao.findByCategory(category);
    }
}
