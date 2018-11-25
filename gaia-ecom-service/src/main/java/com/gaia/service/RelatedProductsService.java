package com.gaia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gaia.domain.RelatedProductsRowMapper;
import com.gaia.web.rest.vm.RelatedProductsResponse;

@Service
public class RelatedProductsService {

	private String query = "select a.product_id, b.name, c.price, c.special_price, CONCAT('http://167.99.153.79:8080/gaia_ecom_admin/GAIA_VIEW_IMAGE_FILE?form=category&type=1&id=', a.product_id) imageurl from categories_products a JOIN products_attributes b ON a.product_id = b.product_id LEFT JOIN products_price c ON c.product_id = b.product_id where a.category_id = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<RelatedProductsResponse> getReLProd(Long categoryId) {
		List<RelatedProductsResponse> response = jdbcTemplate.query(query, new Object[] { categoryId },
				new RelatedProductsRowMapper());
		return response;
	}

}
