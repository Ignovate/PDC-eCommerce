package com.gaia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaia.domain.CategoriesEntity;
import com.gaia.repository.CategoriesRepo;

@Service
public class CategoriesService {
	
	@Autowired
	private CategoriesRepo categoriesRepo;
	
	public CategoriesEntity addCategories(CategoriesEntity categories) {
		return categoriesRepo.save(categories);
	}
	
	public CategoriesEntity getCategories(Long id) {
		return categoriesRepo.findById(id).orElse(null);
	}
	
	public List<CategoriesEntity> getCategories() {
		return categoriesRepo.findAll();
	}
	
	public void deleteCategories(Long id) {
		categoriesRepo.deleteById(id);
	}

}
