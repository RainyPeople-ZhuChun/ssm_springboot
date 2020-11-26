package com.rainypeople.tmall.web;

import com.rainypeople.tmall.pojo.Product;
import com.rainypeople.tmall.pojo.Property;
import com.rainypeople.tmall.pojo.PropertyValue;
import com.rainypeople.tmall.service.ProductService;
import com.rainypeople.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class PropertyValueController {

    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    ProductService productService;

    @GetMapping("products/{pid}/propertyValues")
    public Object list(@PathVariable("pid")int pid)throws IOException {
        Product product = productService.get(pid);
        propertyValueService.init(product);
        List<PropertyValue> propertyValues=propertyValueService.list(product);
        return propertyValues;
    }

    @PutMapping("propertyValues")
    public Object updata(@RequestBody PropertyValue bean)throws IOException{
        propertyValueService.updata(bean);
        return bean;
    }
}
