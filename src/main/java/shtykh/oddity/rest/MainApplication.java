package shtykh.oddity.rest;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import shtykh.oddity.rest.control.ResultController;
import shtykh.oddity.rest.properties.HeaderReader;
import shtykh.oddity.rest.properties.PathReader;
import shtykh.oddity.rest.properties.StringConstants;
import shtykh.oddity.source.RatingRestClient;
import shtykh.oddity.source.ResultClient;
import shtykh.oddity.source.cache.RedisAdapter;
import shtykh.oddity.util.HomeController;
import shtykh.oddity.util.JacksonAdapter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by shtykh on 08/04/15.
 */
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = "shtykh.oddity")
public class MainApplication {
	private static final Logger log = Logger.getLogger(MainApplication.class);
	
	public static void main(String[] args) throws Exception {
		try {
			Object[] classes = new Object[]{
					ResultController.class,
					RestTemplate.class,
					
					RedisAdapter.class,
					ResultClient.class,
					RatingRestClient.class,
					
					MainApplication.class,
					
					StringConstants.class,
					PathReader.class,
					HeaderReader.class,
					JacksonAdapter.class,
					
					HomeController.class
					
			};
			new SpringApplicationBuilder()
					.sources(classes)
					.build()
					.run(args);
			log.info("Application launched successfully, check it out on your localhost");
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}
}
