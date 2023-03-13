package com.thu.authorization.dao;

import com.thu.authorization.domain.entity.Order;
import com.thu.authorization.domain.entity.OrderItem;
import com.thu.authorization.domain.entity.Product;
import com.thu.authorization.domain.entity.User;
import com.thu.authorization.domain.request.OrderRequest;
import com.thu.authorization.domain.response.MostSpentResponse;
import com.thu.authorization.domain.wrapper.*;
import org.aspectj.weaver.ast.Or;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDao extends AbstractHibernateDao<Order> {

    public OrderDao() {
        setClazz(Order.class);
    }

    public int placeNewOrder(OrderRequest request, int user_id) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            transaction = session.beginTransaction();

            Order order = Order.builder().user(User.builder().user_id(user_id).build()).build();
            int order_id = (Integer) session.save(order); // get back user generated id
//            session.save(order);

            for (OrderRequest o : request.getOrder()) {
                Product product = session.get(Product.class, o.getProduct_id());
                if (product == null || product.getStock_quantity() == 0) {
                    if (transaction != null) {
//                        System.out.println("hehee");
                        transaction.rollback();
                    }
                    return -1;
                } else {
                    if (o.getQuantity() > product.getStock_quantity()) {
                        if (transaction != null) {
//                            System.out.println("hehee1111");
                            transaction.rollback();
                        }
                        return -2;
                    }
                    product.setStock_quantity(product.getStock_quantity() - o.getQuantity());
                    session.save(product);

                    OrderItem orderItem = OrderItem.builder()
                            .order(order)
                            .product(product)
                            .purchased_price(product.getRetail_price())
                            .purchased_quantity(o.getQuantity())
                            .wholesale_price(product.getWholesale_price())
                            .build();
                    session.save(orderItem);


                }
            }
            transaction.commit();
            return order_id;

        } catch (Exception e) {
            e.printStackTrace();
//            if (transaction != null) transaction.rollback();
        }

        return -1;
    }

    public OrderResultWrapper getOrderDetailForUser(int order_id, int user_id) {
        Session session = getCurrentSession();
        Query query = session.createQuery(
                "select p.product_id, p.name, p.description, oi.purchased_price, oi.purchased_quantity, o.order_status, o.date_placed "
                        + "from OrderItem oi "
                        + "JOIN oi.order o "
                        + "JOIN oi.product p "
                        + "JOIN o.user u "
                        + "where o.order_id = :order_id "
                        + "and u.user_id = :user_id ");
        query.setParameter("order_id", order_id);
        query.setParameter("user_id", user_id);

        List<Object[]> resultListlist = query.getResultList();
        List<OrderItemResultWrapper> list = new ArrayList<>();
        if (resultListlist.size() == 0) {
            return null;
        }

        OrderResultWrapper order = new OrderResultWrapper();
        order.setOrder_status((String) resultListlist.get(0)[5]);
        order.setDate_placed((String) resultListlist.get(0)[6]);
        order.setUser_id(user_id);
        order.setOrder_id(order_id);
        for (Object[] aRow : resultListlist) {
            OrderItemResultWrapper orderItem = OrderItemResultWrapper.builder()
                    .product_id((Integer) aRow[0]) //product_id
                    .name((String) aRow[1]) //name
                    .description((String) aRow[2]) // description
                    .purchased_price((double) aRow[3]) // purchased price
                    .purchased_quantity((int) aRow[4]) // purchased quantity
                    .build();
            list.add(orderItem);
        }

        order.setOrderItems(list);

        return order;

    }

    public AllOrderResultWrapper getAllOrdersForUser(int user_id) {
        Session session = getCurrentSession();
        Query query = session.createQuery(
                "select o.order_id "
                        + "from Order o "
                        + "JOIN o.user u "
                        + "where u.user_id = :user_id ");
        query.setParameter("user_id", user_id);

        List<Object> resultListlist = query.getResultList();
        if (resultListlist.size() == 0) {
            return null;
        }

        AllOrderResultWrapper orders = new AllOrderResultWrapper();
        orders.setUser_id(user_id);

        List<OrderResultWrapper> list = new ArrayList<>();

        for (Object aRow : resultListlist) {
            OrderResultWrapper order = getOrderDetailForUser((int) aRow, user_id);
            list.add(order);
        }

        orders.setOrders(list);

        return orders;
    }

    public AllOrderResultAdminWrapper getAllOrdersForAdmin() {
        Session session = getCurrentSession();
        Query query = session.createQuery(
                "select o.order_id, u.user_id "
                        + "from Order o "
                        + "JOIN o.user u ");

        List<Object[]> resultListlist = query.getResultList();
        if (resultListlist.size() == 0) {
            return null;
        }

        AllOrderResultAdminWrapper orders = new AllOrderResultAdminWrapper();

        List<OrderResultAdminWrapper> list = new ArrayList<>();
        for (Object[] aRow : resultListlist) {
            OrderResultAdminWrapper order = getOrderDetailForAdmin((int) aRow[0]);
            list.add(order);
        }

        orders.setOrders(list);

        return orders;
    }

    public OrderResultAdminWrapper getOrderDetailForAdmin(int order_id) {
        Session session = getCurrentSession();
        Query query = session.createQuery(
                "select p.product_id, p.name, p.description, oi.purchased_price, oi.purchased_quantity, p.wholesale_price, o.order_status, o.date_placed, u.user_id "
                        + "from OrderItem oi "
                        + "JOIN oi.order o "
                        + "JOIN oi.product p "
                        + "JOIN o.user u "
                        + "where o.order_id = :order_id ");
        query.setParameter("order_id", order_id);

        List<Object[]> resultListlist = query.getResultList();
        List<OrderItemAdminWrapper> list = new ArrayList<>();
        if (resultListlist.size() == 0) {
            return null;
        }

        OrderResultAdminWrapper order = new OrderResultAdminWrapper();
        order.setOrder_status((String) resultListlist.get(0)[6]);
        order.setDate_placed((String) resultListlist.get(0)[7]);
        order.setUser_id((int) resultListlist.get(0)[8]);
        order.setOrder_id(order_id);
        for (Object[] aRow : resultListlist) {
            OrderItemAdminWrapper orderItem = OrderItemAdminWrapper.builder()
                    .product_id((Integer) aRow[0]) //product_id
                    .name((String) aRow[1]) //name
                    .description((String) aRow[2]) // description
                    .purchased_price((double) aRow[3]) // purchased price
                    .purchased_quantity((int) aRow[4]) // purchased quantity
                    .wholesale_price((double) aRow[5]) //wholesale price
                    .build();
            list.add(orderItem);
        }

        order.setOrderItems(list);

        return order;

    }

    public boolean completeOrder(int order_id) {
        int row = 0;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            transaction = session.beginTransaction();
            Query query = session.createQuery(
                    "update Order o "
                            + "set o.order_status='Completed' "
                            + "where o.order_id = :order_id "
                            + "and o.order_status ='Processing' ");
            query.setParameter("order_id", order_id);

            row = query.executeUpdate();

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
//            if (transaction != null) transaction.rollback();
        }
        if (row == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean cancelOrder(int order_id) {
        int row = 0;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            transaction = session.beginTransaction();
            Query query = session.createQuery(
                    "update Order o "
                            + "set o.order_status='Canceled' "
                            + "where o.order_id = :order_id "
                            + "and o.order_status ='Processing' ");
            query.setParameter("order_id", order_id);

            row = query.executeUpdate();

            if (row != 0) {
                // status already updated in this session but not on database
                query = session.createQuery(
                        "select p.product_id, p.stock_quantity, oi.purchased_quantity "
                                + "from OrderItem oi "
                                + "JOIN oi.product p "
                                + "JOIN oi.order o "
                                + "where o.order_id = :order_id "
                                + "and o.order_status ='Canceled' ");
                query.setParameter("order_id", order_id);

                List<Object[]> resultListlist = query.getResultList();

                for (Object[] aRow : resultListlist) {
                    Product p = session.get(Product.class, (int) aRow[0]);
                    p.setStock_quantity((int) aRow[1] + (int) aRow[2]);
                    session.save(p);
                }

            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
//            if (transaction != null) transaction.rollback();
        }
        if (row == 0) {
            return false;
        } else {
            return true;
        }
    }

    public Order getOrderById(int order_id) {
        return this.findById(order_id);
    }

    public boolean getOrderForUser(int order_id, int user_id) {
        Session session = getCurrentSession();
        Query query = session.createQuery(
                "select o "
                        + "from Order o "
                        + "join o.user u "
                        + "where o.order_id = :order_id "
                        + "and u.user_id = :user_id ");
        query.setParameter("order_id", order_id);
        query.setParameter("user_id", user_id);

        List<Object[]> resultListlist = query.getResultList();

        return resultListlist.size() == 0 ? false : true;
    }


    public List<MostSpentWrapper> getMostSpent(int limit) {
        Session session = getCurrentSession();
        Query query = session.createQuery(
                "select sum(oi.purchased_price*oi.purchased_quantity) as spent, u.email "
                        + "from OrderItem oi "
                        + "JOIN oi.order o "
                        + "JOIN o.user u "
                        + "where o.order_status = 'Completed' "
        + "group by u.email "
                + "order by spent desc " );

        query.setMaxResults(limit);

        List<Object[]> resultListlist = query.getResultList();
        if (resultListlist.size() == 0) {
            return null;
        }

        List<MostSpentWrapper> list = new ArrayList<>();

        for (Object[] aRow : resultListlist) {
            MostSpentWrapper mostSpentWrapper = MostSpentWrapper.builder()
                    .moneySpent((double) aRow[0])
                    .email((String) aRow[1])
                    .build();
            list.add(mostSpentWrapper);
        }

        return list;
    }

}
