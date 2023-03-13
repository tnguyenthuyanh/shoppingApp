package com.thu.authorization.controller;

import com.thu.authorization.domain.ServiceStatus;
import com.thu.authorization.domain.entity.Product;
import com.thu.authorization.domain.request.ProductRequest;
import com.thu.authorization.domain.response.AllProductResponse;
import com.thu.authorization.domain.response.ProductResponse;
import com.thu.authorization.domain.wrapper.ProductResultWrapper;
import com.thu.authorization.service.ProductService;
import com.thu.authorization.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("watchlist")
public class WatchListController {

    private ProductService productService;
    private UserService userService;

    @Autowired
    public void setProductService(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

//
//    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('read')")
//    public AllProductResponse getAllProductsForAdmin() {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("write"))) {
//            List<Product> products = productService.getAllProductsForAdmin();
//            List<Object> objects = (List) products;
//
//            return AllProductResponse.builder()
//                    .serviceStatus(
//                            ServiceStatus.builder()
//                                    .success(true)
//                                    .build()
//                    )
//                    .products(objects)
//                    .build();
//        } else {
//            List<ProductResultWrapper> products = productService.getAllProductsForUser();
//            List<Object> objects = (List) products;
//
//            return AllProductResponse.builder()
//                    .serviceStatus(
//                            ServiceStatus.builder()
//                                    .success(true)
//                                    .build()
//                    )
//                    .products(objects)
//                    .build();
//        }
//
//
//    }

    @PostMapping("/product/{product_id}")
    @PreAuthorize("hasAuthority('read')")
    public ProductResponse updateProduct(@PathVariable Integer product_id) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = userService.getUserIdByUsername(username);

        List<ProductResultWrapper> list = productService.getAllProductsForUser();
        if (list.stream().filter(e -> e.getProduct_id() == product_id).findAny().isPresent() == false) { //stock quantity = 0
            return ProductResponse.builder()
                    .serviceStatus(
                            ServiceStatus.builder()
                                    .success(true)
                                    .build()
                    )
                    .message("Cannot found product")
                    .build();
        } else {
            boolean success = productService.addToWatchlist(product_id, user_id);

            if (success) {
                return ProductResponse.builder()
                        .serviceStatus(
                                ServiceStatus.builder()
                                        .success(true)
                                        .build()
                        )
                        .message("Successfully added to watchlist")
                        .build();
            }

            return ProductResponse.builder()
                    .serviceStatus(
                            ServiceStatus.builder()
                                    .success(false)
                                    .build()
                    )
                    .message("Product was already added to watchlist")
                    .build();
        }
    }

    @DeleteMapping("/product/{product_id}")
    @PreAuthorize("hasAuthority('read')")
    public ProductResponse removeFromWatchList(@PathVariable Integer product_id) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = userService.getUserIdByUsername(username);

        boolean success = productService.removeFromWatchlist(product_id, user_id);

        if (success) {
            return ProductResponse.builder()
                    .serviceStatus(
                            ServiceStatus.builder()
                                    .success(true)
                                    .build()
                    )
                    .message("Successfully removed from watchlist")
                    .build();
        }

        return ProductResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(false)
                                .build()
                )
                .message("Cannot found product in Watchlist")
                .build();

    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('read')")
    public AllProductResponse viewWatchlist() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = userService.getUserIdByUsername(username);

        List<ProductResultWrapper> list = productService.viewWatchlist(user_id);
        List<Object> objectList = new ArrayList<Object>(list);

        return AllProductResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .products(objectList)
                .build();
    }

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
