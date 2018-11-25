package com.gaia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gaia.domain.SalesQuoteEntity;

public interface SalesQuoteRepo
		extends JpaRepository<SalesQuoteEntity, Long>, JpaSpecificationExecutor<SalesQuoteEntity> {

}
