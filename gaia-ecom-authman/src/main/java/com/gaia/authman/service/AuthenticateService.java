package com.gaia.authman.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.gaia.authman.common.Constants;
import com.gaia.authman.common.GaiaException;
import com.gaia.authman.domain.CustomersEntity;
import com.gaia.authman.repository.CustomersRepo;
import com.gaia.authman.web.rest.vm.Token;
import com.gaia.authman.web.rest.vm.AuthenticateResponse;

@Service
public class AuthenticateService {

	@Autowired
	private CustomersRepo repo;

	@Autowired
	private TokenService tokService;

	public AuthenticateResponse authenticate(Token request) throws GaiaException {
		CustomersEntity customer = authenticateCust(request);
		if (customer == null) {
			throw new GaiaException(Constants.INVALID_CRED_CODE, Constants.INVALID_CRED_MSG);
		}
		AuthenticateResponse response = tokService.createToken(request);
		return response;
	}

	private CustomersEntity authenticateCust(Token request) {

		Specification<CustomersEntity> specification = new Specification<CustomersEntity>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<CustomersEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(cb.equal(root.get("username"), request.getUsername()));
				predicates.add(cb.equal(root.get("password"), request.getPassword()));
				return cb.and(predicates.stream().toArray(Predicate[]::new));
			}

		};

		return repo.findOne(specification).get();

	}

}
