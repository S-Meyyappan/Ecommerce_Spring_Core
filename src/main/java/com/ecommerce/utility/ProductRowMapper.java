package com.ecommerce.utility;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.model.Vendor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Category category = new Category();
        Vendor vendor = new Vendor();
        Product product = new Product();

        category.setId(rs.getInt("category_id"));
        category.setName(rs.getString("category_name"));

        vendor.setId(rs.getInt("vendor_id"));
        vendor.setName(rs.getString("vendor_name"));

        product.setId(rs.getInt("product_id"));
        product.setName(rs.getString("product_name"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setStockQuantity(rs.getInt("stockQuantity"));
        product.setCategory(category);
        product.setVendor(vendor);

        return product;
    }
}
