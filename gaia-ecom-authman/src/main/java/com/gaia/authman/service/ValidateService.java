package com.gaia.authman.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gaia.authman.common.Constants;
import com.gaia.authman.common.GaiaException;
import com.gaia.authman.web.rest.vm.Token;
import com.gaia.authman.web.rest.vm.ValidateResponse;

import io.jsonwebtoken.impl.DefaultClaims;

@Service
public class ValidateService {

	@Value("${token.expiry:86400000}")
	private long expiry;

	@Autowired
	private TokenHandler tokenHandler;

	@Autowired
	private TokenStorage tokenStorage;

	public ValidateResponse validateToken(String token) throws GaiaException {
		ValidateResponse response = new ValidateResponse();
		DefaultClaims claims = tokenHandler.extractToken(token);
		Token accessToken = new Token();
		String tokenFromCache = null;

		if (claims != null) {
			accessToken = tokenHandler.validateToken(token);
			tokenFromCache = tokenStorage.retrieveAccessToken(accessToken.getUsername(), expiry);
		}

		if (!StringUtils.equals(token, tokenFromCache)) {
			response.setResponseCode(Constants.INVALID_TOKEN_CODE);
			response.setResponseMsg(Constants.INVALID_TOKEN_MSG);
			return response;
		}

		response.setResponseCode(Constants.SUCCESS_CODE);
		response.setResponseMsg(Constants.SUCCESS_MSG);
		response.setToken(accessToken);
		return response;
	}

}
