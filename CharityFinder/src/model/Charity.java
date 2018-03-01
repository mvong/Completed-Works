/* Mark Vong
 * ITP 368
 * Final GUI project
 * mvong@usc.edu
 */
package model;

import resources.Constant;

// Model class for Charity 
// Contains all information pertaining to each charity such as name, address, mission, cause, rating
// Also depicts whether or not the charity is a favorite of the user

public class Charity {
	
	// Member variables depicting charity attributes
	private String name;
	private String address;
	private String state;
	private String postalCode;
	private String city;
	private String type;
	private String status;
	private String classification;
	
	// Default Constructor
	public Charity() {
		
	}
	
	// Constructor
	public Charity(String name, String address, 
			String city, String state, String postalCode, 
			String type, String status, String classification) {
		this.name = name != null && name.equals(Constant.NULL) ? Constant.NA : name;
		this.address = address != null && address.equals(Constant.NULL) ? "" : address;
		this.city = city != null && city.equals(Constant.NULL) ? "" : city;
		this.state = state != null && state.equals(Constant.NULL) ? "" : state;
		this.postalCode = postalCode != null && postalCode.equals(Constant.NULL) ? "" : postalCode; 
		this.type = type != null && type.equals(Constant.NULL) ? Constant.NA : type;
		this.status = status != null && status.equals(Constant.NULL) ? Constant.NA : status;
		this.classification = classification != null && classification.equals(Constant.NULL) ? Constant.NA : classification;
	}
	
	// Helper method to get charity's full address
	public String getFullAddress() {
		return address + "\n" + city + " " + state + " " + postalCode;
	}
		
	// Getters and setters for each variable
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
