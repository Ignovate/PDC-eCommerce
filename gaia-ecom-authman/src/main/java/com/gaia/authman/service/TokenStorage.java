package com.gaia.authman.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.gaia.authman.common.Constants;
import com.gaia.authman.common.GaiaException;

@Service
public class TokenStorage {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenStorage.class);

	@Autowired
	private CacheManager cacheManager;

	public void storeToken(String username, String token) {
		String existingToken = cacheManager.getCache(Constants.ACCESS_TOKEN_CACHE).get(username, String.class);
		if (!StringUtils.isEmpty(existingToken)) {
			invalidateToken(username, existingToken);
			existingToken = cacheManager.getCache(Constants.ACCESS_TOKEN_CACHE).get(username, String.class);
			LOGGER.info("existingToken after invalidating [{}]", existingToken);
		}
		cacheManager.getCache(Constants.ACCESS_TOKEN_CACHE).put(username, token);
		cacheManager.getCache(Constants.LAST_ACCESS_CACHE).put(username, System.currentTimeMillis());
	}

	private void invalidateToken(String username, String token) {
		evictToken(username);
	}

	private void evictToken(String username) {
		cacheManager.getCache(Constants.ACCESS_TOKEN_CACHE).evict(username);
		cacheManager.getCache(Constants.LAST_ACCESS_CACHE).evict(username);
	}

	public String retrieveAccessToken(String username, long expiry) throws GaiaException {
		String token = cacheManager.getCache(Constants.ACCESS_TOKEN_CACHE).get(username, String.class);
		
		if(token == null)
			throw new GaiaException(Constants.INVALID_TOKEN_CODE, Constants.INVALID_TOKEN_MSG);
		
		long now = System.currentTimeMillis();
		long lastAccessed = cacheManager.getCache(Constants.LAST_ACCESS_CACHE).get(username, Long.class);
		
		if(now > (lastAccessed + expiry)) {
			throw new GaiaException(Constants.TOKEN_EXPIRED_CODE, Constants.TOKEN_EXPIRED_MSG);
		}
		cacheManager.getCache(Constants.LAST_ACCESS_CACHE).put(username, now);
		return token;

	}

}
