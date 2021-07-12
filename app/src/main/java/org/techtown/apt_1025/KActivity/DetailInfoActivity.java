package org.techtown.apt_1025.KActivity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.apt_1025.KNetwork.HttpClient;
import org.techtown.apt_1025.KNetwork.HttpInterface;
import org.techtown.apt_1025.KNetwork.SerialApartInfo;
import org.techtown.apt_1025.KNetwork.SerialLatLng;
import org.techtown.apt_1025.MainActivity;
import org.techtown.apt_1025.MyApart.AreaActivity;
import org.techtown.apt_1025.MyApart.PredictActivity;
import org.techtown.apt_1025.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

public class DetailInfoActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView tv_detail_apart_name, tv_detail_location, tv_detail_built_year, tv_detail_area, tv_detail_scale,btn_detail_favorite_text, apart_name_2;
    ToggleButton btn_detail_favorite;
    Button btn_detail_analysis, my_apt_back;
    RecyclerView rv_detail_deal_price, rv_recommand_apart, rv_detail_facility;
    int store;
    ArrayList<SerialApartInfo.ApartRecommend> recommendArrayList;
    ArrayList<SerialApartInfo.ApartFacility> facilityArraylist;
    ArrayList<SerialApartInfo.ApartPrice> priceArrayList;
    AdapterFacility adapterFacility;
    AdapterRecommend adapterRecommend;
    AdapterDealPrice adapterDealPrice;
    private int a_id;
    private int u_id;
    private boolean ischecked;
    private String  apt_name, apt_area;
    Intent intent;
    private  static final String TAG = DetailInfoActivity.class.getName();
    public static NaverMap naverMap;
    private MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        tv_detail_apart_name=findViewById(R.id.tv_detail_apart_name);
        apart_name_2 = findViewById(R.id.apart_name_2);
        tv_detail_location=findViewById(R.id.tv_detail_location);
        tv_detail_built_year=findViewById(R.id.tv_detail_built_year);
        tv_detail_area=findViewById(R.id.tv_detail_area);
        tv_detail_scale=findViewById(R.id.tv_detail_scale);
        btn_detail_favorite=findViewById(R.id.btn_detail_favorite);
        btn_detail_analysis=findViewById(R.id.btn_detail_analysis);
        btn_detail_favorite_text=findViewById(R.id.btn_detail_favorite_text);
        rv_detail_deal_price=findViewById(R.id.rv_detail_deal_price);
        rv_recommand_apart=findViewById(R.id.rv_recommand_apart);
        rv_detail_facility=findViewById(R.id.rv_detail_facility);
        my_apt_back = findViewById(R.id.my_apt_back);

        rv_recommand_apart.setLayoutManager(new LinearLayoutManager(DetailInfoActivity.this, LinearLayoutManager.VERTICAL, false ));
        rv_detail_facility.setLayoutManager(new LinearLayoutManager(DetailInfoActivity.this, LinearLayoutManager.VERTICAL, false ));
        rv_detail_deal_price.setLayoutManager(new LinearLayoutManager(DetailInfoActivity.this, LinearLayoutManager.VERTICAL, false ));
        recommendArrayList=new ArrayList<>();
        facilityArraylist= new ArrayList<>();
        priceArrayList=new ArrayList<>();
        intent=getIntent();
        try{
            a_id= intent.getIntExtra("a_id", -1);
            u_id=intent.getIntExtra("u_id", -1);
        }
        catch (Exception e){
            a_id=-1;
            u_id=-1;
        }

        new GetApartInfo().execute(a_id);
        Log.d("area", "area="+apt_area);
        try {
            ischecked = new IsChecked().execute(a_id, u_id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        btn_detail_favorite.setChecked(ischecked);

        adapterDealPrice=new AdapterDealPrice(getApplicationContext(), priceArrayList); rv_detail_deal_price.setAdapter(adapterDealPrice);
        adapterRecommend=new AdapterRecommend(getApplicationContext(), recommendArrayList, u_id); rv_recommand_apart.setAdapter(adapterRecommend);
        adapterFacility=new AdapterFacility(getApplicationContext(),facilityArraylist); rv_detail_facility.setAdapter(adapterFacility);

        Toast added = Toast.makeText( getApplicationContext(), "관심아파트에 추가되었습니다.", Toast.LENGTH_SHORT );
        Toast removed = Toast.makeText( getApplicationContext(), "관심아파트가 제거되었습니다.", Toast.LENGTH_SHORT );

        my_apt_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });


        btn_detail_analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();

            }
        });
        btn_detail_favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    store++;
                    removed.cancel();
                    added.show();
                    new ChangeLikes().execute(String.valueOf(a_id),String.valueOf(store));
                    new InsertStore().execute(String.valueOf(a_id),String.valueOf( u_id), tv_detail_location.getText().toString());

                }
                else{
                    store--;
                    added.cancel();
                    removed.show();
                    new ChangeLikes().execute(String.valueOf(a_id),String.valueOf(store));
                    new DeleteStore().execute(String.valueOf(a_id),String.valueOf( u_id), tv_detail_location.getText().toString());
                }
                btn_detail_favorite_text.setText(Integer.toString(store));
                btn_detail_favorite_text.bringToFront();
            }
        });
        mapView = (MapView) findViewById(R.id.map_view);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync( this);
    }

    @SuppressLint("StaticFieldLeak")
    private class GetApartInfo extends AsyncTask<Integer, Void, SerialApartInfo> {

        @Override
        protected SerialApartInfo doInBackground(Integer... integers) {

            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<SerialApartInfo> call = httpInterface.getApartInfo(integers[0]);
            SerialApartInfo response = null;
            try {
                response = call.execute().body();
            } catch (Exception e) {

            }
            return response;

        }

        @Override
        protected void onPostExecute(SerialApartInfo serialApartInfo) {
            apt_area=String.valueOf(serialApartInfo.area);
            apt_name=serialApartInfo.apart_name;
            store=serialApartInfo.store;
            if(apt_name.length()>9)
                tv_detail_apart_name.setTextSize(22);
            else if(apt_name.length()>13)
                tv_detail_apart_name.setTextSize(18);
            else
                tv_detail_apart_name.setTextSize(26);
            tv_detail_apart_name.setText(serialApartInfo.apart_name);
            apart_name_2.setText("   "+serialApartInfo.apart_name);
            tv_detail_location.setText(serialApartInfo.total_address);
            tv_detail_area.setText(String.valueOf(serialApartInfo.area)+" m2");
            String scale = serialApartInfo.scale;
            if(serialApartInfo.scale.equals("0"))
                tv_detail_scale.setText("알 수 없음");
            else
                tv_detail_scale.setText(serialApartInfo.scale.split("\n")[1]);
            tv_detail_built_year.setText(serialApartInfo.year_bulit);
            btn_detail_favorite_text.setText(Integer.toString(store));
            btn_detail_favorite_text.bringToFront();
            facilityArraylist.addAll(serialApartInfo.apart_facility);
            priceArrayList.addAll(serialApartInfo.apart_price);
            recommendArrayList.addAll(serialApartInfo.apart_recommend);
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onMapReady(@NonNull final NaverMap naverMap) {

        SerialLatLng serialLatLng = null;

        try {
            serialLatLng = new GetLatLng().execute(tv_detail_location.getText().toString()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String latitude = serialLatLng.longitude;
        String longitude = serialLatLng.latitude;

        this.naverMap = naverMap;
        naverMap.setMapType(NaverMap.MapType.Basic);
        naverMap.setLayerGroupEnabled(naverMap.LAYER_GROUP_BUILDING, true);


        LatLng initialPosition = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        CameraPosition cameraPosition = new CameraPosition(initialPosition, 17, 0, 0);
        naverMap.setCameraPosition(cameraPosition);
        Marker marker = new Marker();
        marker.setPosition(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)));
        marker.setMap(naverMap);
        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mapView.onResume();
        adapterRecommend.notifyDataSetChanged();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @SuppressLint("StaticFieldLeak")
    private class GetLatLng extends AsyncTask<String, Void, SerialLatLng> {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected SerialLatLng doInBackground(String... strings) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<SerialLatLng> call = httpInterface.getLatLng(strings[0]);
            SerialLatLng response = null;
            try {
                response = call.execute().body();
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            }
            return response;

        }
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> { // 시세분석
        String target;

        @Override
        protected void onPreExecute() {
            try {
                // 해당 페이지 아파트의 total_address를 넘겨준다
                //target = "http://3.35.217.139/area_select_bm.php?total_address=" + URLEncoder.encode(tv_detail_location.getText().toString(), "UTF-8");
                target = "http://3.35.217.139/area_select2_bb.php?a_id=" + URLEncoder.encode(String.valueOf(a_id), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result) {
            Intent intent2 = new Intent(DetailInfoActivity.this, PredictActivity.class);
            intent2.putExtra("userList", result);
            DetailInfoActivity.this.startActivity(intent2);
        }
    }
}

