package com.thu.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
public class AuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationApplication.class, args);
    }

}
