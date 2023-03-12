package com.thu.authorization.dao;

import com.thu.authorization.domain.entity.Product;
//import com.thu.authorization.domain.wrapper.ProductResultWrapper;
import com.thu.authorization.domain.wrapper.ProductResultWrapper;
import org.hibernate.Session;
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

    //    public Optional<User> loadUserByUsername(String email) {
//        return this.findByEmail(email);
//    }
    public List<ProductResultWrapper> getAllProductsForUser() {
        Session session = getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProductResultWrapper> criteria = builder.createQuery(ProductResultWrapper.class);
        Root<Product> root = criteria.from(Product.class);
        criteria.multiselect(root.get("product_id"), root.get("name"), root.get("description"), root.get("retail_price"));
//        criteria.select(builder.construct(ProductResultWrapper.class, root.get("product_id"), root.get("name"), root.get("description"), root.get("retail_price")));

        criteria.where(builder.notEqual(root.get("stock_quantity"), 0));
        Query query = session.createQuery(criteria);
//        List<Product> productResultWrapperList =
        List<ProductResultWrapper> products = query.getResultList();
//                new ArrayList();
//        for (ProductResultWrapper pd : productResultWrapperList) {
//            Product product = Product.builder()
////                    .product_id(pd.getProduct_id())
//                    .name(pd.getName())
//                    .description(pd.getDescription())
//                    .retail_price(pd.getRetail_price())
//                    .build();
//            products.add(product);
//        }


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
}
