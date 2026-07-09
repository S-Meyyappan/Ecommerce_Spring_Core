package com.ecommerce.repository;

import com.ecommerce.dto.VendorProductCountDto;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.utility.CategoryUtility;
import com.ecommerce.model.Vendor;
import com.ecommerce.utility.ProductUtility;
import com.ecommerce.utility.VendorProductCountUtility;
import com.ecommerce.utility.VendorUtility;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CategoryUtility categoryUtility;
    private final VendorUtility vendorUtility;
    private final ProductUtility productUtility;
    private final VendorProductCountUtility vendorProductCountUtility;

    public ProductRepository(JdbcTemplate jdbcTemplate, CategoryUtility productUtility, VendorUtility vendorUtility, ProductUtility productUtility1, VendorProductCountUtility vendorProductCountUtility) {
        this.jdbcTemplate = jdbcTemplate;
        this.categoryUtility = productUtility;
        this.vendorUtility = vendorUtility;
        this.productUtility = productUtility1;
        this.vendorProductCountUtility = vendorProductCountUtility;
    }

    public Optional<Category> getCategoryByName(String categoryName) {
        String sql = "SELECT * FROM category WHERE name = ?";
        return jdbcTemplate.query(sql, categoryUtility, categoryName)
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
        String sql = "INSERT INTO product (name, price, stockQuantity, category_id, vendor_id)"
                        + "VALUES (?, ?, ?, ?, ?)";
        Object[] values = new Object[]{product.getName(), product.getPrice(), product.getStockQuantity(), product.getCategory().getId(), product.getVendor().getId()};
        jdbcTemplate.update(sql,values);
    }

    public Optional<Product> getProductById(int productId) {
        String sql = """
                SELECT
                c.id AS category_id, c.name AS category_name,
                v.id AS vendor_id, v.name AS vendor_name,
                p.id AS product_id, p.name AS product_name, p.price, p.stockQuantity
                FROM product p
                JOIN category c ON p.category_id = c.id
                JOIN vendor v ON p.vendor_id = v.id
                WHERE p.id = ?
                """;
        return jdbcTemplate.query(sql, productUtility, productId)
                .stream().findFirst();
    }

    public void updateStockQuantity(Product product) {
        String sql = "UPDATE product SET stockQuantity = ? WHERE id = ?";
        Object[] values = new Object[]{product.getStockQuantity(), product.getId()};
        jdbcTemplate.update(sql, values);
    }

    public List<VendorProductCountDto> getProductCountByVendors() {
        String sql = """
                SELECT
                    v.name AS vendor_name, count(v.name) AS product_count
                    FROM product p
                    JOIN vendor v
                    ON v.id = p.vendor_id
                    GROUP BY(v.name)
                """;
        return jdbcTemplate.query(sql, vendorProductCountUtility );
    }
}
