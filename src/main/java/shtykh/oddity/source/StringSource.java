package shtykh.oddity.source;

import java.util.Optional;

/**
 * Created by shtykh on 22/09/16.
 */
public interface StringSource {
	Optional<String> get(Class<?> clazz, Object... params);
}
