/* Mark Vong
 * ITP 368
 * Final GUI project
 * mvong@usc.edu
 */
package resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

// Http request class to handle the API call procedures (GET, POST, PUT, PATCH, DELETE)

public class HttpRequest {
	
	// private static member variables
	private static URL url;
	private static HttpsURLConnection urlConnection;
	private static final String GET = "GET";
	private static final String POST = "POST";
	private static final String PUT = "PUT";
	private static final String PATCH = "PATCH";
	private static final String DELETE = "DELETE";
	private static final String USER = "User/1.0";
	private static final String OVERRIDE = "X-HTTP-Method-Override";
	
	// Delete entire route data
	public static boolean DELETE(String route) throws IOException {
		initConnection(route);
		urlConnection.setRequestMethod(DELETE);
		int responseCode = urlConnection.getResponseCode();
		if(responseCode != 200) {
			return false;
		}
		return true;
	}
	
	// Add data to route child without overwriting existing data
	public static boolean PATCH(String route, String data) throws IOException {
		initConnection(route);
		urlConnection.setRequestProperty(OVERRIDE, PATCH);
		urlConnection.setRequestMethod(POST);
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		writeData(data);
		urlConnection.connect();
		int responseCode = urlConnection.getResponseCode();
		if(responseCode != 200) {
			System.out.println("Response code: " + responseCode);
			return false;
		}
		return true;
	}
	
	// Append data with unique key 
	public static boolean POST(String route, String data) throws IOException {
		initConnection(route);
		urlConnection.setRequestMethod(POST);
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		writeData(data);
		urlConnection.connect();
		int responseCode = urlConnection.getResponseCode();
		if(responseCode != 200) {
			System.out.println("Response code: " + responseCode);
			return false;
		}
		return true;
	}
	
	// Overwrite data at the given route 
	public static boolean PUT(String route, String data) throws IOException {
		initConnection(route);
		urlConnection.setRequestMethod(PUT);
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		writeData(data);
		urlConnection.connect();
		int responseCode = urlConnection.getResponseCode();
		if(responseCode != 200) {
			System.out.println("Response code: " + responseCode);
			return false;
		}
		return true;
	}
	
	// Retrieve child data at route
	public static String GET(String route) throws IOException {
		initConnection(route);
		urlConnection.setRequestMethod(GET);
		int responseCode = urlConnection.getResponseCode();
		StringBuilder response = new StringBuilder("");
		if(responseCode == 200) {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream()));
			String inputString;
			
			while((inputString = br.readLine()) != null) {
				response.append(inputString);
			}
			br.close();
		}
		return response.toString();
	}
	
	// Initialize https connection
	private static void initConnection(String route) throws IOException {
		url = new URL(route);
		urlConnection = (HttpsURLConnection) url.openConnection();
		urlConnection.setRequestProperty("User", USER);
	}
	
	// Write data with bufferedwriter
	private static void writeData(String data) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
		bw.write(data);
		bw.flush();
		bw.close();
	}
}
