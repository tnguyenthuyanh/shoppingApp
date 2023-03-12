package com.thu.authorization.controller;

import com.thu.authorization.domain.ServiceStatus;

import com.thu.authorization.domain.response.AllProductResponse;
import com.thu.authorization.domain.entity.Product;
import com.thu.authorization.domain.response.ProductResponse;
import com.thu.authorization.domain.wrapper.ProductResultWrapper;
import com.thu.authorization.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/test")
    public Object getAuthUserDetail() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('read')")
    public AllProductResponse getAllProductsForAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("write"))) {
            List<Product> products = productService.getAllProductsForAdmin();
            List<Object> objects = (List) products;

            return AllProductResponse.builder()
                    .serviceStatus(
                            ServiceStatus.builder()
                                    .success(true)
                                    .build()
                    )
                    .products(objects)
                    .build();
        } else {
            List<ProductResultWrapper> products = productService.getAllProductsForUser();
            List<Object> objects = (List) products;

            return AllProductResponse.builder()
                    .serviceStatus(
                            ServiceStatus.builder()
                                    .success(true)
                                    .build()
                    )
                    .products(objects)
                    .build();
        }


    }

    //    @GetMapping("all")
//    @PreAuthorize("hasAuthority('read')")
//    public AllProductResponse getAllProductsForUser(){
//
//            List<Product> products = productService.getAllProductsForUser();
//
//
//        return AllProductResponse.builder()
//                .serviceStatus(
//                        ServiceStatus.builder()
//                                .success(true)
//                                .build()
//                )
//                .products(products)
//                .build();
//    }
//
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read')")
    public ProductResponse getContentById(@PathVariable Integer id) {
        Object object;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("write"))) {
            Product product = productService.getProductByIdForAdmin(id);
            object = product;

        } else {
            ProductResultWrapper product = productService.getProductByIdForUser(id);
            object = product;
        }
        if (object == null) {
            return ProductResponse.builder()
                    .serviceStatus(
                            ServiceStatus.builder()
                                    .success(false)
                                    .build()
                    )
                    .build();

        }

        return ProductResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .product(object)
                .build();
    }
//
//    @PostMapping("create")
//    @PreAuthorize("hasAuthority('write')")
//    public MessageResponse createContent(@RequestBody ContentCreationRequest request){
//        contentService.createContent(request);
//
//        return MessageResponse.builder()
//                .serviceStatus(
//                        ServiceStatus.builder()
//                                .success(true)
//                                .build()
//                )
//                .message("New content created")
//                .build();
//    }
//
//    @PutMapping("update")
//    @PreAuthorize("hasAuthority('update')")
//    public MessageResponse updateContent(@RequestBody ContentUpdateRequest request){
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
