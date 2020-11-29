package com.rainypeople.tmall.service;

import com.rainypeople.tmall.dao.OrderDao;
import com.rainypeople.tmall.pojo.Order;
import com.rainypeople.tmall.pojo.OrderItem;
import com.rainypeople.tmall.pojo.User;
import com.rainypeople.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CacheConfig(cacheNames = "orders")
public class OrderService {

    public static final String waitPay="waitPay";
    public static final String waitDelivery="waitDelivery";
    public static final String waitConfirm="waitConfirm";
    public static final String waitReview="waitReview";
    public static final String finish="finish";
    public static final String delete="delete";


    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderItemService orderItemService;

    @CacheEvict(allEntries = true)
    public void updata(Order order) {
        orderDao.save(order);
    }

    @Cacheable(key = "'orders-page-'+#p0+'-'+#p1")
    public Page4Navigator<Order> list(int start, int size, int navigatePages) {
        Sort sort=new Sort(Sort.Direction.DESC,"id");
        Pageable pageable=new PageRequest(start,size,sort);
        Page<Order> pageFromJPA=orderDao.findAll(pageable);

        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    public void removeOrderFromOrderItem(List<Order> orders) {
        for (Order order : orders) {
            removeOrderFromOrderItem(order);
        }
    }

    public void removeOrderFromOrderItem(Order order) {
        List<OrderItem> orderItems= order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(null);
        }
    }

    @Cacheable(key= "'orders-one-'+#p0")
    public Order get(int oid) {
        return orderDao.getOne(oid);
    }

    @CacheEvict(allEntries = true)
    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public float add(Order order, List<OrderItem> ois) {
        float total = 0;
        add(order);

        if(false) {
            throw new RuntimeException();
        }

        for (OrderItem oi: ois) {
            oi.setOrder(order);
            orderItemService.updata(oi);
            total+=oi.getProduct().getPromotePrice()*oi.getNumber();
        }
        return total;
    }

    @CacheEvict(allEntries = true)
    public void add(Order order) {
        orderDao.save(order);
    }

    @Cacheable(key="'orders-page-'+#p0+ '-' + #p1")
    public List<Order> listByUserWithoutDelete(User user) {
        List<Order> orders = listByUserAndNotDeleted(user);
        orderItemService.fill(orders);
        return orders;
    }

    public List<Order> listByUserAndNotDeleted(User user) {
        return orderDao.findByUserAndStatusNotOrderByIdDesc(user, OrderService.delete);
    }

    public void cacl(Order o) {
        List<OrderItem> orderItems = o.getOrderItems();
        float total = 0;
        for (OrderItem orderItem : orderItems) {
            total+=orderItem.getProduct().getPromotePrice()*orderItem.getNumber();
        }
        o.setTotal(total);
    }
}
