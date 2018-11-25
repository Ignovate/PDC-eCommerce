package com.gaia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.gaia.common.StringUtils;
import com.gaia.domain.CategoriesProductEntity;
import com.gaia.domain.ProductEntity;
import com.gaia.repository.ProductRepo;

@Service
public class ProductService {

	@Autowired
	private ProductRepo productRepo;

	public ProductEntity getProduct(Long id) {
		return productRepo.findById(id).orElse(null);
	}

	public Page<ProductEntity> getProduct(Map<String, Long> map, String sku, Pageable pageable) {
		Specification<ProductEntity> spec = new Specification<ProductEntity>() {

			@Override
			public Predicate toPredicate(Root<ProductEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<Predicate>();

				map.forEach((k, v) -> {
					predicate.add(criteriaBuilder.equal(root.get(k), v));
				});

				if (sku != null)
					predicate.add(criteriaBuilder.equal(root.get("sku"), sku));

				return criteriaBuilder.and(predicate.stream().toArray(Predicate[]::new));
			}

		};
		return productRepo.findAll(spec, pageable);
	}

}
