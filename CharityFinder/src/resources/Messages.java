/* Mark Vong
 * ITP 368
 * Final GUI project
 * mvong@usc.edu
 */
package resources;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

// Messages class for internationalization

public class Messages {
	public static final String BUNDLE_NAME = "resources.messages"; //$NON-NLS-1$
	public static final String BUNDLE_NAME_ES = "resources.messages_es";

	public static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static void setResourceBundle(String BUNDLE_NAME) {
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	}
	
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
