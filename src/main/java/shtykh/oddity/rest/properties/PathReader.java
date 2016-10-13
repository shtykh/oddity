package shtykh.oddity.rest.properties;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import shtykh.oddity.util.args.SecondLevelPropertyReader;

import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by shtykh on 20/09/16.
 */
public class PathReader extends SecondLevelPropertyReader {
	private Multimap<Class, String> pathTemplates;

	@Override
	public void afterRun() {
		super.afterRun();
		initMethodTemplates();
	}

	private void initMethodTemplates() {
		pathTemplates = ArrayListMultimap.create();
		for (String name : getProperties().stringPropertyNames()) {
			Arrays.stream(getProperty(name).split("\\|")).forEach(new Consumer<String>() {
				@Override
				public void accept(String s) {
					try {
						pathTemplates.put(Class.forName(name), s);
					} catch (ClassNotFoundException e) {
						throw new RuntimeException(e);
					}
				}
			});

		}
	}

	@Override
	protected String getPropertiesName() {
		return "paths";
	}
	
	public <T> String getPath(Class<T> clazz, final Object... parameters) {
		return getPathTemplate(clazz)
				.map(template -> createPath(template, parameters))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.findAny()
				.get();
	}

	private Optional<String> createPath(String template, Object[] parameters) {
		try {
			String path = String.format(template, parameters);
			return Optional.of(path);
		} catch (IllegalFormatException e) {
			return Optional.empty();
		}
	}

	private <T> Stream<String> getPathTemplate(Class<T> clazz) {
		return pathTemplates.get(clazz).stream();
	}

}
