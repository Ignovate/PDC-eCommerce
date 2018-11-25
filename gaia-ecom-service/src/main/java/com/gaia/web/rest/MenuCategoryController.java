package com.gaia.web.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaia.common.GaiaException;
import com.gaia.service.MenuCategoryService;
import com.gaia.web.rest.vm.MenuCategoryResponse;

@RestController
@RequestMapping(path = "/api/v1.0", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuCategoryController {

	@Autowired
	private MenuCategoryService service;

	@GetMapping("menu")
	public ResponseEntity<Map<String, List<MenuCategoryResponse>>> getMenu() throws GaiaException {
		Map<String, List<MenuCategoryResponse>> response = service.getMenu();
		return new ResponseEntity<Map<String, List<MenuCategoryResponse>>>(response, HttpStatus.OK);
	}
}
