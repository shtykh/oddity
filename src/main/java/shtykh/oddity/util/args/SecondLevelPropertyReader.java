package shtykh.oddity.util.args;

/**
 * Created by shtykh on 20/09/16.
 */
public abstract class SecondLevelPropertyReader extends PropertyReader {
	protected static PropertyReader instance;
	
	protected SecondLevelPropertyReader() {
		instance = this;
	}

	@Override
	public void afterRun() {
		readFromFile(getProperty(getPropertiesName()));
	}

	protected abstract String getPropertiesName();
}
