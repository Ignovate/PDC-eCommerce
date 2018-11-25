package com.gaia.authman.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gaia.authman.common.GaiaException;
import com.gaia.authman.service.AuthenticateService;
import com.gaia.authman.web.rest.vm.Token;
import com.gaia.authman.web.rest.vm.AuthenticateResponse;

@RestController
@RequestMapping(path = "/api/authman/v1.0", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticateController {
	
	@Autowired
	private AuthenticateService service;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateController.class);

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<AuthenticateResponse> authenticate(@RequestBody Token request)
			throws GaiaException {
		LOGGER.info("username [{}] password [{}]", request.getUsername(), request.getPassword());
		AuthenticateResponse response = service.authenticate(request);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
