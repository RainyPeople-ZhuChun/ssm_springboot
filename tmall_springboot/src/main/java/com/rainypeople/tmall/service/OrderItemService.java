package com.rainypeople.tmall.service;

import com.rainypeople.tmall.dao.OrderItemDao;
import com.rainypeople.tmall.pojo.Order;
import com.rainypeople.tmall.pojo.OrderItem;
import com.rainypeople.tmall.pojo.Product;
import com.rainypeople.tmall.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    OrderItemDao orderItemDao;
    @Autowired
    ProductImageService productImageService;

    public void fill(List<Order> orders) {
        for (Order order:orders){
            fill(order);
        }
    }

    public void fill(Order order) {
        List<OrderItem> orderItems=listByOrder(order);
        float total=0;
        int totalNumber=0;
        for (OrderItem orderItem:orderItems){
            total+=orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
            totalNumber+=orderItem.getNumber();
            productImageService.setFirstProductImage(orderItem.getProduct());
        }
        order.setOrderItems(orderItems);
        order.setTotal(total);
        order.setTotalNumber(totalNumber);
    }

    public List<OrderItem> listByOrder(Order order) {
        return orderItemDao.findByOrderOrderByIdDesc(order);
    }

    public int getSaleCount(Product product) {
        List<OrderItem> ois =listByProduct(product);
        int result =0;
        for (OrderItem oi : ois) {
            if(null!=oi.getOrder())
                if(null!= oi.getOrder() && null!=oi.getOrder().getPayDate())
                    result+=oi.getNumber();
        }
        return result;
    }

    private List<OrderItem> listByProduct(Product product) {
        return orderItemDao.findByProduct(product);
    }

    public List<OrderItem> listByUser(User user) {
        return orderItemDao.findByUserAndOrderIsNull(user);
    }

    public void updata(OrderItem oi) {
        orderItemDao.save(oi);
    }

    public void add(OrderItem oi) {
        orderItemDao.save(oi);
    }

    public OrderItem get(int id) {
        return orderItemDao.getOne(id);
    }
}
