package com.gaia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gaia.domain.SalesQuoteItemsEntity;

public interface SalesQuoteItemsRepo extends JpaRepository<SalesQuoteItemsEntity, Long>, JpaSpecificationExecutor<SalesQuoteItemsEntity> {

}
