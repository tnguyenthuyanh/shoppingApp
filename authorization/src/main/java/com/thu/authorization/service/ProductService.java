package com.thu.authorization.service;

import com.thu.authorization.dao.ProductDao;
import com.thu.authorization.domain.entity.Product;
import com.thu.authorization.domain.wrapper.ProductResultWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductDao productDao;

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResultWrapper> getAllProductsForUser() {
        return productDao.getAllProductsForUser();
    }

    public List<Product> getAllProductsForAdmin() {
        return productDao.getAllProductsForAdmin();
    }
}
