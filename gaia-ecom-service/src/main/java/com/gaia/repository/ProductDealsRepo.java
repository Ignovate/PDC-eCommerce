package com.gaia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gaia.domain.ProductDealsEntity;

public interface ProductDealsRepo
		extends JpaRepository<ProductDealsEntity, Long>, JpaSpecificationExecutor<ProductDealsEntity> {

}
