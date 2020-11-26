package com.rainypeople.tmall.service;

import com.rainypeople.tmall.dao.PropertyValueDao;
import com.rainypeople.tmall.pojo.Product;
import com.rainypeople.tmall.pojo.Property;
import com.rainypeople.tmall.pojo.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueService {
    @Autowired
    PropertyValueDao propertyValueDao;
    @Autowired
    PropertyService propertyService;


    public void init(Product product) {
        List<Property> properties=propertyService.listByCategory(product.getCategory());
        for (Property property:properties){
            PropertyValue propertyValue = getByPropertyAndProduct(product, property);
            if (null==propertyValue){
                propertyValue=new PropertyValue();
                propertyValue.setProperty(property);
                propertyValue.setProduct(product);
                propertyValueDao.save(propertyValue);
            }
        }
    }

    public PropertyValue getByPropertyAndProduct(Product product, Property property) {
        PropertyValue propertyValue = propertyValueDao.getByProductAndProperty(product, property);
        return propertyValue;
    }

    public List<PropertyValue> list(Product product) {
        List<PropertyValue> propertyValues = propertyValueDao.findByProductOrderByIdDesc(product);
        return propertyValues;
    }

    public void updata(PropertyValue bean) {
        propertyValueDao.save(bean);
    }
}
