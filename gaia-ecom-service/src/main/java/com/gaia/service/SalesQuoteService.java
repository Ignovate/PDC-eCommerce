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

import com.gaia.domain.SalesQuoteEntity;
import com.gaia.repository.SalesQuoteRepo;

@Service
public class SalesQuoteService {

	@Autowired
	private SalesQuoteRepo repo;

	public SalesQuoteEntity addSalesQuote(SalesQuoteEntity request) {
		return repo.save(request);
	}

	public SalesQuoteEntity getSalesQuote(Long id) {
		return repo.findById(id).orElse(null);
	}

	public Page<SalesQuoteEntity> getSalesQuote(Map<String, String> map, Pageable pageable, Map<String,String> stringMap, Map<String,BigDecimal> decimalMap) {
		Specification<SalesQuoteEntity> spec = new Specification<SalesQuoteEntity>() {
			@Override
			public Predicate toPredicate(Root<SalesQuoteEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<Predicate>();
				decimalMap.forEach((k, v) -> {
					predicate.add(criteriaBuilder.equal(root.get(k), v));
				});
				stringMap.forEach((k, v) -> {
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

	public void deleteSalesQuote(Long id) {
		repo.deleteById(id);
	}

}
