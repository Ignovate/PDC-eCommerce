package com.gaia.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gaia.web.rest.vm.RelatedProductsResponse;

public class RelatedProductsRowMapper implements RowMapper<RelatedProductsResponse>{

	@Override
	public RelatedProductsResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
		RelatedProductsResponse resp = new RelatedProductsResponse();
		resp.setProductId(rs.getLong("product_id"));
		resp.setName(rs.getString("name"));
		resp.setPrice(rs.getBigDecimal("price"));
		resp.setSpecialPrice(rs.getBigDecimal("special_price"));
		resp.setImageUrl(rs.getString("imageurl"));
		return resp;
	}

}
