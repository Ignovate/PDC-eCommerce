package com.gaia.service;

import java.time.LocalDateTime;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.gaia.common.Constants;
import com.gaia.common.ErrorCodes;
import com.gaia.common.GaiaException;
import com.gaia.domain.UserIdMapEntity;
import com.gaia.domain.UserMasterEntity;
import com.gaia.repository.UserIdMapRepo;
import com.gaia.repository.UserMasterRepo;
import com.gaia.web.rest.vm.LoginReqVm;
import com.gaia.web.rest.vm.RegisterUserReqVm;

@Service
public class UserService {

	@Autowired
	private UserMasterRepo userMasterRepo;

	@Autowired
	private UserIdMapRepo userIdMapRepo;

	public void login(LoginReqVm loginReq) throws GaiaException {
		UserIdMapEntity userEntity = getuserlogindetails(loginReq.getUsername());
		if (userEntity == null)
			throw new GaiaException(ErrorCodes.CODE_INCORRECT_USER, ErrorCodes.MSG_INCORRECT_USER);

		UserMasterEntity userMaster = getUsermaster(userEntity.getUserId());

		if (Constants.BLOCK.equals(userMaster.getUserStatus())) {
			throw new GaiaException(ErrorCodes.CODE_USER_BLOCK, ErrorCodes.MSG_USER_BLOCK);
		}
		if (Constants.LOCK.equals(userMaster.getUserStatus())) {
			throw new GaiaException(ErrorCodes.CODE_USER_LOCKED, ErrorCodes.MSG_USER_LOCKED);
		}

		if (!loginReq.getPassword().equals(userEntity.getPassword())) {
			int count = userEntity.getIncorrectPasswdCnt();
			userEntity.setIncorrectPasswdCnt(count + 1);
			userIdMapRepo.save(userEntity);
			throw new GaiaException(ErrorCodes.CODE_INVALID_CRED, ErrorCodes.MSG_INVALID_CRED);
		}
		
		if(Constants.NEW.equals(userMaster.getUserStatus())) {
			userMaster.setUserStatus(Constants.ACTIVE);
			userMaster.setPrevUserStatus(Constants.NEW);
			userMasterRepo.save(userMaster);
		}
		
		userEntity.setLastLoginDate(LocalDateTime.now());
		if(userEntity.getIncorrectPasswdCnt() != 0) {
			userEntity.setIncorrectPasswdCnt(0);
		}
		userIdMapRepo.save(userEntity);

	}

	private UserIdMapEntity getuserlogindetails(String username) {
		Specification<UserIdMapEntity> specification = new Specification<UserIdMapEntity>() {
			@Override
			public Predicate toPredicate(Root<UserIdMapEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.and(cb.equal(root.get("loginUserId"), username));
			}
		};

		UserIdMapEntity userEntity = userIdMapRepo.findOne(specification).orElse(null);
		return userEntity;
	}

	public UserMasterEntity getUsermaster(Long userId) {
		Specification<UserMasterEntity> specification = new Specification<UserMasterEntity>() {
			@Override
			public Predicate toPredicate(Root<UserMasterEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.and(cb.equal(root.get("userId"), userId));
			}
		};

		return userMasterRepo.findOne(specification).orElse(null);
	}

	public void signup(RegisterUserReqVm regUser) throws GaiaException {
		UserIdMapEntity userEntity = getuserlogindetails(regUser.getUsername());
		if (userEntity != null) {
			throw new GaiaException(ErrorCodes.CODE_USERNAME_PRESENT, ErrorCodes.MSG_USERNAME_PRESENT);
		}
		if (!regUser.getPassword().equals(regUser.getConfirmPassword())) {
			throw new GaiaException(ErrorCodes.CODE_CONFIRM_PASS_MISMATCH, ErrorCodes.MSG_CONFIRM_PASS_MISMATCH);
		}
		UserMasterEntity userMaster = new UserMasterEntity();
		userMaster.setUserName(regUser.getName());
		userMaster.setUserGroups(regUser.getUserGroup());
		userMaster.setUserType(regUser.getUserType());
		userMaster.setUserStatus(Constants.NEW);
		userMaster.setEmail(regUser.getEmail());
		userMaster.setMobile(regUser.getMobile());
		userMaster.setMakerId(regUser.getUsername());

		userMaster = userMasterRepo.save(userMaster);

		userEntity = new UserIdMapEntity();
		userEntity.setUserId(userMaster.getUserId());
		userEntity.setLoginUserId(regUser.getUsername());
		userEntity.setPassword(regUser.getPassword());
		userEntity.setMakerId(regUser.getUsername());
		userEntity.setMakerDtTm(userMaster.getMakerDtTm());
		userEntity.setIncorrectPasswdCnt(0);

		userIdMapRepo.save(userEntity);
	}

}
