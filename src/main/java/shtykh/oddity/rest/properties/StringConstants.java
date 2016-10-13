package shtykh.oddity.rest.properties;

import org.springframework.stereotype.Component;
import shtykh.oddity.util.args.SecondLevelPropertyReader;

import java.text.MessageFormat;

/**
 * Created by shtykh on 31/08/16.
 */
@Component
public class StringConstants extends SecondLevelPropertyReader {
	@Override
	protected String getPropertiesName() {
		return "strings";
	}

	public static String getString(String key, Object... objects) {
		return MessageFormat.format(instance.getProperty(key), objects);
	}

}
