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

import com.gaia.domain.ProductAttrEntity;
import com.gaia.repository.ProductAttrRepo;

@Service
public class ProductAttrService {

	@Autowired
	private ProductAttrRepo productAttrRepo;

	public ProductAttrEntity getProductAttr(Long id) {
		return productAttrRepo.findById(id).orElse(null);
	}

	public Page<ProductAttrEntity> getProductAttr(Map<String, String> map, Long id, Long productId, Pageable pageable) {
		Specification<ProductAttrEntity> spec = new Specification<ProductAttrEntity>() {

			@Override
			public Predicate toPredicate(Root<ProductAttrEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<Predicate>();

				map.forEach((k, v) -> {
					predicate.add(criteriaBuilder.equal(root.get(k), v));
				});

				if (id != null)
					predicate.add(criteriaBuilder.equal(root.get("id"), id));
				if (productId != null)
					predicate.add(criteriaBuilder.equal(root.get("productId"), productId));

				return criteriaBuilder.and(predicate.stream().toArray(Predicate[]::new));
			}

		};
		return productAttrRepo.findAll(spec, pageable);
	}

}
