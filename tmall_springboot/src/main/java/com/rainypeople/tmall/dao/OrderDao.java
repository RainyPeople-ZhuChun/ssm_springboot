package com.rainypeople.tmall.dao;

import com.rainypeople.tmall.pojo.Order;
import com.rainypeople.tmall.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDao extends JpaRepository<Order,Integer> {
    List<Order> findByUserAndStatusNotOrderByIdDesc(User user, String delete);
}
