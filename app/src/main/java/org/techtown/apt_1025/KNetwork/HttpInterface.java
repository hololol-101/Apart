package org.techtown.apt_1025.KNetwork;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HttpInterface {
    @GET("/eun/getAid.php")
    Call<SerialApartInfo> getApartInfo(
            @Query("a_id") int a_id
    );
    @GET("/eun/updateLikes.php")
    Call<Void> changeLikes(
            @Query("a_id")int a_id,
            @Query("likes")int likes
    );
    @DELETE("/eun/delete_store.php")
    Call<Void> deleteStore(
            @Query("a_id") int a_id,
            @Query("u_id") int u_id,
            @Query("total_address")String total_address
    );
    @GET("/eun/InsertStore.php")
    Call<Void> insertStore(
            @Query("a_id") int a_id,
            @Query("u_id") int u_id,
            @Query("total_address") String total_address
    );
    @GET("/eun/IsChecked.php")
    Call<Boolean> isChecked(
            @Query("a_id") int a_id,
            @Query("u_id") int u_id
    );
    @GET("/eun/putMyApart.php")
    Call<Void> putMyApart(
            @Query("u_id") int u_id,
            @Query("total_address") String total_address
    );
    @GET("/eun/getApartList.php")
    Call<List<SerialApartInfo.SearchApartList>> getApartList();
    @GET("/eun/getApartListSpe.php")
    Call<List<SerialApartInfo.SearchApartList>> getApartListSpe(
            @Query("addr") String addr
    );
    @GET("/eun/RankingList.php")
    Call<List<SerialApartInfo.ApartRecommend>> getTop10();
    @GET("/eun/getKorAddress.php")
    Call<List<String>> getSiGuGun();
    @GET("/eun/korDong.php")
    Call<List<String>> getEupMyeonDong(
            @Query("si_gu_gun") String sigugun
    );
    @GET("/eun/allFavorite.php")
    Call<List<SerialApartInfo.ApartRecommend>> getFavorite(
            @Query("u_id") int u_id
    );
    @GET("/eun/search_apart.php")
    Call<List<SerialApartInfo.ApartRecommend>> searchApt(
            @Query("apart_name") String apart_name,
            @Query("minprice") Double minprice,
            @Query("maxprice") Double maxprice,
            @Query("minarea") Double minarea,
            @Query("maxarea") Double maxarea,
            @Query("total_addr") String total_addr,
            @Query("bus")String bus,
            @Query("subway")String subway,
            @Query("nursery")String nursery,
            @Query("bank") String bank,
            @Query("preschool")String preschool,
            @Query("school")String school,
            @Query("parking")String parking,
            @Query("market")String market,
            @Query("convinience_store")String convinience_store,
            @Query("laundry")String laundry,
            @Query("hospital") String hospital

    );
    @GET("select_longlat_by_address.php")
    Call<SerialLatLng> getLatLng(
            @Query("total_address") String total_address
    );

    @GET("/select_longlat.php")
    Call<SerialLatLng> getLatLng(
            @Query("addr_num") Integer addr_num
    );

    @GET("/select_addrnum.php")
    Call<ArrayList<Integer>> getAddrNum(
            @Query("eup_myeon_dong") String eup_myeon_dong
    );

    @GET("/select_address.php")
    Call<SerialTotalAddr> getAddr(
            @Query("addr_num") Integer addr_num
    );

    @GET("/select_aptname.php")
    Call<ArrayList<String>> getAptName(
            @Query("total_address") String total_address
    );

    @GET("/select_pplyear.php")
    Call<Serialpplyear> getpplyear(
            @Query("apart_name") String apart_name
    );

    @GET("/select_area.php")
    Call<List<Double>> getArea(
            @Query("apart_name") String apart_name
    );

    @GET("/select_aid.php")
    Call<SerialAid> getAid(
            @Query("area") String area,
            @Query("apart_name") String apart_name
    );
}