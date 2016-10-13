package shtykh.oddity.source.cache;

import shtykh.oddity.source.Source;
import shtykh.oddity.source.StringSource;
import shtykh.oddity.util.Serializer;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by shtykh on 19/09/16.
 */
public interface CachedSource extends Source {
	
	StringSource getSource();

	Serializer getSerializer();

	Cache getCache();

	@Override
	default <T> Optional<T> get(Class<T> clazz, Object... params) {
		return get(clazz, () -> getSource().get(clazz, params), Optional::empty, params);
	}

	default <T> Optional<T> get(final Class<T> clazz,
								final Supplier<Optional<T>> makingObject,
								final Object... params) {
		return get(clazz, () -> getSource().get(clazz, params), makingObject, params);
	}
	
	@Deprecated
	/**
	 ** One should use
	 */
	default <T> Optional<T> get(final Class<T> clazz,
					  final Supplier<Optional<String>> readingObject,
					  final Supplier<Optional<T>> makingObject,
					  final Object... params) {
		final String key = getSerializer().key(clazz, params);
		Supplier<Optional<T>> decache = () -> {
			try{
				String cached = getCache().get(key);
				if(cached == null) {
					throw new NullPointerException();
				}
				return getSerializer().read(cached, clazz);
			} catch (Exception e) {
				return Optional.empty();
			}
		};
		Supplier<Optional<T>> getStoreAndRead = () -> {
			try {
				String gotten = readingObject.get()
						.orElseThrow(() -> new Exception("Could not get " + clazz));
				setCached(key, gotten);
				return getSerializer().read(gotten, clazz);
			} catch (Exception e) {
				return Optional.empty();
			}
		};
		Supplier<Optional<T>> makeWriteAndReturn = () -> {
			Optional<T> madeOrNot = makingObject.get();
			madeOrNot.ifPresent(made -> {
				try {
					String written = getSerializer().write(made).get();
					setCached(key, written);
				} catch (Exception e) {
					Optional.empty();
				}
			});
			return madeOrNot;
		};
		return Stream.of(decache, getStoreAndRead, makeWriteAndReturn)
				.map(Supplier::get)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.findFirst();
	}

	default void setCached(String key, String written) {
		try{
			getCache().set(key, written);
		} catch (Exception ignored) {
			ignored.printStackTrace(); // todo
		}
	}
}
