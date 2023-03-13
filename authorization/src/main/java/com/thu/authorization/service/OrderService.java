package com.thu.authorization.service;

import com.thu.authorization.dao.OrderDao;
import com.thu.authorization.dao.ProductDao;
import com.thu.authorization.domain.entity.Order;
import com.thu.authorization.domain.entity.Product;
import com.thu.authorization.domain.request.OrderRequest;
import com.thu.authorization.domain.wrapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderDao orderDao;

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public int placeNewOrder(OrderRequest request, int user_id) {
        return orderDao.placeNewOrder(request, user_id);
    }

    public OrderResultWrapper getOrderDetailForUser(int order_id, int user_id) {
        return orderDao.getOrderDetailForUser(order_id, user_id);
    }

    public OrderResultAdminWrapper getOrderDetailForAdmin(int order_id) {
        return orderDao.getOrderDetailForAdmin(order_id);
    }

    public Order getOrderById(int order_id) {
        return orderDao.getOrderById(order_id);
    }

    public boolean getOrderForUser(int order_id, int user_id) {
        return orderDao.getOrderForUser(order_id, user_id);
    }

    public boolean cancelOrder(int order_id) {
        return orderDao.cancelOrder(order_id);
    }

    public boolean completeOrder(int order_id) {
        return orderDao.completeOrder(order_id);
    }
    public AllOrderResultWrapper getAllOrdersForUser(int user_id) {
        return orderDao.getAllOrdersForUser(user_id);
    }

    public AllOrderResultAdminWrapper getAllOrdersForAdmin() {
        return orderDao.getAllOrdersForAdmin();
    }
}
