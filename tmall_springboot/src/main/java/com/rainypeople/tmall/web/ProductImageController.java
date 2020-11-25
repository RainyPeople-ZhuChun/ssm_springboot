package com.rainypeople.tmall.web;

import com.rainypeople.tmall.pojo.Product;
import com.rainypeople.tmall.pojo.ProductImage;
import com.rainypeople.tmall.service.ProductImageService;
import com.rainypeople.tmall.service.ProductService;
import com.rainypeople.tmall.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductImageController {

    @Autowired
    ProductImageService productImageService;
    @Autowired
    ProductService productService;

    @GetMapping("products/{pid}/productImages")
    public Object list(@PathVariable("pid")int pid, @RequestParam("type")String type)throws IOException{
        Product product = productService.get(pid);

        if ("single".equals(type)){
            List<ProductImage> singles=productImageService.listSingleProductImages(product,type);
            return singles;
        }else if ("detail".equals(type)){
            List<ProductImage> details=productImageService.listDetailProductImages(product,type);
            return details;
        }else {
            return new ArrayList<>();
        }
    }

    @PostMapping("productImages")
    public Object add(@RequestParam("type")String type,
                      @RequestParam("pid")int pid,
                      MultipartFile image,
                      HttpServletRequest request)throws IOException{
        ProductImage bean=new ProductImage();
        Product product=productService.get(pid);
        bean.setProduct(product);
        bean.setType(type);
        productImageService.add(bean);

        String folder="img/";
        if ("single".equals(type)){
            folder += "productSingle";
        }else {
            folder += "productDetail";
        }
        File imageFolder=new File(request.getServletContext().getRealPath(folder));
        System.out.println(folder);
        String fileName=bean.getId()+".jpg";
        //在imageFolder里面创建以产品图片id命名的图片文件
        File file=new File(imageFolder,fileName);
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
       try {
           //复制图片
           image.transferTo(file);
           //把图片转换为jpg格式
           BufferedImage img = ImageUtil.change2jpg(file);
           //保存图片
           ImageIO.write(img,"jpg",file);
       }catch (Exception e){
           e.printStackTrace();
       }
      if ("single".equals(type)){
          String imageFolder_small=request.getServletContext().getRealPath("img/productSingle_small");
          String imageFolder_middle=request.getServletContext().getRealPath("img/productSingle_middle");
          File f_small=new File(imageFolder_small,fileName);
          File f_middle=new File(imageFolder_middle,fileName);
          if (!f_small.getParentFile().exists()){
              f_small.getParentFile().mkdirs();
          }else if(!f_middle.getParentFile().exists()){
              f_middle.getParentFile().mkdirs();
          }
          ImageUtil.resizeImage(file, 56, 56, f_small);
          ImageUtil.resizeImage(file, 217, 190, f_middle);
      }
        return bean;
    }

    @DeleteMapping("productImages/{id}")
    public String delete(@PathVariable("id")int id){
        productImageService.delete(id);
        return null;
    }
}
