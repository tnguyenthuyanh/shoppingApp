package com.thu.authorization.dao;

import com.thu.authorization.domain.entity.*;
//import com.thu.authorization.domain.wrapper.ProductResultWrapper;
import com.thu.authorization.domain.request.ProductRequest;
import com.thu.authorization.domain.wrapper.*;
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

    public void updateProduct(ProductRequest request, int product_id) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            transaction = session.beginTransaction();


            Product product = session.get(Product.class, product_id);

            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setStock_quantity(request.getStock_quantity());
            product.setWholesale_price(request.getWholesale_price());
            product.setRetail_price(request.getRetail_price());

            session.save(product);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
//            if (transaction != null) transaction.rollback();
        }

    }

    public boolean isProductInWatchlist(int product_id, int user_id) {
        List<Object[]> result = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            transaction = session.beginTransaction();

            Query query = session.createQuery(
                    "select pw from ProductWatchlist pw "
                            + "where pw.user.user_id = :user_id "
                            + "and pw.product.product_id = :product_id ");
            query.setParameter("product_id", product_id);
            query.setParameter("user_id", user_id);

            result = query.getResultList();

            transaction.commit();
        } catch (
                Exception e) {
            e.printStackTrace();
//            if (transaction != null) transaction.rollback();
        }
        if (result.size() == 0) {
            return false;
        } else {
            return true;
        }

    }

    public boolean addToWatchlist(int product_id, int user_id) {

        boolean success = false;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            transaction = session.beginTransaction();

            if (!isProductInWatchlist(product_id, user_id)) {
                ProductWatchlist p = ProductWatchlist.builder()
                        .product(Product.builder().product_id(product_id).build())
                        .user(User.builder().user_id(user_id).build())
                        .build();
                session.save(p);
                success = true;
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
//            if (transaction != null) transaction.rollback();
        }
        return success;
    }

    public boolean removeFromWatchlist(int product_id, int user_id) {

        boolean success = false;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            transaction = session.beginTransaction();

            if (isProductInWatchlist(product_id, user_id)) {
                Query query = session.createQuery(
                        "delete from ProductWatchlist pw "
                                + "where pw.user.user_id = :user_id "
                                + "and pw.product.product_id = :product_id ");
                query.setParameter("product_id", product_id);
                query.setParameter("user_id", user_id);

                query.executeUpdate();
                success = true;
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
//            if (transaction != null) transaction.rollback();
        }
        return success;
    }

    public List<ProductResultWrapper> viewWatchlist(int user_id) {

        List<ProductResultWrapper> list = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            transaction = session.beginTransaction();

            Query query = session.createQuery(
                    "select pw.product.product_id, pw.product.name, pw.product.description, pw.product.retail_price " +
                            "from ProductWatchlist pw "
                            + "where pw.user.user_id = :user_id "
                            + "and pw.product.stock_quantity != 0");
            query.setParameter("user_id", user_id);

            List<Object[]> result = query.getResultList();
            for (Object[] o : result) {
                ProductResultWrapper p = ProductResultWrapper.builder()
                        .product_id((int) o[0])
                        .name((String) o[1])
                        .description((String) o[2])
                        .retail_price((double) o[3])
                        .build();
                list.add(p);
            }

            transaction.commit();
        } catch (
                Exception e) {
            e.printStackTrace();
//            if (transaction != null) transaction.rollback();
        }
        return list;

    }

    public List<MostFrequentlyPurchasedWrapper> getMostFrequentlyPurchased(String username, int limit) {
        Session session = getCurrentSession();
        Query query = session.createQuery(
                "select sum(oi.purchased_quantity) as quantity, oi.product.product_id, oi.product.name, oi.product.description "
                        + "from OrderItem oi "
                        + "where oi.order.order_status != 'Canceled' "
                        + "and oi.order.user.email = :username "
                        + "group by oi.product.product_id "
                        + "order by quantity desc ");
        query.setParameter("username", username);
        query.setMaxResults(limit);

        List<Object[]> resultList = query.getResultList();
        List<MostFrequentlyPurchasedWrapper> list = new ArrayList<>();

        for (Object[] aRow : resultList) {
            MostFrequentlyPurchasedWrapper item = MostFrequentlyPurchasedWrapper.builder()
                    .purchased_quantity(((Long) aRow[0]).intValue())
                    .product_id((int) aRow[1])
                    .name((String) aRow[2])
                    .description((String) aRow[3])
                    .build();
            list.add(item);
        }

        return list;
    }

    public List<MostRecentlyPurchasedWrapper> getMostRecentlyPurchased(String username, int limit) {
        Session session = getCurrentSession();
        Query query = session.createQuery(
                "select oi.order.date_placed, oi.product.product_id, oi.product.name, oi.product.description, "
                        + "oi.purchased_quantity, oi.purchased_price "
                        + "from OrderItem oi "
                        + "where oi.order.order_status != 'Canceled' "
                        + "and oi.order.user.email = :username "
                        + "order by oi.order.date_placed desc ");
        query.setParameter("username", username);
        query.setMaxResults(limit);

        List<Object[]> resultList = query.getResultList();
        List<MostRecentlyPurchasedWrapper> list = new ArrayList<>();

        for (Object[] aRow : resultList) {
            MostRecentlyPurchasedWrapper item = MostRecentlyPurchasedWrapper.builder()
                    .date_placed((String) aRow[0])
                    .product_id((int) aRow[1])
                    .name((String) aRow[2])
                    .description((String) aRow[3])
                    .purchased_quantity((int) aRow[4])
                    .purchased_price((double) aRow[5])
                    .build();
            list.add(item);
        }

        return list;
    }

    public List<MostFrequentlyPurchasedWrapper> getMostPopularProducts(int limit) {
        Session session = getCurrentSession();
        Query query = session.createQuery(
                "select sum(oi.purchased_quantity) as quantity, oi.product.product_id, oi.product.name, oi.product.description "
                        + "from OrderItem oi "
                        + "where oi.order.order_status = 'Completed' "
                        + "group by oi.product.product_id "
                        + "order by quantity desc ");
        query.setMaxResults(limit);

        List<Object[]> resultList = query.getResultList();
        List<MostFrequentlyPurchasedWrapper> list = new ArrayList<>();

        for (Object[] aRow : resultList) {
            MostFrequentlyPurchasedWrapper item = MostFrequentlyPurchasedWrapper.builder()
                    .purchased_quantity(((Long) aRow[0]).intValue())
                    .product_id((int) aRow[1])
                    .name((String) aRow[2])
                    .description((String) aRow[3])
                    .build();
            list.add(item);
        }

        return list;
    }

    public List<MostProfitProductWrapper> getMostProfitProducts(int limit) {
        Session session = getCurrentSession();
        Query query = session.createQuery(
                "select Sum((oi.purchased_quantity*(oi.purchased_price-oi.wholesale_price))) as profit, "
                        + "oi.product.product_id, oi.product.name, oi.product.description "
                        + "from OrderItem oi "
                        + "where oi.order.order_status = 'Completed' "
                        + "group by oi.product.product_id "
                        + "order by profit desc ");
        query.setMaxResults(limit);

        List<Object[]> resultList = query.getResultList();
        List<MostProfitProductWrapper> list = new ArrayList<>();

        for (Object[] aRow : resultList) {
            double profit = (double) aRow[0];
            MostProfitProductWrapper item = MostProfitProductWrapper.builder()
                    .profit(Math.round(profit*100)/100.0)
                    .product_id((int) aRow[1])
                    .name((String) aRow[2])
                    .description((String) aRow[3])
                    .build();
            list.add(item);
        }

        return list;
    }

}
