package com.ecommerce.repository;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.utility.CategoryUtility;
import com.ecommerce.model.Vendor;
import com.ecommerce.utility.VendorUtility;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CategoryUtility productUtility;
    private final VendorUtility vendorUtility;

    public ProductRepository(JdbcTemplate jdbcTemplate, CategoryUtility productUtility, VendorUtility vendorUtility) {
        this.jdbcTemplate = jdbcTemplate;
        this.productUtility = productUtility;
        this.vendorUtility = vendorUtility;
    }

    public Optional<Category> getCategoryByName(String categoryName) {
        String sql = "SELECT * FROM category WHERE name = ?";
        return jdbcTemplate.query(sql, productUtility, categoryName)
                .stream().findFirst();
    }

    public Category createCategory(String categoryName) {
        String sql = "INSERT INTO category (name) VALUES (?)";
        jdbcTemplate.update(sql, categoryName);
        return getCategoryByName(categoryName).get();
    }

    public Optional<Vendor> getVendorByName(String vendorName) {
        String sql = "SELECT * FROM vendor WHERE name = ?";
        return jdbcTemplate.query(sql, vendorUtility, vendorName)
                .stream().findFirst();
    }

    public Vendor createVendor(String vendorName) {
        String sql = "INSERT INTO vendor (name) VALUES (?)";
        jdbcTemplate.update(sql, vendorName);
        return getVendorByName(vendorName).get();
    }

    public void addProduct(Product product) {
        String sql = "INSERT INTO product (name, price, stockQuantity, category_id, vendor_id) VALUES (?, ?, ?, ?, ?)";
        Object[] values = new Object[]{product.getName(), product.getPrice(), product.getStockQuantity(), product.getCategory().getId(), product.getVendor().getId()};
        jdbcTemplate.update(sql,values);
    }
}
