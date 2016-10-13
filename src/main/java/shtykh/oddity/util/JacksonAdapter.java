package shtykh.oddity.util;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by shtykh on 15/09/16.
 */
@Component
public class JacksonAdapter implements Serializer {
	private static Logger log = Logger.getLogger(JacksonAdapter.class);
	private ObjectMapper mapper = new ObjectMapper();
	
	private JacksonAdapter() {
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}
	
	@Override
	public <T> Optional<T> read(String string, Class<T> clazz){
		try {
			return Optional.of(mapper.readValue(string, clazz));
		} catch (Exception e) {
			log.error(e);
			return Optional.empty();
		}
	}

	@Override
	public <T> Optional<String> write(T object) {
		try {
			return Optional.of(mapper.writeValueAsString(object));
		} catch (IOException e) {
			log.error(e);
			return Optional.empty();
		}
	}

	@Override
	public <T> String key(Class<T> clazz, Object[] params) {
		return MessageFormat.format("{0}_{1}",
				clazz.getSimpleName(),
				Stream.of(params)
						.map(String::valueOf).
						reduce((s1, s2) -> s1 + "_" + s2).orElse(""));
	}
}
