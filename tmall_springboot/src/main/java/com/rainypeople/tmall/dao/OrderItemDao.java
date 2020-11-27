package com.rainypeople.tmall.dao;

import com.rainypeople.tmall.pojo.Order;
import com.rainypeople.tmall.pojo.OrderItem;
import com.rainypeople.tmall.pojo.Product;
import com.rainypeople.tmall.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemDao extends JpaRepository<OrderItem,Integer> {
    List<OrderItem> findByOrderOrderByIdDesc(Order order);

    List<OrderItem> findByProduct(Product product);

    List<OrderItem> findByUserAndOrderIsNull(User user);
}
