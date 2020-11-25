package com.rainypeople.tmall.web;

import com.rainypeople.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertyValueController {

    @Autowired
    PropertyValueService propertyValueService;

    @GetMapping("")
    public Object list(int pid){
        return null;
    }
}
