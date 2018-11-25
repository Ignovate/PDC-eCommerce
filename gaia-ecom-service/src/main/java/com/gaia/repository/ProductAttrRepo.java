package com.gaia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gaia.domain.ProductAttrEntity;

public interface ProductAttrRepo
		extends JpaRepository<ProductAttrEntity, Long>, JpaSpecificationExecutor<ProductAttrEntity> {

}
