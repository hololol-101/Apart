package org.techtown.apt_1025.KNetwork;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SerialLatLng implements Serializable {
    @SerializedName("longitude")
    public String longitude;
    @SerializedName("latitude")
    public String latitude;
}