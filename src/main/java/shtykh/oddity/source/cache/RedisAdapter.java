package shtykh.oddity.source.cache;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisConnectionException;
import shtykh.oddity.util.args.PropertyReader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by shtykh on 19/09/16.
 */
@Component
public class RedisAdapter extends PropertyReader implements Cache {
	private JedisPool redisPool;
	private Map<String, Method> methods;
	private int expireTime;

	public RedisAdapter() throws NoSuchMethodException {
		initMethods();
	}

	@Override
	public void afterRun() {
		String host = getProperty("redis.host");
		int port = Integer.decode(getProperty("redis.port"));
		redisPool = new JedisPool(new JedisPoolConfig(), host, port, Protocol.DEFAULT_TIMEOUT);
		expireTime = Integer.decode(getProperty("redis.expire.seconds"));
	}

	private void initMethods() throws NoSuchMethodException {
		methods = new HashMap<>();
		methods.put("get", Jedis.class.getMethod("get", String.class));
		methods.put("set", Jedis.class.getMethod("set", String.class, String.class));
		methods.put("expire", Jedis.class.getMethod("expire", String.class, int.class));
		methods.put("keys", Jedis.class.getMethod("keys", String.class));
		methods.put("del", Jedis.class.getMethod("del", String.class));
	}

	private Method getMethod(String name) {
		return methods.get(name);
	}

	public <T> T call(String methodName, Class<T> returnClass, Object... parameters) {
		Jedis redis = null;
		try {
			redis = redisPool.getResource();
			Method method = getMethod(methodName);
			return returnClass.cast(method.invoke(redis, parameters));
		} catch (JedisConnectionException | InvocationTargetException | IllegalAccessException e) {
			if (redis != null) {
				redisPool.returnBrokenResource(redis);
				redis = null;
			}
			throw new RuntimeException(e);
		} finally {
			if (redis != null) {
				redisPool.returnResource(redis);
			}
		}
	}

	@Override
	public String get(String keyName) {
		return call("get", String.class, keyName);
	}

	@Override
	public String set(String keyName, String value) {
		String result = call("set", String.class, keyName, value);
		expire(keyName, expireTime);
		return result;
	}

	@Override
	public Long del(String keyName) {
		return call("del", Long.class, keyName);
	}

	@Override
	public Long expire(String keyName, int seconds) {
		return call("expire", Long.class, keyName, seconds);
	}

	@Override
	public Set<String> keys() {
		return call("keys", Set.class, "*");
	}

}
