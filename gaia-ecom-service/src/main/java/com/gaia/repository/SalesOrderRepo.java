package com.gaia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gaia.domain.SalesOrderEntity;

public interface SalesOrderRepo
		extends JpaRepository<SalesOrderEntity, Long>, JpaSpecificationExecutor<SalesOrderEntity> {

}
