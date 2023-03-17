package com.thu.authorization.controller;


import com.google.gson.Gson;
import com.thu.authorization.domain.entity.Product;
import com.thu.authorization.domain.request.ProductRequest;
import com.thu.authorization.domain.response.AllProductResponse;
import com.thu.authorization.domain.response.ProductResponse;
import com.thu.authorization.domain.response.ProductUpdateResponse;
import com.thu.authorization.domain.wrapper.ProductResultWrapper;
import com.thu.authorization.security.JwtProvider;
import com.thu.authorization.service.ProductService;
import com.thu.authorization.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
//@Import(SecurityConfig.class)
public class ProductControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @MockBean
    UserService userService;

    @SpyBean
    JwtProvider jwtProvider;

    @Test
    @WithMockUser(authorities="read")
    public void getAllProductsForAdmin() throws Exception {
        List<Product> mockList = new ArrayList<>();

        when(productService.getAllProductsForAdmin()).thenReturn(mockList);
        System.out.println(mockList);
        List<Object> objectList = new ArrayList<Object>(mockList);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/product/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        AllProductResponse response = new Gson().fromJson(result.getResponse().getContentAsString(), AllProductResponse.class);
//        System.out.println(mockList);
        assertEquals(response.getProducts(), objectList);
    }

    @Test
    public void createProductTest() throws Exception {

        ProductRequest request = ProductRequest.builder().name("product").wholesale_price(12).retail_price(10).stock_quantity(5).description("new product").build();
        productService.addProduct(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/product/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        ProductResponse productResponse = new Gson().fromJson(result.getResponse().getContentAsString(), ProductResponse.class);
        assertTrue(productResponse.getServiceStatus().isSuccess());

    }

}
