package com.rainypeople.tmall.service;

import com.rainypeople.tmall.dao.UserDao;
import com.rainypeople.tmall.pojo.User;
import com.rainypeople.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public Page4Navigator<User> list(int start, int size, int navigatePages) {
        Sort sort=new Sort(Sort.Direction.DESC,"id");
        Pageable pageable=new PageRequest(start,size,sort);
        Page<User> pageFromJPA = userDao.findAll(pageable);

        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }
}