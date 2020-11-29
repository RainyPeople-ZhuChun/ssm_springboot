package com.rainypeople.tmall.service;

import com.rainypeople.tmall.dao.PropertyDao;
import com.rainypeople.tmall.pojo.Category;
import com.rainypeople.tmall.pojo.Property;
import com.rainypeople.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames="properties")
public class PropertyService {

    @Autowired
    PropertyDao propertyDao;
    @Autowired
    CategoryService categoryService;

    @CacheEvict(allEntries = true)
    public void add(Property bean){
        propertyDao.save(bean);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id){
        propertyDao.delete(id);
    }

    @CacheEvict(allEntries = true)
    public void updata(Property bean){
        propertyDao.save(bean);
    }

    @Cacheable(key="'properties-one-'+ #p0")
    public Property get(int id){
        Property bean = propertyDao.findOne(id);
        return bean;
    }

    @Cacheable(key="'properties-cid-'+#p0+'-page-'+#p1 + '-' + #p2 ")
    public Page4Navigator<Property> list(int cid,int start, int size, int navigatePages){
        Category category=categoryService.get(cid);

        Sort sort=new Sort(Sort.Direction.DESC,"id");
        Pageable pageable=new PageRequest(start,size,sort);

        Page<Property> pageFromJPA=propertyDao.findByCategory(category,pageable);

        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    @Cacheable(key="'properties-cid-'+ #p0.id")
    public List<Property> listByCategory(Category category) {
        return propertyDao.findByCategory(category);
    }
}
