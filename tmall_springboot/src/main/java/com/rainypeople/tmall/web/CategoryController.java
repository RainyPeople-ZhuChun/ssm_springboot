package com.rainypeople.tmall.web;

import com.rainypeople.tmall.pojo.Category;
import com.rainypeople.tmall.service.CategoryService;
import com.rainypeople.tmall.util.ImageUtil;
import com.rainypeople.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public Page4Navigator<Category> list(@RequestParam(value = "start",defaultValue = "0")int start,
                               @RequestParam(value = "size",defaultValue = "5")int size) throws Exception{
        start=start>0?start:0;
        Page4Navigator<Category> page=categoryService.list(start,size,5);
        return page;
    }

    @PostMapping("categories")
    public Object add(Category bean, MultipartFile image, HttpServletRequest request) throws IOException {
        categoryService.add(bean);
        saveOrUpdateImageFile(bean, image, request);
        return bean;
    }

    public void saveOrUpdateImageFile(Category bean, MultipartFile image, HttpServletRequest request) throws IOException {
        //获取image下的category这个图片文件夹
        File imagefolder=new File(request.getServletContext().getRealPath("img/category"));
        //创建图片文件，以分类id命名
        File file=new File(imagefolder,bean.getId()+".jpg");
        //判断保存图片的父文件夹也就是图片文件夹catagory是否存在，如果不存在则创建
        if (file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        //把图片进行复制
        image.transferTo(file);
        //把图片转换为jpg格式
        BufferedImage img = ImageUtil.change2jpg(file);
        //保存图片
        ImageIO.write(img,"jpg",file);

    }

    @DeleteMapping("categories/{id}")
    public String delete(@PathVariable("id")int id,HttpServletRequest request)throws IOException{
        categoryService.delete(id);
        File imageFolder=new File(request.getServletContext().getRealPath("img/category"));
        File file=new File(id+".jpg");
        file.delete();
        return null;
    }

    @GetMapping("categories/{id}")
    public Category get(@PathVariable("id")int id)throws IOException{
        Category bean=categoryService.get(id);
        return bean;
    }

    @PutMapping("categories/{id}")
    public Category updata(Category bean,MultipartFile image,HttpServletRequest request)throws IOException{
        //获取参数用的是 request.getParameter("name"). 为什么不用 add里的注入一个 Category对象呢？ 因为。。。PUT 方式注入不了
        String name=request.getParameter("name");
        bean.setName(name);
        categoryService.updata(bean);
        if (null!=image){
            //调用方法保存图片
           saveOrUpdateImageFile(bean,image,request);
        }
        return bean;
    }
}
