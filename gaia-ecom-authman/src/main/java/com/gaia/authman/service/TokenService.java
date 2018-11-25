package com.gaia.authman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gaia.authman.common.Constants;
import com.gaia.authman.web.rest.vm.Token;
import com.gaia.authman.web.rest.vm.AuthenticateResponse;

@Service
public class TokenService {
	
	@Value("${token.expiry:86400000}")
	private long expiry;
	
	@Autowired
	private TokenHandler tokenHandler;
	
	@Autowired
	private TokenStorage tokenStorage;
	
	public AuthenticateResponse createToken(Token request) {
		AuthenticateResponse response = new AuthenticateResponse();
		String token = tokenHandler.createToken(request);
		tokenStorage.storeToken(request.getUsername(), token);
		response.setToken(token);
		response.setResponseCode(Constants.SUCCESS_CODE);
		response.setResponseMsg(Constants.SUCCESS_MSG);
		response.setExpiresIn(expiry);
		return response;
		
	}

}
