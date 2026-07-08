package com.ecommerce;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        System.out.println("JdbcTemplate " + jdbcTemplate);
        context.close();
    }
}
