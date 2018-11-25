package com.gaia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gaia.domain.ProductsStatusEntity;

public interface ProductsStatusRepo
		extends JpaRepository<ProductsStatusEntity, Long>, JpaSpecificationExecutor<ProductsStatusEntity> {

}
