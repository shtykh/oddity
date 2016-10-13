package shtykh.oddity.source;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shtykh.oddity.rest.properties.HeaderReader;
import shtykh.oddity.rest.properties.PathReader;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Optional;

/**
 * Created by shtykh on 20/09/16.
 */
@Component
public class RatingRestClient implements StringSource {
	Logger log = Logger.getLogger(RatingRestClient.class);
	
	@Autowired
	private RestTemplate rest;
	
	@Autowired
	private PathReader paths;

	@Autowired
	private HeaderReader headerReader;

	@Override
	public Optional<String> get(Class<?> clazz, Object... parameters) {
		String resource = paths.getPath(clazz, parameters);
		System.out.println(MessageFormat.format("reading from {0}", resource));
		try {
			URI url = new URI(resource);
			HttpEntity<String> requestEntity = new RequestEntity<>(null, headerReader.getHeaders(), HttpMethod.GET, url);
			String result = rest.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
			return Optional.of(result);
		} catch (Exception e) {
			log.error("Getting from rest error", e);
			return Optional.empty();
		}
	}
}
