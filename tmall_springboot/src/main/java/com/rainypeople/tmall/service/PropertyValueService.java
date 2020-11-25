package com.rainypeople.tmall.service;

import com.rainypeople.tmall.dao.PropertyValueDao;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyValueService {
    @Autowired
    PropertyValueDao propertyValueDao;


}
