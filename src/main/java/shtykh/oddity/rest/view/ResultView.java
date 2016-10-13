package shtykh.oddity.rest.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shtykh.oddity.util.Serializer;

import java.text.MessageFormat;

/**
 * Created by shtykh on 20/09/16.
 */
@Component
public class ResultView {
	
	@Autowired
	Serializer serializer;
	
	String view(Object obj) {
		try {
			return serializer.write(obj).get();
		} catch (Exception e) {
			return error(e);
		}
	}

	private String error(Exception e) {
		return MessageFormat.format("{0}: {1}", e.getClass().getSimpleName(), e.getLocalizedMessage());
	}
}
