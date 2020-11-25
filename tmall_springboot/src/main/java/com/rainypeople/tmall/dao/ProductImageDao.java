package com.rainypeople.tmall.dao;

import com.rainypeople.tmall.pojo.Product;
import com.rainypeople.tmall.pojo.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageDao extends JpaRepository<ProductImage,Integer> {
    public List<ProductImage> findByProductAndTypeOrderByIdDesc(Product product, String type);
}
