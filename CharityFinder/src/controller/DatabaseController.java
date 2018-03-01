/* Mark Vong
 * ITP 368
 * Final GUI project
 * mvong@usc.edu
 */
package controller;

import java.util.ArrayList;

// Database controller class to convert strings to json objects

public class DatabaseController {
	
	// convert an arraylist of charities to a json object for firebase POST/PATCH call
	public static String convertArrayListToJSON(ArrayList<String> charities) {
		StringBuilder JSONString = new StringBuilder("");
		JSONString.append("{");
		for(int i = 0 ; i < charities.size() ; i++) {
			JSONString.append("\"");
			JSONString.append("Charity_" + i);
			JSONString.append("\"");
			JSONString.append(":");
			JSONString.append("\"");
			JSONString.append(charities.get(i));
			JSONString.append("\"");			
			if(i < charities.size() - 1) {
				JSONString.append(",");
			}
		}
		JSONString.append("}");
		return JSONString.toString();
	}
	
	// Convert user data to json object
	public static String convertDataToJSON(String[] key, String[] value) {
		StringBuilder JSONString = new StringBuilder("");
		JSONString.append("{");
		for(int i = 0 ; i < key.length ; i++) {
			JSONString.append("\"");
			JSONString.append(key[i]);
			JSONString.append("\"");
			JSONString.append(":");
			JSONString.append("\"");
			JSONString.append(value[i]);
			JSONString.append("\"");
			if(i < key.length - 1) {
				JSONString.append(",");
			}
		}
		JSONString.append("}");
		return JSONString.toString();
	}
}
