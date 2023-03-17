package com.thu.authorization.controller;

import com.thu.authorization.domain.ServiceStatus;

import com.thu.authorization.domain.request.OrderRequest;
import com.thu.authorization.domain.request.ProductRequest;
import com.thu.authorization.domain.response.AllProductResponse;
import com.thu.authorization.domain.entity.Product;
import com.thu.authorization.domain.response.MostSpentResponse;
import com.thu.authorization.domain.response.OrderResponse;
import com.thu.authorization.domain.response.ProductResponse;
import com.thu.authorization.domain.wrapper.*;
import com.thu.authorization.security.AuthUserDetail;
import com.thu.authorization.service.ProductService;
import com.thu.authorization.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("product")
public class ProductController {

    private ProductService productService;
    private UserService userService;

    @Autowired
    public void setProductService(ProductService productService,
                                  UserService userService) {
        this.productService = productService;
        this.userService = userService;
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

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read')")
    public ProductResponse getProductById(@PathVariable Integer id) {
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
                    .message("No product found!")
                    .build();

        }

        return ProductResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .product(object)
                .message("Product found!")
                .build();
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('write')")
    public ProductResponse addProduct(@Valid @RequestBody ProductRequest request,
                                      BindingResult bindingResult) {

            // perform validation check
            if (bindingResult.hasErrors()) {
                List<FieldError> errors = bindingResult.getFieldErrors();
                errors.forEach(error -> System.out.println(
                        "ValidationError in " + error.getObjectName() + ": " + error.getDefaultMessage()));
                return ProductResponse.builder()
                        .message("Invalid input")
                        .build();
            }

        productService.addProduct(request);

        return ProductResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .message("New product created!")
                .product(request)
                .build();


    }

    @PatchMapping("/update/{product_id}")
    @PreAuthorize("hasAuthority('write')")
    public ProductResponse updateProduct(@RequestBody ProductRequest request, @PathVariable Integer product_id) {

        productService.updateProduct(request, product_id);

        return ProductResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .message("Product updated!")
                .product(request)
                .build();


    }

    @GetMapping("/frequent/{limit}")
    @PreAuthorize("hasAuthority('read')")
    public ProductResponse getMostFrequentlyPurchased(@PathVariable Integer limit) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<MostFrequentlyPurchasedWrapper> response = productService.getMostFrequentlyPurchased(username, limit);

        return ProductResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .message("Top " + limit + " frequent")
                .product(response)
                .build();

    }

    @GetMapping("/recent/{limit}")
    @PreAuthorize("hasAuthority('read')")
    public ProductResponse getMostRecentlyPurchased(@PathVariable Integer limit) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<MostRecentlyPurchasedWrapper> list = productService.getMostRecentlyPurchased(username, limit);

        return ProductResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .message("Most recently purchased")
                .product(list)
                .build();
    }

    @GetMapping("/popular/{limit}")
    @PreAuthorize("hasAuthority('write')")
    public ProductResponse getMostPopularProduct(@PathVariable Integer limit) {

        List<MostFrequentlyPurchasedWrapper> list = productService.getMostPopularProducts(limit);

        return ProductResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .message("Most popular products")
                .product(list)
                .build();
    }

    @GetMapping("/profit/{limit}")
    @PreAuthorize("hasAuthority('write')")
    public ProductResponse getMostProfitProducts(@PathVariable Integer limit) {

        List<MostProfitProductWrapper> list = productService.getMostProfitProducts(limit);

        return ProductResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .message("Most profit products")
                .product(list)
                .build();
    }

}
