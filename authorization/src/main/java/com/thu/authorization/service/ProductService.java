package com.thu.authorization.service;

import com.thu.authorization.dao.ProductDao;
import com.thu.authorization.domain.entity.Product;
import com.thu.authorization.domain.request.ProductRequest;
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

    public Product getProductByIdForAdmin(int id) {
        return productDao.getProductByIdForAdmin(id);
    }

    public ProductResultWrapper getProductByIdForUser(int id) {
        return productDao.getProductByIdForUser(id);
    }

    public void addProduct(ProductRequest request) {
        productDao.addProduct(request);
    }

    public void updateProduct(ProductRequest request, int product_id) {
        productDao.updateProduct(request, product_id);
    }

    public boolean addToWatchlist(int product_id, int user_id) {
        return productDao.addToWatchlist(product_id, user_id);
    }

    public List<ProductResultWrapper> viewWatchlist(int user_id) {
        return productDao.viewWatchlist(user_id);
    }

    public boolean removeFromWatchlist(int product_id, int user_id) {
        return productDao.removeFromWatchlist(product_id, user_id);
    }
}
