package shtykh.oddity.util;

import java.util.Optional;

/**
 * Created by shtykh on 22/09/16.
 */
public interface Serializer {
	<T> Optional<T> read(String string, Class<T> clazz);

	<T> Optional<String> write(T object);

	<T> String key(Class<T> clazz, Object[] params);
}
