package com.thu.authorization.controller;

import com.thu.authorization.domain.ServiceStatus;
import com.thu.authorization.domain.entity.Order;
import com.thu.authorization.domain.entity.Product;
import com.thu.authorization.domain.entity.User;
import com.thu.authorization.domain.request.OrderRequest;
import com.thu.authorization.domain.response.AllProductResponse;
import com.thu.authorization.domain.response.OrderResponse;
import com.thu.authorization.domain.response.OrderUpdateResponse;
import com.thu.authorization.domain.response.ProductResponse;
import com.thu.authorization.domain.wrapper.*;
import com.thu.authorization.service.OrderService;
import com.thu.authorization.service.ProductService;
import com.thu.authorization.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("order")
public class OrderController {

    private OrderService orderService;
    private UserService userService;

    @Autowired
    public void setOrderService(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('read')")
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = userService.getUserIdByUsername(username);

        int order_id = orderService.placeNewOrder(request, user_id);

        if (order_id == -1) { //some item(s) do not exist or out of stock
            return OrderResponse.builder()
                    .serviceStatus(
                            ServiceStatus.builder()
                                    .success(false)
                                    .build()
                    )
                    .message("Cannot place order! Some item(s) do not exist or out of stock")
                    .build();
        } else if (order_id == -2) { //not enough inventory
            return OrderResponse.builder()
                    .serviceStatus(
                            ServiceStatus.builder()
                                    .success(false)
                                    .build()
                    )
                    .message("Cannot place order due to not enough inventory!")
                    .build();
        } else {
            return OrderResponse.builder()
                    .serviceStatus(
                            ServiceStatus.builder()
                                    .success(true)
                                    .build()
                    )
                    .message("New order placed!")
                    .build();
        }

    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('read')")
    public OrderResponse getAllOrders() {
        Object object;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("write"))) {
            AllOrderResultAdminWrapper orders = orderService.getAllOrdersForAdmin();
            object = orders;
        } else {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            int user_id = userService.getUserIdByUsername(username);
            AllOrderResultWrapper orders = orderService.getAllOrdersForUser(user_id);
            object = orders;
        }
        return OrderResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .order(object)
                .build();

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read')")
    public OrderResponse getOrderDetailById(@PathVariable Integer id) {

        boolean check = true;
        Object object;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("write"))) {
            OrderResultAdminWrapper order = orderService.getOrderDetailForAdmin(id);
            if (order == null) check = false;
            object = order;
        } else {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            int user_id = userService.getUserIdByUsername(username);
            OrderResultWrapper order = orderService.getOrderDetailForUser(id, user_id);
            if (order == null) check = false;
            object = order;
        }

        if (check == false) {
            return OrderResponse.builder()
                    .serviceStatus(
                            ServiceStatus.builder()
                                    .success(false)
                                    .build()
                    )
                    .message("order not found")
                    .build();
        }


        return OrderResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .message("Order found!")
                .order(object)
                .build();
    }

    @GetMapping("/{id}/canceled")
    @PreAuthorize("hasAuthority('read')")
    public OrderUpdateResponse cancelOrderById(@PathVariable Integer id) {
        String message = null;
        boolean status = true;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("write"))) {
            Order order = orderService.getOrderById(id);
            if (order == null) {
                message = "No order found!";
                status = false;
            } else {
                boolean canCancelOrder = orderService.cancelOrder(id);
                if (canCancelOrder) {
                    message = "Order is canceled";
                } else {
                    message = "Cannot process! Order is either canceled or completed";
                    status = false;
                }
            }

        } else {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            int user_id = userService.getUserIdByUsername(username);
            boolean order_exist = orderService.getOrderForUser(id, user_id);
            if (order_exist == false) {
                message = "No order found!";
                status = false;
            } else {
                boolean canCancelOrder = orderService.cancelOrder(id);
                if (canCancelOrder) {
                    message = "Order is canceled";
                } else {
                    message = "Cannot process! Order is either canceled or completed";
                    status = false;
                }
            }
        }

        return OrderUpdateResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(status)
                                .build()
                )
                .message(message)
                .build();

    }

    @GetMapping("/{id}/completed")
    @PreAuthorize("hasAuthority('write')")
    public OrderUpdateResponse completeOrderById(@PathVariable Integer id) {
        boolean canCompleteOrder = orderService.completeOrder(id);

        if (!canCompleteOrder) {
            return OrderUpdateResponse.builder()
                    .serviceStatus(
                            ServiceStatus.builder()
                                    .success(false)
                                    .build()
                    )
                    .message("Cannot process! Order is either canceled or completed")
                    .build();
        }

        return OrderUpdateResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .message("Order is completed")
                .build();

    }

    @GetMapping("/user/{user_id}")
    @PreAuthorize("hasAuthority('write')")
    public OrderResponse getAllOrdersByUserIdForAdmin(@PathVariable Integer user_id) {
        AllOrderResultWrapper orders = orderService.getAllOrdersForUser(user_id);

        return OrderResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .order(orders)
                .build();

    }

//
//    @PutMapping("update")
//    @PreAuthorize("hasAuthority('update')")
//    public MessageResponse updateContent(@RequestBody ProductRequest request){
//        contentService.updateContent(request);
//
//        return MessageResponse.builder()
//                .serviceStatus(
//                        ServiceStatus.builder()
//                                .success(true)
//                                .build()
//                )
//                .message("Content updated")
//                .build();
//    }
//
//    @DeleteMapping("delete/{id}")
//    @PreAuthorize("hasAuthority('delete')")
//    public MessageResponse deleteContent(@PathVariable Integer id){
//        contentService.deleteContent(id);
//
//        return MessageResponse.builder()
//                .serviceStatus(
//                        ServiceStatus.builder()
//                                .success(true)
//                                .build()
//                )
//                .message("Content deleted")
//                .build();
//    }


}
