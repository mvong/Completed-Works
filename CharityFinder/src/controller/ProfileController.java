/* Mark Vong
 * ITP 368
 * Final GUI project
 * mvong@usc.edu
 */
package controller;

import java.io.IOException;

import model.User;
import resources.HttpRequest;
import resources.Routes;

// Profile controller class to communicate changes between views as well as to the DB

public class ProfileController {
	
	// update the user info 
	public static void updateUserInfo(User user) {
		// update user's first name
		String firstNameRoute = Routes.getRouteWithKey(user, Routes.firstNameKey);
		// update user's last name
		String lastNameRoute = Routes.getRouteWithKey(user, Routes.lastNameKey);
		// update user's age
		String ageRoute = Routes.getRouteWithKey(user, Routes.ageKey);
		// update user's about me
		String aboutRoute = Routes.getRouteWithKey(user, Routes.aboutMeKey);
		
		// PUT requests
		try {
			HttpRequest.PUT(firstNameRoute, getSingleJSON(user.getFirstName()));
			HttpRequest.PUT(lastNameRoute, getSingleJSON(user.getLastName()));
			HttpRequest.PUT(ageRoute, getSingleJSON(String.valueOf(user.getAge())));
			HttpRequest.PUT(aboutRoute, getSingleJSON(user.getAboutMe()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// return string for PUT request
	private static String getSingleJSON(String data) {
		return "\"" + data + "\"";
	}
}
