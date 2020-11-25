package com.rainypeople.tmall.dao;

import com.rainypeople.tmall.pojo.Product;
import com.rainypeople.tmall.pojo.Property;
import com.rainypeople.tmall.pojo.PropertyValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyValueDao extends JpaRepository<PropertyValue,Integer> {
    List<PropertyValue> findByProductOrderByIdDesc(Product product);
    PropertyValue getByProductAndProperty(Product product, Property property);
}
