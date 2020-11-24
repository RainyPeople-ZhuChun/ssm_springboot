package com.rainypeople.tmall.web;

import com.rainypeople.tmall.pojo.Property;
import com.rainypeople.tmall.service.PropertyService;
import com.rainypeople.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


}
