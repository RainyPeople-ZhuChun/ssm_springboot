package com.rainypeople.tmall.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class adminPageController {

    @GetMapping("/admin")
    public String admin(){
        return "redirect:admin_category_list";
    }

    @GetMapping("/admin_category_list")
    public String listCategory(){
        return "admin/listCategory";
    }

    @GetMapping("/admin_category_edit")
    public String edit(){
        return "admin/editCategory";
    }
}
