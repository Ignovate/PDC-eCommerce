package com.gaia.authman.configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gaia.authman.common.Constants;
import com.google.common.cache.CacheBuilder;

@Configuration
@EnableCaching
public class CacheConfiguration extends CachingConfigurerSupport {

	@Value("${cache.timeout}")
	private Long cacheTimeout;

	@Bean()
	public CacheManager cacheManager() {
		SimpleCacheManager simpleCacheManager = new SimpleCacheManager();

		GuavaCache accessTokenCache = new GuavaCache(Constants.ACCESS_TOKEN_CACHE,
				CacheBuilder.newBuilder().expireAfterWrite(cacheTimeout, TimeUnit.MINUTES).maximumSize(99999).build());

		GuavaCache lastAccessCache = new GuavaCache(Constants.LAST_ACCESS_CACHE,
				CacheBuilder.newBuilder().expireAfterWrite(cacheTimeout, TimeUnit.MINUTES).maximumSize(99999).build());

		simpleCacheManager.setCaches(Arrays.asList(accessTokenCache, lastAccessCache));

		return simpleCacheManager;
	}

}
