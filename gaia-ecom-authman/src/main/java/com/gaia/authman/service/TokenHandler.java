package com.gaia.authman.service;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaia.authman.web.rest.vm.Token;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

@Service
public class TokenHandler {

	final SignatureAlgorithm signatureAlgorithm;
	final Key key;
	final ObjectMapper objectMapper;

	public TokenHandler(byte[] secret) {
		signatureAlgorithm = SignatureAlgorithm.HS256;
		key = new SecretKeySpec(secret, signatureAlgorithm.getJcaName());
		objectMapper = new ObjectMapper();
	}

	public String createToken(Token request) {
		JwtBuilder builder = Jwts.builder().setPayload(getJsonString(request)).signWith(signatureAlgorithm, key);
		return builder.compact();

	}
	
	public Token validateToken(String token) {
		Token tokenVo = null;
		Jwt<?, ?> jwt = Jwts.parser().setSigningKey(key).parse(token);
		DefaultClaims claims = (DefaultClaims) jwt.getBody();
		if(claims != null) {
			tokenVo = new Token();
			tokenVo.setUsername(claims.get("username", String.class));
			tokenVo.setPassword(claims.get("password", String.class));
		}
		return tokenVo;
	}
	
	public DefaultClaims extractToken(String token) {
		Jwt<?, ?> jwt = Jwts.parser().setSigningKey(key).parse(token);
		DefaultClaims claims = (DefaultClaims) jwt.getBody();
		return claims;
	}

	private String getJsonString(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
