package com.thu.authorization.controller;

import com.thu.authorization.domain.ServiceStatus;

import com.thu.authorization.domain.response.AllProductResponse;
import com.thu.authorization.domain.entity.Product;
import com.thu.authorization.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public Object getAuthUserDetail(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("all")
    @PreAuthorize("hasAuthority('read')")
    public AllProductResponse getAllProducts(){
        List<Product> products;
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains("write")) {
            products = productService.getAllProductsForAdmin();
        } else { // has read only
            products = productService.getAllProductsForUser();
        }

        return AllProductResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .products(products)
                .build();
    }
//
//    @GetMapping("get/{id}")
//    @PreAuthorize("hasAuthority('read')")
//    public ProductResponse getContentById(@PathVariable Integer id){
//        Content content = contentService.getContentById(id);
//
//        return ProductResponse.builder()
//                .serviceStatus(
//                        ServiceStatus.builder()
//                                .success(true)
//                                .build()
//                )
//                .content(content)
//                .build();
//    }
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
