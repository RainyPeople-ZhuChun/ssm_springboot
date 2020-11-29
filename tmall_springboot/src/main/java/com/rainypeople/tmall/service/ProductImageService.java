package com.rainypeople.tmall.service;

import com.rainypeople.tmall.dao.ProductImageDao;
import com.rainypeople.tmall.pojo.OrderItem;
import com.rainypeople.tmall.pojo.Product;
import com.rainypeople.tmall.pojo.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.List;

@Service
@CacheConfig(cacheNames = "productImages")
public class ProductImageService {

    @Autowired
    ProductImageDao productImageDao;
    @Autowired
    ProductService productService;

    @CacheEvict(allEntries = true)
    public void add(ProductImage bean) {
        productImageDao.save(bean);

    }

    @CacheEvict(allEntries = true)
    public void delete(int id) {
        productImageDao.delete(id);
    }

    @Cacheable(key = "'productImages-one-'+#p0")
    public ProductImage get(int id) {
        return productImageDao.findOne(id);
    }


    @Cacheable(key="'productImages-single-pid-'+ #p0.id")
    public List<ProductImage> listSingleProductImages(Product product, String type) {
        List<ProductImage> singles = productImageDao.findByProductAndTypeOrderByIdDesc(product, type);
        return singles;
    }

    @Cacheable(key="'productImages-detail-pid-'+ #p0.id")
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

    public void setFirstProdutImagesOnOrderItems(List<OrderItem> ois) {
        for (OrderItem orderItem : ois) {
            setFirstProductImage(orderItem.getProduct());
        }
    }


}
