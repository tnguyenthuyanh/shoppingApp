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
                .antMatchers("/product/all").hasAuthority("read")
                .antMatchers("/product/add").hasAuthority("write")
                .antMatchers("/order/{id}/canceled").hasAuthority("read")
                .antMatchers("/order/{id}/completed").hasAuthority("write")
                .antMatchers("/order/user/**").hasAuthority("write")

//                .antMatchers("/content/getAll", "/content/get/*").hasAuthority("read")
//                .antMatchers("/content/create").hasAuthority("write")
//                .antMatchers("/content/update").hasAuthority("update")
//                .antMatchers("/content/delete/*").hasAuthority("delete")
                .anyRequest()
                .authenticated();
        return http.build();
    }



}
