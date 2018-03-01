/* Mark Vong
 * ITP 368
 * Final GUI project
 * mvong@usc.edu
 */

package model;

// User singleton class to avoid passing user instance between views

public class UserSingleton {
	
	// user/singleton instances
	private static UserSingleton userInstance;
	private static User user;
	
	// private constructor
	private UserSingleton(User user) {
		UserSingleton.user = user;
	}
	
	// init instances
	public static UserSingleton getInstance(User user) {
		if(userInstance == null) {
			userInstance = new UserSingleton(user);
		}
		return userInstance;
	}
	
	// return user instance
	public static User getUser() {
		return UserSingleton.user;
	}
	
	// clear current user if logged out
	public static void logOut() {
		UserSingleton.userInstance = null;
		UserSingleton.user = null;
	}
}
