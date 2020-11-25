package com.rainypeople.tmall.web;

import com.rainypeople.tmall.pojo.Product;
import com.rainypeople.tmall.service.ProductService;
import com.rainypeople.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("categories/{cid}/products")
    public Page4Navigator<Product> list(@PathVariable("cid")int cid,
                                        @RequestParam(value = "start",defaultValue = "0")int start,
                                        @RequestParam(value = "size",defaultValue = "5")int size)throws IOException{
        start=start>0?start:0;
        Page4Navigator<Product> page = productService.list(cid, start, size, 5);
        return page;
    }

    @PostMapping("products")
    public Product add(@RequestBody Product bean)throws IOException{
        bean.setCreateDate(new Date());
        productService.add(bean);
        return bean;
    }

    @DeleteMapping("products/{id}")
    public String delete(@PathVariable("id")int id)throws IOException{
        productService.delete(id);
        return null;
    }

    @GetMapping("products/{id}")
    public Product get(@PathVariable("id")int id)throws IOException{
        Product bean = productService.get(id);
        return bean;
    }

    @PutMapping("products")
    public Object updata(@RequestBody Product bean)throws IOException{
        productService.update(bean);
        return bean;
    }

}
