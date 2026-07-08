package com.ecommerce;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    static {
        System.out.println("AppConfig loaded");
    }

}
