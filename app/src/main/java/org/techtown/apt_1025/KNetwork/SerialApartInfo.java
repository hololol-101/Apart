package org.techtown.apt_1025.KNetwork;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SerialApartInfo {
    @SerializedName("a_id")
    public int a_id;
    @SerializedName("total_address")
    public String total_address;
    @SerializedName("addr_id")
    public int addr_id;
    @SerializedName("area")
    public double area;
    @SerializedName("scale")
    public String scale;
    @SerializedName("store")
    public int store;
    @SerializedName("year_built")
    public String year_bulit;
    @SerializedName("apart_name")
    public String apart_name;
    @SerializedName("longitude")
    public String longitude;



    @SerializedName("apart_facility")
    public List<ApartFacility> apart_facility=new ArrayList<>();
    @SerializedName("apart_price")
    public List<SerialApartInfo.ApartPrice> apart_price=new ArrayList<>();
    @SerializedName("apart_recommend")
    public List<SerialApartInfo.ApartRecommend> apart_recommend=new ArrayList<>();
    @SerializedName("search_apt_list")
    public List<SerialApartInfo.SearchApartList> search_apt_list=new ArrayList<>();

    public class ApartFacility implements Serializable {
        @SerializedName("facility")
        public String facility;
        @SerializedName("distance")
        public String distance;
    }
    public class ApartPrice implements Serializable {
        @SerializedName("price")
        public double price;
        @SerializedName("upload_day")
        public String upload_day;
    }

    public class ApartRecommend implements Serializable{
        @SerializedName("a_id")
        public int a_id;
        @SerializedName("apt_name")
        public String apt_name;
        @SerializedName("apt_location")
        public String apt_location;
        @SerializedName("builtyear")
        public String builtyear;
        @SerializedName("lastPrice")
        public double lastPrice;
        @SerializedName("area")
        public  String area;
        @SerializedName("store")
        public  int store;
        @SerializedName("scale")
        public String scale;

    }
    public class SearchApartList implements Serializable{
        @SerializedName("apt_name")
        public String apt_name;
        @SerializedName("apt_location")
        public String apt_location;
    }
}
