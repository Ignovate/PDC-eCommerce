package com.gaia.authman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gaia.authman.domain.CustomersEntity;
import com.gaia.authman.domain.CustomersEntityPk;

public interface CustomersRepo
		extends JpaRepository<CustomersEntity, CustomersEntityPk>, JpaSpecificationExecutor<CustomersEntity> {

}
