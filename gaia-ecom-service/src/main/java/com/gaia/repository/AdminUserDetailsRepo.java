package com.gaia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gaia.domain.AdminUserDetailsEntity;

public interface AdminUserDetailsRepo
		extends JpaRepository<AdminUserDetailsEntity, Long>, JpaSpecificationExecutor<AdminUserDetailsEntity> {

}
