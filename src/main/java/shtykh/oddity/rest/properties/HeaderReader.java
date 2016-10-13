package shtykh.oddity.rest.properties;

import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import shtykh.oddity.util.args.SecondLevelPropertyReader;

/**
 * Created by shtykh on 20/09/16.
 */
public class HeaderReader extends SecondLevelPropertyReader {
	MultiValueMap<String, String> headers;

	public MultiValueMap<String, String> getHeaders() {
		return headers;
	}

	@Override
	public void afterRun() {
		super.afterRun();
		headers = new HttpHeaders();
		for (String name : getProperties().stringPropertyNames()) {
			headers.add(name, getProperty(name));
		}
	}

	@Override
	protected String getPropertiesName() {
		return "headers";
	}
}
