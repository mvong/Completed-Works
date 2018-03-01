package model;
/* Mark Vong
 * ITP 368
 * Final GUI project
 * mvong@usc.edu
 */
import java.util.ArrayList;

// User class for application

public class User {
	
	// Member variables: username, password, first name, last name, age, favorite charities
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String aboutMe;
	private ArrayList<Charity> favoriteCharities;
	private ArrayList<String> charityStrings;
	private int age;
	
	// Initial constructor for testing
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.firstName = "Tommy";
		this.lastName = "Trojan";
		this.age = 18;
		this.aboutMe = "Just a short blurb about myself!";
		this.favoriteCharities = new ArrayList<Charity>();
	}
	
	// Constructor
	public User(String username, String password, String firstName, String lastName, int age, String aboutMe) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.aboutMe = aboutMe;
		this.favoriteCharities = new ArrayList<Charity>();
		this.charityStrings = new ArrayList<String>();
	}
	
	// getters and setters
	public ArrayList<String>getCharityStrings() {
		return this.charityStrings;
	}
	public void setCharityStrings(ArrayList<String> charityStrings) {
		this.charityStrings = charityStrings;
	}
	public String getAboutMe() {
		return aboutMe;
	}
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public ArrayList<Charity> getFavoriteCharities() {
		return favoriteCharities;
	}
	public void setFavoriteCharities(ArrayList<Charity> favoriteCharities) {
		this.favoriteCharities = favoriteCharities;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
