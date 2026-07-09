package com.ecommerce.utility;

import com.ecommerce.model.Vendor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class VendorUtility implements RowMapper<Vendor> {
    @Override
    public Vendor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Vendor vendor = new Vendor();
        vendor.setId(rs.getInt("id"));
        vendor.setName(rs.getString("name"));
        vendor.setEmail(rs.getString("email"));
        return vendor;
    }
}
