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

import com.gaia.domain.ProductsTypeEntity;
import com.gaia.repository.ProductsTypeRepo;

@Service
public class ProductsTypeService {

	@Autowired
	private ProductsTypeRepo prodTypeRepo;

	public ProductsTypeEntity getProdTypes(Long id) {
		return prodTypeRepo.findById(id).orElse(null);
	}

	public Page<ProductsTypeEntity> getProdTypes(Map<String, Long> map, Pageable pageable) {
		Specification<ProductsTypeEntity> spec = new Specification<ProductsTypeEntity>() {

			@Override
			public Predicate toPredicate(Root<ProductsTypeEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<Predicate>();

				map.forEach((k, v) -> {
					predicate.add(criteriaBuilder.equal(root.get(k), v));
				});

				return criteriaBuilder.and(predicate.stream().toArray(Predicate[]::new));
			}

		};
		return prodTypeRepo.findAll(spec, pageable);
	}

}
