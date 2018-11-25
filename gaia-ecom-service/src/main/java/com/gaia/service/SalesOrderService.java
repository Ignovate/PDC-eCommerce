package com.gaia.service;

import java.math.BigDecimal;
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

import com.gaia.domain.SalesOrderEntity;
import com.gaia.repository.SalesOrderRepo;

@Service
public class SalesOrderService {

	@Autowired
	private SalesOrderRepo repo;

	public SalesOrderEntity addSalesOrder(SalesOrderEntity request) {
		return repo.save(request);
	}

	public SalesOrderEntity getSalesOrder(Long id) {
		return repo.findById(id).orElse(null);
	}

	public Page<SalesOrderEntity> getSalesOrder(Map<String, String> map, Pageable pageable, Map<String,Long> longMap, Map<String,BigDecimal> decimalMap) {
		Specification<SalesOrderEntity> spec = new Specification<SalesOrderEntity>() {
			@Override
			public Predicate toPredicate(Root<SalesOrderEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<Predicate>();
				decimalMap.forEach((k, v) -> {
					predicate.add(criteriaBuilder.equal(root.get(k), v));
				});
				longMap.forEach((k, v) -> {
					predicate.add(criteriaBuilder.equal(root.get(k), v));
				});
				map.forEach((k, v) -> {
					predicate.add(criteriaBuilder.equal(root.get(k), v));
				});
				return criteriaBuilder.and(predicate.stream().toArray(Predicate[]::new));
			}

		};
		return repo.findAll(spec, pageable);

	}

	public void deleteSalesOrder(Long id) {
		repo.deleteById(id);
	}

}
