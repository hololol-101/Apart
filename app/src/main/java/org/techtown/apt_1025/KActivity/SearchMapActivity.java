package org.techtown.apt_1025.KActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;

import org.techtown.apt_1025.KNetwork.HttpClient;
import org.techtown.apt_1025.KNetwork.HttpInterface;
import org.techtown.apt_1025.KNetwork.SerialAid;
import org.techtown.apt_1025.KNetwork.SerialLatLng;
import org.techtown.apt_1025.KNetwork.SerialTotalAddr;
import org.techtown.apt_1025.R;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;


public class SearchMapActivity extends FragmentActivity implements OnMapReadyCallback, Serializable {

    //
    ProgressDialog dialog;
    //

    private static String TAG = "mapAPI";
    private String si_gu_gun, eup_myeon_dong, totalAddr;
    List<String> gulist;
    public SerialLatLng apartInfo;
    private int u_id;

    public static NaverMap naverMap;
    private MapView mapView;
    Button gubtn, dongbtn, searchbtn;
    PopupMenu gumenu, dongmenu;
    private InfoWindow infoWindow = new InfoWindow();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        gubtn = (Button) findViewById(R.id.gubtn);
        dongbtn = (Button) findViewById(R.id.dongbtn);
        searchbtn = (Button) findViewById(R.id.search_button);

        Intent intent = getIntent();
        u_id = intent.getIntExtra("u_id", -1);
        Log.d("u_id", Integer.toString(u_id));

        try {
            gulist=new ArrayList<>();
            gulist.addAll(new GetGuGun().execute().get());

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }

