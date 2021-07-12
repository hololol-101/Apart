package org.techtown.apt_1025.MyApart;

public class Item {
    String a_id;
    String total_address;
    String addr_id;
    String area;
    String scale;
    String store;
    String year_built;
    String apart_name;

    public Item(String a_id, String total_address, String addr_id, String area, String scale, String store, String year_built, String apart_name) {
        this.a_id = a_id;
        this.total_address = total_address;
        this.addr_id = addr_id;
        this.area = area;
        this.scale = scale;
        this.store = store;
        this.year_built = year_built;
        this.apart_name = apart_name;
    }

    public String getA_id() {
        return a_id;
    }

    public void setA_id(String a_id) {
        this.a_id = a_id;
    }

    public String getTotal_address() {
        return total_address;
    }

    public void setTotal_address(String total_address) {
        this.total_address = total_address;
    }

    public String getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(String addr_id) {
        this.addr_id = addr_id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getYear_built() {
        return year_built;
    }

    public void setYear_built(String year_built) {
        this.year_built = year_built;
    }

    public String getApart_name() {
        return apart_name;
    }

    public void setApart_name(String apart_name) {
        this.apart_name = apart_name;
    }
}
