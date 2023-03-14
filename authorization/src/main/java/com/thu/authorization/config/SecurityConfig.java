package com.thu.authorization.config;

import com.thu.authorization.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private JwtFilter jwtFilter;

    @Autowired
    public void setJwtFilter(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/product/test").permitAll()
                .antMatchers("/product/add").hasAuthority("write")
                .antMatchers("/product/all", "/product/{id}").hasAuthority("read")
                .antMatchers( "/product/update/**", "/product/popular/**", "/product/profit/**").hasAuthority("write")
                .antMatchers("/order","/order/all", "/order/{id}", "/order/{id}/cancel").hasAuthority("read")
                .antMatchers("/order/{id}/complete", "/order/spend/**", "/order/user/**").hasAuthority("write")
                .antMatchers("/watchlist/**", "/product/frequent/*", "/product/recent/*").hasAuthority("read")
                .anyRequest()
                .authenticated();
        return http.build();
    }


}
