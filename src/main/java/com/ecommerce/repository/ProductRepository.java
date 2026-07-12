package com.ecommerce.repository;

import com.ecommerce.dto.VendorProductCountDto;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.utility.CategoryRowMapper;
import com.ecommerce.model.Vendor;
import com.ecommerce.utility.ProductRowMapper;
import com.ecommerce.utility.VendorProductCountRowMapper;
import com.ecommerce.utility.VendorRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CategoryRowMapper categoryRowMapper;
    private final VendorRowMapper vendorRowMapper;
    private final ProductRowMapper productRowMapper;
    private final VendorProductCountRowMapper vendorProductCountRowMapper;

    public ProductRepository(JdbcTemplate jdbcTemplate, CategoryRowMapper categoryRowMapper, VendorRowMapper vendorRowMapper, ProductRowMapper productRowMapper, VendorProductCountRowMapper vendorProductCountRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.categoryRowMapper = categoryRowMapper;
        this.vendorRowMapper = vendorRowMapper;
        this.productRowMapper = productRowMapper;
        this.vendorProductCountRowMapper = vendorProductCountRowMapper;
    }

    public Optional<Category> getCategoryById(int categoryId) {
        String sql = "SELECT * FROM category WHERE id = ?";
        return jdbcTemplate.query(sql, categoryRowMapper, categoryId)
                .stream().findFirst();
    }

    public Optional<Vendor> getVendorById(int vendorId) {
        String sql = "SELECT * FROM vendor WHERE id = ?";
        return jdbcTemplate.query(sql, vendorRowMapper, vendorId)
                .stream().findFirst();
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
        return jdbcTemplate.query(sql, productRowMapper, productId)
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
        return jdbcTemplate.query(sql, vendorProductCountRowMapper);
    }
}
