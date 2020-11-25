package com.rainypeople.tmall.service;

import com.rainypeople.tmall.dao.ProductImageDao;
import com.rainypeople.tmall.pojo.Product;
import com.rainypeople.tmall.pojo.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {

    @Autowired
    ProductImageDao productImageDao;
    @Autowired
    ProductService productService;

    public void add(ProductImage bean) {
        productImageDao.save(bean);

    }
    public void delete(int id) {
        productImageDao.delete(id);
    }

    public ProductImage get(int id) {
        return productImageDao.findOne(id);
    }


    public List<ProductImage> listSingleProductImages(Product product, String type) {
        List<ProductImage> singles = productImageDao.findByProductAndTypeOrderByIdDesc(product, type);
        return singles;
    }

    public List<ProductImage> listDetailProductImages(Product product, String type) {
        return productImageDao.findByProductAndTypeOrderByIdDesc(product,type);
    }

    public void setFirstProductImage(List<Product> content) {
        for (Product product:content){
            setFirstProductImage(product);
        }
    }

    public void setFirstProductImage(Product product) {
        List<ProductImage> singles = listSingleProductImages(product, "single");
        if (!singles.isEmpty()){
            product.setFirstProductImage(singles.get(0));
        }else {
            product.setFirstProductImage(new ProductImage());
        }
    }
}
