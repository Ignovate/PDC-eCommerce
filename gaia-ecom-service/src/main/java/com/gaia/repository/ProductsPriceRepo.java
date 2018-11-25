package com.gaia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gaia.domain.ProductsPriceEntity;

public interface ProductsPriceRepo
		extends JpaRepository<ProductsPriceEntity, Long>, JpaSpecificationExecutor<ProductsPriceEntity> {

}
