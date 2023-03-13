package com.thu.authorization.dao;

import com.thu.authorization.domain.entity.Order;
import com.thu.authorization.domain.entity.OrderItem;
import com.thu.authorization.domain.entity.Product;
//import com.thu.authorization.domain.wrapper.ProductResultWrapper;
import com.thu.authorization.domain.entity.User;
import com.thu.authorization.domain.request.OrderRequest;
import com.thu.authorization.domain.request.ProductRequest;
import com.thu.authorization.domain.wrapper.ProductResultWrapper;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

@Repository
public class ProductDao extends AbstractHibernateDao<Product> {

    public ProductDao() {
        setClazz(Product.class);
    }

    public List<ProductResultWrapper> getAllProductsForUser() {
        Session session = getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProductResultWrapper> criteria = builder.createQuery(ProductResultWrapper.class);
        Root<Product> root = criteria.from(Product.class);
        criteria.multiselect(root.get("product_id"), root.get("name"), root.get("description"), root.get("retail_price"));
        criteria.where(builder.notEqual(root.get("stock_quantity"), 0));
        Query query = session.createQuery(criteria);
        List<ProductResultWrapper> products = query.getResultList();

        return products;
    }

    public List<Product> getAllProductsForAdmin() {
        Session session = getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);
        criteria.select(root);
        Query query = session.createQuery(criteria);
        List<Product> products = query.getResultList();
        return products;
    }

    public Product getProductByIdForAdmin(int id) {
        return this.findById(id);
    }

    public ProductResultWrapper getProductByIdForUser(int id) {
        Session session = getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProductResultWrapper> criteria = builder.createQuery(ProductResultWrapper.class);
        Root<Product> root = criteria.from(Product.class);
        criteria.multiselect(root.get("product_id"), root.get("name"), root.get("description"), root.get("retail_price"));
        criteria.where(builder.and
                (builder.notEqual(root.get("stock_quantity"), 0), builder.equal(root.get("product_id"), id)));
        Query query = session.createQuery(criteria);
        List<ProductResultWrapper> products = query.getResultList();

        return products.size() == 0 ? null : products.get(0);
    }

    public void addProduct(ProductRequest request) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            transaction = session.beginTransaction();

            Product product = Product.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .wholesale_price(request.getWholesale_price())
                    .retail_price(request.getRetail_price())
                    .stock_quantity(request.getStock_quantity())
                    .build();

            session.save(product);

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
//            if (transaction != null) transaction.rollback();
        }

    }

}
