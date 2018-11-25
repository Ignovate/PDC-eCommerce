package com.gaia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gaia.domain.SalesOrderPaymentEntity;
import com.gaia.domain.SalesQuotePaymentEntity;

public interface SalesQuotePaymentRepo extends JpaRepository<SalesQuotePaymentEntity, Long>, JpaSpecificationExecutor<SalesQuotePaymentEntity> {

}