        Button back = (Button) findViewById(R.id.map_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        gumenu = new PopupMenu(this, gubtn, Gravity.NO_GRAVITY, 0, R.style.MyPopupMenu);
        for(int i = 0; i < gulist.size(); i++) {
            gumenu.getMenu().add(0, i, 0, String.valueOf(gulist.get(i)));
        }


        gubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gumenu.show();
                gumenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        gubtn.setText(item.getTitle());
                        si_gu_gun = String.valueOf(item.getTitle());
                        try {
                            List<String> donglist = new ArrayList<>();
                            donglist.addAll(new GetEupMyeonDong().execute(si_gu_gun).get());
                            dongmenu = new PopupMenu(getBaseContext(), dongbtn, Gravity.NO_GRAVITY, 0, R.style.MyPopupMenu);
                            for(int i = 0; i < donglist.size(); i++) {
                                dongmenu.getMenu().add(0, i, 0, String.valueOf(donglist.get(i)));
                            }
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        } catch (ExecutionException ex) {
                            ex.printStackTrace();
                        }
                        return false;
                    }
                });
            }
        });




        dongbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dongmenu.show();
                dongmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        eup_myeon_dong = String.valueOf(item.getTitle());
                        dongbtn.setText(eup_myeon_dong);
                        Log.d("eeee", "num : "+item.getTitle());
                        return false;
                    }
                });
            }
        });


        searchbtn.setOnClickListener(new View.OnClickListener() {
            List<Marker> markerList = new ArrayList<>();
            @Override
            public void onClick(View v) {
                try {

                    /////
                    dialog = new ProgressDialog(SearchMapActivity.this);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setMessage("로딩중");
                    dialog.show();

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            TimerTask task = new TimerTask(){
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                }
                            };

                            Timer timer = new Timer();
                            timer.schedule(task, 1500);
                        }
                    });
                    thread.start();
                    /////

                    String[] aptname;
                    ArrayList<String> total = new ArrayList<>();
                    List<Integer> numlist = new ArrayList<>();
                    numlist.clear();
                    numlist.addAll(new GetAddrNum().execute(eup_myeon_dong).get());
                    List<SerialLatLng> mArrayList = new ArrayList<>();

                    for(int i = 0; i < numlist.size(); i++) {
                        apartInfo = new GetLatLng().execute(numlist.get(i)).get();
                        mArrayList.add(apartInfo);
                        totalAddr = new GetAddr().execute(numlist.get(i)).get().total_address;
                        total.add(totalAddr);
                    }

                    for(int i = 0; i < markerList.size(); i++) {
                        markerList.get(i).setMap(null);

                    }
                    markersPosition = new Vector<LatLng>();
                    aptname = new String[mArrayList.size()];

                    for (int i = 0; i < mArrayList.size(); i++) {
                        String lngi = mArrayList.get(i).longitude;
                        String lati = mArrayList.get(i).latitude;
                        String totaladd = total.get(i);
                        Log.w("eeee", "Lat : " + lati + " Lon : " + lngi + " total : " + totaladd);
                        markersPosition.add(new LatLng(Double.parseDouble(lngi), Double.parseDouble(lati)));
                        aptname[i] = totaladd;
                    }

                    for (int i = 0; i < markersPosition.size(); i++) {
                        final ArrayList<String> apt_name = new GetAptName().execute(aptname[i]).get();
                        final Marker marker = new Marker();
                        marker.setPosition(markersPosition.get(i));
                        marker.setCaptionText(apt_name.get(0)); // 아파트 이름달기
                        marker.setMap(naverMap);
                        markerList.add(marker);
                        marker.setOnClickListener(new Overlay.OnClickListener() {
                            @Override
                            public boolean onClick(@NonNull Overlay overlay) { // 아파트 클릭하면 정보나옴
                                ViewGroup rootView = (ViewGroup) findViewById(R.id.fragment_container);
                                PointAdapter adapter = new PointAdapter(SearchMapActivity.this, apt_name.get(0), rootView);
                                infoWindow.setAdapter(adapter);
                                infoWindow.open(marker);
                                infoWindow.setOnClickListener(new Overlay.OnClickListener() {
                                    @Override
                                    public boolean onClick(@NonNull Overlay overlay) {
                                        final List<Double> areas = new ArrayList<>();
                                        try {
                                            areas.addAll(new GetArea().execute(apt_name.get(0)).get());
                                            String[] age = new String[areas.size()];
                                            for(int i = 0; i < areas.size(); i++) {
                                                age[i] = apt_name.get(0)+"("+ Double.toString(areas.get(i))+"m2)";
                                            }
                                            AlertDialog.Builder dlg = new AlertDialog.Builder(SearchMapActivity.this);
                                            dlg.setItems(age, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    int a_id = 0;
                                                    try {
                                                        a_id = new GetAid().execute(Double.toString(areas.get(which)), apt_name.get(0)).get().a_id;
                                                        Intent intent = new Intent(SearchMapActivity.this, DetailInfoActivity.class);
                                                        intent.putExtra("a_id", a_id);
                                                        intent.putExtra("u_id", u_id);
                                                        startActivity(intent);
                                                    } catch (ExecutionException e) {
                                                        e.printStackTrace();
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            });
                                            dlg.show();
                                        } catch (ExecutionException e) {
                                            e.printStackTrace();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        return false;
                                    }
                                });
                                return false;
                            }
                        });
                    }
                    LatLng initialPosition = new LatLng(Double.parseDouble(mArrayList.get(0).longitude),Double.parseDouble(mArrayList.get(0).latitude));
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(initialPosition);
                    naverMap.moveCamera(cameraUpdate);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });



        mapView = (MapView) findViewById(R.id.map_view);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    private Vector<LatLng> markersPosition;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onMapReady(@NonNull final NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setMapType(NaverMap.MapType.Basic);
        naverMap.setLayerGroupEnabled(naverMap.LAYER_GROUP_BUILDING, true);
        LatLng initialPosition = new LatLng(35.871408,  128.601746);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(initialPosition);
        naverMap.moveCamera(cameraUpdate);
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
    private class GetLatLng extends AsyncTask<Integer, Void, SerialLatLng> {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected SerialLatLng doInBackground(Integer... integers) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<SerialLatLng> call = httpInterface.getLatLng(integers[0]);
            SerialLatLng response = null;
            try {
                response = call.execute().body();
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            }
            return response;

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetEupMyeonDong extends AsyncTask<String, Void, List<String>> {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected List<String> doInBackground(String... strings) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<List<String>> call = httpInterface.getEupMyeonDong(strings[0]);
            List<String> response = null;
            try {
                response = call.execute().body();
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            }
            return response;

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetGuGun extends AsyncTask<String, Void, List<String>> {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected List<String> doInBackground(String... strings) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<List<String>> call = httpInterface.getSiGuGun();
            List<String> response = null;
            try {
                response = call.execute().body();
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            }
            return response;

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetAddrNum extends AsyncTask<String, Void, ArrayList<Integer>> {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected ArrayList<Integer> doInBackground(String... strings) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<ArrayList<Integer>> call = httpInterface.getAddrNum(strings[0]);
            ArrayList<Integer> response = null;
            try {
                response = call.execute().body();
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            }
            return response;

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetAddr extends AsyncTask<Integer, Void, SerialTotalAddr> {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected SerialTotalAddr doInBackground(Integer... integers) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<SerialTotalAddr> call = httpInterface.getAddr(integers[0]);
            SerialTotalAddr response = null;
            try {
                response = call.execute().body();
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            }
            return response;

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetAptName extends AsyncTask<String, Void, ArrayList<String>> {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<ArrayList<String>> call = httpInterface.getAptName(strings[0]);
            ArrayList<String> response = null;
            try {
                response = call.execute().body();
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            }
            return response;

        }
    }
    @SuppressLint("StaticFieldLeak")
    private class GetArea extends AsyncTask<String, Void, List<Double>> {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected List<Double> doInBackground(String... strings) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<List<Double>> call = httpInterface.getArea(strings[0]);
            List<Double> response = null;
            try {
                response = call.execute().body();
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            }
            return response;

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetAid extends AsyncTask<String, Void, SerialAid> {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected SerialAid doInBackground(String... strings) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<SerialAid> call = httpInterface.getAid(strings[0], strings[1]);
            SerialAid response = null;
            try {
                response = call.execute().body();
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            }
            return response;

        }
    }
}