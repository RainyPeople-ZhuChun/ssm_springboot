package com.rainypeople.tmall.web;

import com.rainypeople.tmall.pojo.Order;
import com.rainypeople.tmall.service.OrderItemService;
import com.rainypeople.tmall.service.OrderService;
import com.rainypeople.tmall.util.Page4Navigator;
import com.rainypeople.tmall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class OrderController {

    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderService orderService;

    @GetMapping("orders")
    public Page4Navigator<Order> list(@RequestParam (name = "start",defaultValue = "0")int start,
                                      @RequestParam(name = "size",defaultValue = "5")int size)throws Exception{
        start=start>0?start:0;
        Page4Navigator<Order> page=orderService.list(start,size,5);
        orderItemService.fill(page.getContent());
        orderService.removeOrderFromOrderItem(page.getContent());
        return page;
    }

    @PutMapping("deliveryOrder/{oid}")
    public Object deliveryOrder(@PathVariable int oid)throws Exception{
        Order order=orderService.get(oid);
        order.setDeliveryDate(new Date());
        order.setStatus(OrderService.waitConfirm);
        orderService.updata(order);

        return Result.success();
    }

}
