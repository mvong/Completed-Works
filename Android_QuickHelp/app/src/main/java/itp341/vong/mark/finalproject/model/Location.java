package itp341.vong.mark.finalproject.model;

import java.io.Serializable;

/**
 * Created by Mark on 5/7/17.
 */

public class Location implements Serializable{

    private String housenumber;
    private String streetname;
    private String city;
    private String state;

    public String getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    public String getStreetname() {
        return streetname;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Location(String housenumber, String streetname, String city, String state) {

        this.housenumber = housenumber;
        this.streetname = streetname;
        this.city = city;
        this.state = state;
    }

    @Override
    public String toString() {
        return housenumber + " "
                + streetname + ", "
                + city + ", "
                + state + " ";
    }

    public String toJSONString() {
        streetname = streetname.replaceAll(" ", "");
        return housenumber + "+" + streetname +",+"+city+",+"+state;
    }
}
