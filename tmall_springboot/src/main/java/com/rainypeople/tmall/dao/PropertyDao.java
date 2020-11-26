package com.rainypeople.tmall.dao;

import com.rainypeople.tmall.pojo.Category;
import com.rainypeople.tmall.pojo.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyDao extends JpaRepository<Property,Integer> {
    Page<Property> findByCategory(Category category, Pageable pageable);
    List<Property> findByCategory(Category category);
}
