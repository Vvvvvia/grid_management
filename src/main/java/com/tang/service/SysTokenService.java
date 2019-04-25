package com.tang.service;


import com.tang.entity.SysLoginUser;
import com.tang.entity.Token;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.MapUtils;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * token存到redis的实现类<br>
 * jwt实现的token
 *
 *
 */
@Primary
@Service
public class SysTokenService {

	private static final Logger log = LoggerFactory.getLogger("adminLogger");

	/**
	 * token过期秒数
	 */
	@Value("${token.expire.seconds}")
	private Integer expireSeconds;
	@Autowired
	private RedisTemplate<String, SysLoginUser> redisTemplate;
	@Autowired
	private SysLogService logService;
	/**
	 * 私钥
	 */
	@Value("${token.jwtSecret}")
	private String jwtSecret;

	private static Key KEY = null;
	private static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";

	public Token saveToken(SysLoginUser loginUser) {
		loginUser.setToken(UUID.randomUUID().toString());
		cacheSysLoginUser(loginUser);
		// 登陆日志
		logService.save(loginUser.getId(), "登录",true);

		String jwtToken = createJWTToken(loginUser);

		return new Token(jwtToken, loginUser.getLoginTime());
	}

	/**
	 * 生成jwt
	 * 
	 * @param loginUser
	 * @return
	 */
	private String createJWTToken(SysLoginUser loginUser) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(LOGIN_USER_KEY, loginUser.getToken());// 放入一个随机字符串，通过该串可找到登陆用户

		String jwtToken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance())
				.compact();

		return jwtToken;
	}

	private void cacheSysLoginUser(SysLoginUser loginUser) {
		loginUser.setLoginTime(System.currentTimeMillis());
		loginUser.setExpireTime(loginUser.getLoginTime() + expireSeconds * 1000);
	// 根据uuid将loginUser缓存
		redisTemplate.boundValueOps(getTokenKey(loginUser.getToken())).set(loginUser, expireSeconds, TimeUnit.SECONDS);
}

	/**
	 * 更新缓存的用户信息
	 */

	public void refresh(SysLoginUser loginUser) {
		cacheSysLoginUser(loginUser);
	}


	public SysLoginUser getSysLoginUser(String jwtToken) {
		String uuid = getUUIDFromJWT(jwtToken);
		if (uuid != null) {
			return redisTemplate.boundValueOps(getTokenKey(uuid)).get();
		}

		return null;
	}


	public boolean deleteToken(String jwtToken) {
		String uuid = getUUIDFromJWT(jwtToken);
		if (uuid != null) {
			String key = getTokenKey(uuid);
			SysLoginUser loginUser = redisTemplate.opsForValue().get(key);
			if (loginUser != null) {
				redisTemplate.delete(key);
				// 退出日志
				logService.save(loginUser.getId(), "退出",true);

				return true;
			}
		}

		return false;
	}

	private String getTokenKey(String uuid) {
		return "tokens:" + uuid;
	}

	private Key getKeyInstance() {
		if (KEY == null) {
			synchronized (SysTokenService.class) {
				if (KEY == null) {// 双重锁
					byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
					KEY = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
				}
			}
		}

		return KEY;
	}

	private String getUUIDFromJWT(String jwtToken) {
		if ("null".equals(jwtToken) || StringUtils.isBlank(jwtToken)) {
			return null;
		}

		try {
			Map<String, Object> jwtClaims = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwtToken).getBody();
			return MapUtils.getString(jwtClaims, LOGIN_USER_KEY);
		} catch (ExpiredJwtException e) {
			log.error("{}已过期", jwtToken);
		} catch (Exception e) {
			log.error("{}", e);
		}

		return null;
	}
}
