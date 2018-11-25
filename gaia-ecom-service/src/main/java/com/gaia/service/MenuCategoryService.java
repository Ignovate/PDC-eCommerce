package com.gaia.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gaia.domain.MenuCategoryRowMapper;
import com.gaia.web.rest.vm.MenuCategoryResponse;

@Service
public class MenuCategoryService {

	private String query = "select a.category_id, a.name, a.url_key, level, b.product_count, b.is_parent from categories_details a JOIN categories b on a.category_id=b.id";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Map<String, List<MenuCategoryResponse>> getMenu() {
		List<MenuCategoryResponse> response = jdbcTemplate.query(query, new MenuCategoryRowMapper());

		Map<String, List<MenuCategoryResponse>> resp = new HashMap<String, List<MenuCategoryResponse>>();

		List<MenuCategoryResponse> main = new ArrayList<MenuCategoryResponse>();
		List<MenuCategoryResponse> pre = new ArrayList<MenuCategoryResponse>();
		List<MenuCategoryResponse> sub = new ArrayList<MenuCategoryResponse>();

		response.stream().filter(q -> new Long(0).equals(q.getLevel())).forEach(r -> main.add(r));
		response.stream().filter(q -> new Long(1).equals(q.getLevel())).forEach(r -> pre.add(r));
		response.stream().filter(q -> new Long(2).equals(q.getLevel())).forEach(r -> sub.add(r));

		resp.put("mainMenu", main);
		resp.put("preMenu", pre);
		resp.put("subMenu", sub);
		return resp;
	}
}
