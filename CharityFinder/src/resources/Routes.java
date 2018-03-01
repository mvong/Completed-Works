/* Mark Vong
 * ITP 368
 * Final GUI project
 * mvong@usc.edu
 */
package resources;

import model.User;

// Static class to store routes for the HTTP Requests
// Contains routes for both Firebase API and Charity Navigator API calls

public class Routes {
	// Firebase
	public static final String nullRoute = "null";
	public static final String baseRoute = "https://charityfinder-6e2ba.firebaseio.com/";
	public static final String userRoute = baseRoute + "Users";
	public static final String passwordKey = "Password"; 
	public static final String firstNameKey = "First_Name";
	public static final String lastNameKey = "Last_Name";
	public static final String ageKey = "Age";
	public static final String aboutMeKey = "About_Me";
	public static final String charityKey = "Favorite_Charities";
	
	public static String usernameRoute(User user) {
		return userRoute + "/" + user.getUsername() + ".json";
	}
	public static String getRouteWithKey(User user, String key) {
		return userRoute + "/" + user.getUsername() + "/" + key + ".json";
	}
	public static String getRouteWithKeys(User user, String keyOne, String keyTwo) {
		return userRoute + "/" + user.getUsername() + "/" + keyOne + "/" + keyTwo + ".json";
	}
	
	// Charity Navigator 
	public static final String rootAPIUrl = "https://api.data.charitynavigator.org/v2/Organizations?";
	public static final String apiKey = "123d72a4356822be3c50c16058394fe7";
	public static final String apiID = "1f022b7d";
	public static final String baseAPIUrl = rootAPIUrl + "app_id=" + apiID + "&app_key=" + apiKey;
	public static final String defaultStateUrl = baseAPIUrl + "&state=CA";
	
	public static String stateRoute(String state) {
		return baseAPIUrl + "&state=" + state;
	}
	
	
	
	
	
	
}
