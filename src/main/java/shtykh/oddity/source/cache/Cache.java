package shtykh.oddity.source.cache;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by shtykh on 22/09/16.
 */
public interface Cache {
	String get(String keyName);

	String set(String keyName, String value);

	Long del(String keyName);

	Long expire(String keyName, int seconds);

	Set<String> keys();

	default void clear() {
		Set<String> names=keys();
		Iterator<String> it = names.iterator();
		while (it.hasNext()) {
			String s = it.next();
//			System.out.println(s);
			del(s);
		}
	}
}
