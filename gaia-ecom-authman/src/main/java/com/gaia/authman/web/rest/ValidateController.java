package com.gaia.authman.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaia.authman.common.GaiaException;
import com.gaia.authman.service.ValidateService;
import com.gaia.authman.web.rest.vm.ValidateResponse;

@RestController
@RequestMapping(path = "/api/authman/v1.0", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ValidateController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateController.class);
	@Autowired
	private ValidateService service;

	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public ResponseEntity<ValidateResponse> validate(@RequestParam("token") final String token) throws GaiaException {
		ValidateResponse response = service.validateToken(token);
		LOGGER.info("response :{}", response);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
