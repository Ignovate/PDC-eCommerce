package com.gaia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gaia.domain.SalesOrderPaymentEntity;

public interface SalesOrderPaymentRepo
		extends JpaRepository<SalesOrderPaymentEntity, Long>, JpaSpecificationExecutor<SalesOrderPaymentEntity> {

}
