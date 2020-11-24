package com.rainypeople.tmall.web;

import com.rainypeople.tmall.pojo.Property;
import com.rainypeople.tmall.service.PropertyService;
import com.rainypeople.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @GetMapping("categories/{cid}/properties")
    public Page4Navigator<Property> list(@PathVariable("cid")int cid,
                                         @RequestParam(value = "start",defaultValue = "0")int start,
                                         @RequestParam(value = "size",defaultValue = "5")int size){
        start=start>0?start:0;
        Page4Navigator<Property> page = propertyService.list(cid, start, size, 5);

        return page;
    }

    @PostMapping("properties")
    public Property add(@RequestBody Property bean){
        propertyService.add(bean);
        return bean;
    }

    @DeleteMapping("properties/{id}")
    public String delete(@PathVariable("id")int id)throws IOException {
        propertyService.delete(id);
        return null;
    }

    @GetMapping("properties/{id}")
    public Property get(@PathVariable("id")int id)throws IOException{
        Property bean = propertyService.get(id);
        return bean;
    }

    @PutMapping("properties")
    public Property updata(@RequestBody Property bean)throws IOException{
        propertyService.updata(bean);
        return bean;
    }
}
