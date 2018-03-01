/* Mark Vong
 * ITP 368
 * Final GUI project
 * mvong@usc.edu
 */

package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.Label;
import model.Charity;
import model.User;
import resources.Constant;
import resources.HttpRequest;
import resources.Routes;

// login controller to authenticate user and push new users to database

public class LoginController {
	
	// Check if user credentials are valid
	public static boolean signInUser(User user, Label errorLabel) throws IOException {
		String usernameRoute = Routes.usernameRoute(user);
		String foundUsername = HttpRequest.GET(usernameRoute);
		if(foundUsername.equals(Routes.nullRoute)) {
			errorLabel.setText(Constant.NO_USER);
			errorLabel.setVisible(true);
			return false;
		}
		String passwordRoute = Routes.getRouteWithKey(user, Routes.passwordKey);
		String foundUserPassword = HttpRequest.GET(passwordRoute);
		foundUserPassword = parsePassword(foundUserPassword);
		if(!foundUserPassword.equals(user.getPassword())) {
			errorLabel.setText(Constant.WRONG_PW);
			errorLabel.setVisible(true);
			return false;
		}
		HashMap<String, String> userData = jsonToDataMap(foundUsername);
		user.setAboutMe(userData.get(Routes.aboutMeKey));
		user.setAge(Integer.valueOf(userData.get(Routes.ageKey)));
		user.setFirstName(userData.get(Routes.firstNameKey));
		user.setLastName(userData.get(Routes.lastNameKey));
		user.setFavoriteCharities(jsonDataToCharityList(user));
		return true;
	}
	
	// Create new user and push to database
	public static boolean createUser(User user, Label errorLabel) throws IOException {
		String usernameRoute = Routes.usernameRoute(user);
		String foundUsername = HttpRequest.GET(usernameRoute);
		if(foundUsername.equals(Routes.nullRoute)) {
			String[] keys = {Routes.firstNameKey, Routes.lastNameKey, 
					Routes.ageKey, Routes.passwordKey, Routes.aboutMeKey};
			String[] values = {user.getFirstName(), user.getLastName(), 
					String.valueOf(user.getAge()), user.getPassword(), user.getAboutMe()};
			String userData = DatabaseController.convertDataToJSON(keys, values);
			HttpRequest.PATCH(Routes.usernameRoute(user), userData);
			return true;
		}
		errorLabel.setText(Constant.USER_EXISTS);
		errorLabel.setVisible(true);
		return false;
	}
	
	// Parse password string to remove double quotes
	private static String parsePassword(String password) {
		return password.replace("\"", "");
	}
	
	// Parse user json object
	private static HashMap<String, String> jsonToDataMap(String json) {
		json = json.replaceAll("[{\"}]", "");
		String[] pairs = json.split(",");
		HashMap<String, String> userFeats = new HashMap<String, String>();
		for(String s : pairs) {
			String[] pair = s.split(":");
			if(pair.length == 2) {
				userFeats.put(pair[0], pair[1]);
			}
		}
		return userFeats;
	}
	
	// Parse user json object for favorite charities
	private static ArrayList<Charity> jsonDataToCharityList(User user) {
		String charityRoute = Routes.getRouteWithKey(user, Routes.charityKey);
		String charityJSON = "";
		try {
			charityJSON = HttpRequest.GET(charityRoute);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<Charity> charityList = new ArrayList<Charity>();
		
		String[] charityArray  = charityJSON.split("},");
		
		for(String charityString : charityArray) {
			charityString = charityString.replaceAll("[{\"}]", "");
			String[] charityInfo = charityString.split(",");
			HashMap<String, String> cMap = new HashMap<String, String>();
			String charityName = "";
			for(String info : charityInfo) {
				String[] keyVals = info.split(":");
				if(keyVals.length == 2) {
					cMap.put(keyVals[0], keyVals[1]);
				}
				else if(keyVals.length == 3){
					charityName = keyVals[0];
					cMap.put(keyVals[1], keyVals[2]);
				}
			}
			if(cMap.size() > 0) {
				Charity charity = new Charity(charityName, cMap.get(CharityController.addressKey), 
						cMap.get(CharityController.cityKey), cMap.get(CharityController.stateKey),
						cMap.get(CharityController.postalCodeKey), cMap.get(CharityController.typeKey),
						cMap.get(CharityController.statusKey), cMap.get(CharityController.classificationKey));
				charityList.add(charity);	
			}
		}
		return charityList;
	}

	
}
