/* Mark Vong
 * ITP 368
 * Final GUI project
 * mvong@usc.edu
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import model.Charity;
import model.User;
import resources.HttpRequest;
import resources.Routes;

// Charity controller to handle the JSON object parsing for each charity

public class CharityController {
	
	// keys to retrieve respective fields in charity json object
	public static final String nameKey = "charityName";
	public static final String addressKey = "streetAddress1";
	public static final String cityKey = "city";
	public static final String stateKey = "stateOrProvince";
	public static final String postalCodeKey = "postalCode";
	public static final String typeKey = "nteeType";
	public static final String statusKey = "foundationStatus";
	public static final String classificationKey = "nteeClassification";
	
	// list of state abbreviations 
	public static ArrayList<String> stateAbbrevs = new ArrayList<String>() {/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{add("AK"); add("AL"); add("AZ"); add("AR"); add("CA"); add("CO"); 
			add("CT"); add("DE"); add("FL"); add("GA"); add("HI"); 
			add("ID"); add("IL"); add("IN"); add("IA"); add("KS"); 
			add("KY"); add("LA"); add("ME"); add("MD"); add("MA"); 
			add("MI"); add("MN"); add("MS"); add("MO"); add("MT"); 
			add("NE"); add("NV"); add("NH"); add("NJ"); add("NM"); 
			add("NY"); add("NC"); add("ND"); add("OH"); add("OK"); 
			add("OR"); add("PA"); add("RI"); add("SC"); add("SD"); 
			add("TN"); add("TX"); add("UT"); add("VT"); add("VA"); 
			add("WA"); add("WV"); add("WI"); add("WY");}};
		
	// Parse through the charity JSON to retrieve the variables listed above and create new charity objects
	// Return list of charities
	private static ArrayList<Charity> parseCharityJSON(String json) {
		String[] charityJSON = json.split("}}}},");
		ArrayList<HashMap<String, String>> listOfCharityInfo = new ArrayList<HashMap<String, String>>();
		for(String s : charityJSON) {
			s = s.replaceAll("[[{\"}]]", "");
			String[] charityInfo = s.split(",");
			HashMap<String, String> cInfoMap = new HashMap<String, String>();
			for(String k : charityInfo) {
				String[] furtherCharityInfo = k.split(":");
				if(furtherCharityInfo.length == 2) {
					cInfoMap.put(furtherCharityInfo[0], furtherCharityInfo[1]);
				}
			}
			listOfCharityInfo.add(cInfoMap);
		}
		ArrayList<Charity> charityList = new ArrayList<Charity>();
		for(int i = 0 ; i < listOfCharityInfo.size() ; i++) {
			HashMap<String, String> charityInfoMap = listOfCharityInfo.get(i);
			String name = charityInfoMap.get(nameKey);
			String address = charityInfoMap.get(addressKey);
			String city = charityInfoMap.get(cityKey);
			String state = charityInfoMap.get(stateKey);
			String postalCode = charityInfoMap.get(postalCodeKey);
			String type = charityInfoMap.get(typeKey);
			String status = charityInfoMap.get(statusKey);
			String classification = charityInfoMap.get(classificationKey);
			Charity c = new Charity(name, address, city, state, postalCode, type, status, classification);
			if(!nullOrExists(c, charityList)) {
				charityList.add(c);
			}
		}
		return charityList;	
	}
	
	// check if no name or if already exists in list, omit if satisfies condition
	private static boolean nullOrExists(Charity c, ArrayList<Charity> cList) {
		if(c.getName() == null) {
			return true;
		}
		for(Charity cS : cList) {
			if(cS.getName().equals(c.getName())) {
				return true;
			}
		}
		return false;
	}
	
	// Generate the list of charities by state
	public static ArrayList<Charity> generateListByState(String state) {
		ArrayList<Charity> charities = new ArrayList<Charity>();
		try {
			String charityJSON = HttpRequest.GET(Routes.stateRoute(state));
			charities = parseCharityJSON(charityJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return charities;
	}
	
	// Generate default list of charities in CA
	public static ArrayList<Charity> generateDefaultCharityList() {
		ArrayList<Charity> charities = new ArrayList<Charity>();
		try {
			String charityJSON = HttpRequest.GET(Routes.defaultStateUrl);
			charities = parseCharityJSON(charityJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return charities;
	}
	
	// Generate state strings
	public static ArrayList<String> getCharityStrings(ArrayList<Charity> charities) {
		ArrayList<String> charityNames = new ArrayList<String>();
		for(Charity c : charities) {
			charityNames.add(c.getName());
		}
		return charityNames;
	}
	
	// Generate charity map
	public static HashMap<String, Charity> getCharityMap(ArrayList<Charity> charities) {
		HashMap<String, Charity> charityMap = new HashMap<String, Charity>();
		for(Charity c : charities) {
			charityMap.put(c.getName(), c);
		}
		return charityMap;
	}
	
	// user's favorite charities to DB
	public static void addArrayListToDB(User user) {
		ArrayList<Charity> charities = user.getFavoriteCharities();
		for(int i = 0 ; i < charities.size() ; i++) {
			String charityRoute = Routes.getRouteWithKeys(user, Routes.charityKey, charities.get(i).getName()); 
			String charityInfo = convertCharityInfoToJSON(charities.get(i));
			try {
				HttpRequest.PATCH(charityRoute, charityInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// convert the charity's information into json object
	public static String convertCharityInfoToJSON(Charity c) {
		StringBuilder json = new StringBuilder("");
		json.append("{");
		json = appendToSB(json, addressKey, c.getAddress()); json.append(",");
		json = appendToSB(json, cityKey, c.getCity()); json.append(",");
		json = appendToSB(json, stateKey, c.getState()); json.append(",");
		json = appendToSB(json, postalCodeKey, c.getPostalCode()); json.append(",");
		json = appendToSB(json, typeKey, c.getType()); json.append(",");
		json = appendToSB(json, statusKey, c.getStatus()); json.append(",");
		json = appendToSB(json, classificationKey, c.getClassification()); 
		json.append("}");
		return json.toString();
	}
	
	// build each json key value pair
	private static StringBuilder appendToSB(StringBuilder sb, String key, String value) {
		sb.append("\"");
		sb.append(key);
		sb.append("\"");
		sb.append(":");
		sb.append("\"");
		sb.append(value);
		sb.append("\"");
		return sb;
	}
	
	// Add charity to database/user's favorite list
	public static void addCharityToList(User user, Charity charity) {
		if(!user.getFavoriteCharities().contains(charity)) {
			user.getFavoriteCharities().add(charity);
			addArrayListToDB(user);
		}
	}
	
}
