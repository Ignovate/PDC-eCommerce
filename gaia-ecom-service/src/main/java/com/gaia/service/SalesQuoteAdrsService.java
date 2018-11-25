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

import com.gaia.domain.SalesQuoteAdrsEntity;
import com.gaia.repository.SalesQuoteAdrsRepo;

@Service
public class SalesQuoteAdrsService {
	
	@Autowired
	private SalesQuoteAdrsRepo repo;
	
	public SalesQuoteAdrsEntity addSalesQuoteAdrs(SalesQuoteAdrsEntity request) {
		return repo.save(request);
	}

	public SalesQuoteAdrsEntity getSalesQuoteAdrs(Long id) {
		return repo.findById(id).orElse(null);
	}

	public void deleteSalesQuoteAdrs(Long id) {
		repo.deleteById(id);
	}

	public Page<SalesQuoteAdrsEntity> getSalesQuoteAddr(Map<String, String> request, Long id, Long quoteId,
			Pageable pageable) {
		Specification<SalesQuoteAdrsEntity> spec = new Specification<SalesQuoteAdrsEntity>() {
			@Override
			public Predicate toPredicate(Root<SalesQuoteAdrsEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<Predicate>();

				request.forEach((k, v) -> {
					predicate.add(criteriaBuilder.equal(root.get(k), v));
				});

				if (id != null)
					predicate.add(criteriaBuilder.equal(root.get("id"), id));

				if (quoteId != null)
					predicate.add(criteriaBuilder.equal(root.get("quoteId"), quoteId));

				return criteriaBuilder.and(predicate.stream().toArray(Predicate[]::new));
			}

		};
		return repo.findAll(spec, pageable);

	}

}
