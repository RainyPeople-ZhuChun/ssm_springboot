package com.rainypeople.tmall.dao;

import com.rainypeople.tmall.pojo.Category;
import com.rainypeople.tmall.pojo.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product,Integer> {
    Page<Product> findByCategory(Category category,Pageable pageable);
}
