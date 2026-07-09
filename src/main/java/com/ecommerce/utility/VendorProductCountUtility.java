package com.ecommerce.utility;

import com.ecommerce.dto.VendorProductCountDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class VendorProductCountUtility implements RowMapper<VendorProductCountDto> {

    @Override
    public VendorProductCountDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        VendorProductCountDto vendorProductCountDto = new VendorProductCountDto(
                rs.getString("vendor_name"),
                rs.getInt("product_count")
        );
        return vendorProductCountDto;
    }
}
