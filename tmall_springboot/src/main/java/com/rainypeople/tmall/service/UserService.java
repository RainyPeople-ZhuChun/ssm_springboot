package com.rainypeople.tmall.service;

import com.rainypeople.tmall.dao.UserDao;
import com.rainypeople.tmall.pojo.User;
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

@Service
@CacheConfig(cacheNames = "users")
public class UserService {

    @Autowired
    UserDao userDao;

    @Cacheable(key = "'users-page-'+p0+'-'+#p1")
    public Page4Navigator<User> list(int start, int size, int navigatePages) {
        Sort sort=new Sort(Sort.Direction.DESC,"id");
        Pageable pageable=new PageRequest(start,size,sort);
        Page<User> pageFromJPA = userDao.findAll(pageable);

        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    public boolean isExist(String name) {
        User user = getByName(name);
        return null!=user;
    }

    @Cacheable(key = "'users-one-name-'+#p0")
    public User getByName(String name) {
        return userDao.findByName(name);
    }

    @CacheEvict(allEntries = true)
    public void add(User user) {
        userDao.save(user);
    }


    @Cacheable(key = "'users-one-name-'+#p0+'-password-'+#p1")
    public User get(String name, String password) {
        return userDao.getByNameAndPassword(name,password);
    }
}
