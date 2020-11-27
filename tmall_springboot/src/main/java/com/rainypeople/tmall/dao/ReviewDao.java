package com.rainypeople.tmall.dao;

import com.rainypeople.tmall.pojo.Product;
import com.rainypeople.tmall.pojo.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewDao extends JpaRepository<Review,Integer> {

    List<Review> findByProductOrderByIdDesc(Product product);
    int countByProduct(Product product);
}
