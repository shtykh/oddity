package shtykh.oddity.source;

import java.util.Optional;

/**
 * Created by shtykh on 20/09/16.
 */
public interface Source {
	<T> Optional<T> get(Class<T> clazz, Object... params);
}
