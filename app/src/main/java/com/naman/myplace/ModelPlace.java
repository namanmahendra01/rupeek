package com.naman.myplace;

import org.json.JSONException;
import org.json.JSONObject;

public class ModelPlace {

    String name,image,id,address,latitude,longitude;

    public ModelPlace(String name, String image, String id, String address, String latitude, String longitude) {
        this.name = name;
        this.image = image;
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public static ModelPlace fromJson(JSONObject jsonObject) {
        ModelPlace b = new ModelPlace();
        try {
            b.name = jsonObject.getString("name");
            b.address = jsonObject.getString("address");
            b.latitude = jsonObject.getString("latitude");
            b.longitude=jsonObject.getString("longitude");
            b.image=jsonObject.getString("image");
            b.id=jsonObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return b;
    }
    public ModelPlace(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "ModelPlace{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
