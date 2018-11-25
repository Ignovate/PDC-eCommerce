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

import com.gaia.domain.SalesQuotePaymentEntity;
import com.gaia.repository.SalesQuotePaymentRepo;

@Service
public class SalesQuotePaymentService {
	@Autowired
	private SalesQuotePaymentRepo repo;

	public SalesQuotePaymentEntity addSalesQuotePay(SalesQuotePaymentEntity request) {
		return repo.save(request);
	}

	public SalesQuotePaymentEntity getSalesQuotePay(Long id) {
		return repo.findById(id).orElse(null);
	}

	public Page<SalesQuotePaymentEntity> getSalesQuotePay(Map<String, String> request, Map<String, Long> longMap,
			Pageable pageable) {
		Specification<SalesQuotePaymentEntity> spec = new Specification<SalesQuotePaymentEntity>() {
			@Override
			public Predicate toPredicate(Root<SalesQuotePaymentEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<Predicate>();

				request.forEach((k, v) -> {
					predicate.add(criteriaBuilder.equal(root.get(k), v));
				});

				longMap.forEach((k, v) -> {
					predicate.add(criteriaBuilder.equal(root.get(k), v));
				});

				return criteriaBuilder.and(predicate.stream().toArray(Predicate[]::new));
			}

		};
		return repo.findAll(spec, pageable);

	}

	public void deleteSalesQuoteItem(Long id) {
		repo.deleteById(id);
	}

}
