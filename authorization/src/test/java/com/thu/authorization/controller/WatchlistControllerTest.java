package com.thu.authorization.controller;


import com.thu.authorization.domain.entity.Product;
import com.thu.authorization.domain.response.AllProductResponse;
import com.thu.authorization.domain.response.ProductUpdateResponse;
import com.thu.authorization.domain.wrapper.ProductResultWrapper;
import com.thu.authorization.security.JwtProvider;
import com.google.gson.Gson;
import com.thu.authorization.service.ProductService;
import com.thu.authorization.service.UserService;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WatchListController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
//@Import(SecurityConfig.class)
public class WatchlistControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @MockBean
    UserService userService;

    @SpyBean
    JwtProvider jwtProvider;

    List<ProductResultWrapper> mockList;

    @BeforeEach
    public void setup() {
        mockList = new ArrayList<>();
        ProductResultWrapper p1 = ProductResultWrapper.builder().product_id(1).build();
        ProductResultWrapper p2 = ProductResultWrapper.builder().product_id(2).build();
        mockList.add(p1);
        mockList.add(p2);

    }
    @Test
    @WithMockUser(authorities="read")
    public void viewWatchlist() throws Exception {
//        productService.addToWatchlist(4,1);
        when(productService.viewWatchlist(1)).thenReturn(mockList);

        List<Object> objectList = new ArrayList<Object>(mockList);
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/watchlist/all")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();
        AllProductResponse response = new Gson().fromJson(result.getResponse().getContentAsString(), AllProductResponse.class);


        System.out.println(response.getProducts());
        assertEquals(response.getProducts(), objectList);
    }

    @Test
    @WithMockUser(authorities="read")
    public void addToWatchlist() throws Exception {
        boolean mockSucess = true;

        when(productService.addToWatchlist(4,1)).thenReturn(mockSucess);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/watchlist/product/{product_id}", "4")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        ProductUpdateResponse response = new Gson().fromJson(result.getResponse().getContentAsString(), ProductUpdateResponse.class);
        assertEquals(response.getServiceStatus().isSuccess(), mockSucess);
    }


//    @DeleteMapping("/product/{product_id}")
//    @PreAuthorize("hasAuthority('read')")
//    public ProductUpdateResponse removeFromWatchList(@PathVariable Integer product_id) {
//        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        int user_id = userService.getUserIdByUsername(username);
//
//        boolean success = productService.removeFromWatchlist(product_id, user_id);
//
//    }


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


}
