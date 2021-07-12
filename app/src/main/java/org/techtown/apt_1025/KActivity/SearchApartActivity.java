package org.techtown.apt_1025.KActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.techtown.apt_1025.KNetwork.HttpClient;
import org.techtown.apt_1025.KNetwork.HttpInterface;
import org.techtown.apt_1025.KNetwork.SerialApartInfo;
import org.techtown.apt_1025.R;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

public class SearchApartActivity extends AppCompatActivity {

    //
    ProgressDialog dialog;
    //

    EditText apart_name,min_price,max_price,min_area,max_area;
    ListView lv_search_list;
    TextView tvsido, tvsigugun, tveupdong;
    CheckBox bus, subway, nursery, bank, preschool, school, parking, market, convinience_store, laundry, hospital;
    String sbus="null", ssubway="null", snursery="null", sbank="null", spreschool="null", sschool="null", sparking="null", smarket="null", sconvinience_store="null", slaundry="null", shospital="null";
    Button complete;
    String[] sigugun, eupdong, sido;
    Button si_gu_gun_up, si_gu_gun_down, eup_dong_up, eup_dong_down, si_do_up, si_do_down;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    int siguposition=0, dongposition=0, sidoposition=0;
    ArrayList<SerialApartInfo.SearchApartList> total_aparts;
    ArrayList<SerialApartInfo.SearchApartList> aparts;
    AdapterSearchApt adapterSearchApt;
    ArrayList<SerialApartInfo.SearchApartList> resultAparts;
    int u_id;
    Intent getI;
    ArrayList<SerialApartInfo.ApartRecommend> resultApt;
    private  static final String TAG = SearchApartActivity.class.getName();
    double minprice, maxprice, minarea, maxarea;
    String total_addr, apt_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getI=getIntent();
        u_id=getI.getIntExtra("u_id",-1);

        resultApt=new ArrayList<>();
        apart_name=findViewById(R.id.et_search_apart_name);
        min_price=findViewById(R.id.et_search_min_price);
        max_price=findViewById(R.id.ev_search_max_price);
        min_area=findViewById(R.id.et_search_min_area);
        max_area=findViewById(R.id.ev_search_max_area);
        lv_search_list=findViewById(R.id.lv_search_list);
        tvsido=findViewById(R.id.NP_sido);
        tvsigugun=findViewById(R.id.NP_sigugun);
        tveupdong=findViewById(R.id.NP_eupdong);
        bus=findViewById(R.id.cb_search_bus); bus.setOnClickListener(onClickListener);
        subway=findViewById(R.id.cb_search_subway); subway.setOnClickListener(onClickListener);
        nursery=findViewById(R.id.cb_search_nursery); nursery.setOnClickListener(onClickListener);
        bank=findViewById(R.id.cb_search_bank); bank.setOnClickListener(onClickListener);
        preschool=findViewById(R.id.cb_search_preschool);preschool.setOnClickListener(onClickListener);
        school=findViewById(R.id.cb_search_school); school.setOnClickListener(onClickListener);
        parking=findViewById(R.id.cb_search_parking);parking.setOnClickListener(onClickListener);
        market=findViewById(R.id.cb_search_market);market.setOnClickListener(onClickListener);
        convinience_store=findViewById(R.id.cb_search_convinience_store);convinience_store.setOnClickListener(onClickListener);
        laundry=findViewById(R.id.cb_search_laundry);laundry.setOnClickListener(onClickListener);
        hospital=findViewById(R.id.cb_search_hospital);hospital.setOnClickListener(onClickListener);
        complete=findViewById(R.id.btn_search_complete);
        si_gu_gun_up=findViewById(R.id.btn_search_si_gu_gun_up);
        si_gu_gun_down=findViewById(R.id.btn_search_si_gu_gun_down);
        eup_dong_up=findViewById(R.id.btn_search_eup_dong_up);
        eup_dong_down=findViewById(R.id.btn_search_eup_dong_down);
        si_do_up=findViewById(R.id.btn_search_si_do_up);
        si_do_down=findViewById(R.id.btn_search_si_do_down);
        lv_search_list.setVisibility(View.GONE);
        Button backtotototo = (Button)findViewById(R.id.backtotototo);
        backtotototo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        putApartName();
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
            }
            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                //Toast.makeText(getApplicationContext(),newState.name().toString(),Toast.LENGTH_SHORT).show();
                if(newState.name().toString().equalsIgnoreCase("Collapsed")){
                }else if(newState.name().equalsIgnoreCase("Expanded")){
                    // 열렸을때 처리하는 부분
                }
            }
        });

        Button scd = (Button) findViewById(R.id.search_draw);
        scd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        });

        Button btnCloseDrawer = (Button) findViewById(R.id.cls_draw);
        btnCloseDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });



        apart_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(apart_name.getText().toString().equals(""))
                    lv_search_list.setVisibility(View.GONE);
                else
                    lv_search_list.setVisibility(View.VISIBLE);
                String text = apart_name.getText().toString();
                search(text);
            }
        });
        sido=new String[2];
        sido[0]="전체"; sido[1]="대구광역시";
        numP(sido, tvsido, sidoposition);
        try {
            List<String> list= new ArrayList<String>();
            list.addAll(new GetSiGuGun().execute().get());
            sigugun=new String[list.size()];
            listToArray(sigugun, list);
            setClickEvent(0);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        defaultClickEvent();
        si_do_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sidoposition=(sidoposition+2+1)%2;
                defaultClickEvent();
            }
        });
        si_do_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sidoposition=(sidoposition+2-1)%2;
                defaultClickEvent();
            }
        });
        si_gu_gun_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dongposition=0;
                setClickEvent(1);
                setClickEvent2(0);
            }
        });
        si_gu_gun_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dongposition=0;
                setClickEvent(-1);
                setClickEvent2(0);
            }
        });
        eup_dong_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClickEvent2(1);
            }
        });
        eup_dong_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClickEvent2(-1);
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(min_price.getText().toString().equals("")) minprice=(double)-1;
                else{
                    try{
                        minprice=Double.parseDouble(min_price.getText().toString());
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "최소값에는 숫자만 넣어주세요.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if(max_price.getText().toString().equals("")) maxprice=(double)-1;
                else{
                    try{
                        maxprice=Double.parseDouble(max_price.getText().toString());
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "최대값에는 숫자만 넣어주세요.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if(min_area.getText().toString().equals("")) minarea=(double)-1;
                else{
                    try{
                        minarea=Double.parseDouble(min_area.getText().toString());
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "최소값에는 숫자만 넣어주세요.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if(max_area.getText().toString().equals("")) maxarea=(double)-1;
                else{
                    try{
                        maxarea=Double.parseDouble(max_area.getText().toString());
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "최대값에는 숫자만 넣어주세요.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if(tvsido.getText().toString().equals(sido[0])){
                    total_addr = "null";
                }else{
                    total_addr = tvsido.getText().toString()+" "+tvsigugun.getText().toString()+" "+tveupdong.getText().toString();
                }
                if(apart_name.getText().toString().equals("")){
                    apt_name= "null";
                }
                else{
                    apt_name=apart_name.getText().toString();
                }
                if(apt_name=="null"&&total_addr=="null")
                {
                    Toast.makeText(getApplicationContext(), "아파트 명, 지역 중 하나는 입력해야 검색이 가능합니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        resultApt.addAll(new SearchApt().execute().get());
                        Intent intent=new Intent(getApplicationContext(), ApartListActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("resultApt", resultApt);
                        intent.putExtras(bundle);
                        intent.putExtra("u_id", u_id);

                        dialog = new ProgressDialog(SearchApartActivity.this);
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
                        dialog.dismiss();
                        thread.start();
                        /////
                        finish();
                        startActivity(intent);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    void defaultClickEvent(){
        if(sidoposition==0){
            tvsido.setText("전체");
            tvsigugun.setText("");
            tveupdong.setText("");
            siguposition=0;
            dongposition=0;
            aparts.clear();
            aparts.addAll(total_aparts);
            String text = apart_name.getText().toString();
            search(text);
            si_gu_gun_up.setEnabled(false);
            si_gu_gun_down.setEnabled(false);
            eup_dong_down.setEnabled(false);
            eup_dong_up.setEnabled(false);
        }
        else{
            si_gu_gun_up.setEnabled(true);
            si_gu_gun_down.setEnabled(true);
            eup_dong_up.setEnabled(true);
            eup_dong_down.setEnabled(true);
            numP(sido, tvsido, sidoposition);
            setClickEvent(0);
            setClickEvent2(0);
        }

    }
    void setClickEvent(int i){
        List<String>list=new ArrayList<String>();
        siguposition=(siguposition+i+sigugun.length)%sigugun.length;
        dongposition=0;
        try {
            list.addAll(new GetEupDong().execute(sigugun[siguposition]).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        eupdong=new String[list.size()];
        listToArray(eupdong, list);

        numP(sigugun, tvsigugun, siguposition);
        numP(eupdong, tveupdong, dongposition);
        try {
            aparts.addAll(new GetApartListSpe().execute(eupdong[dongposition]).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    void setClickEvent2(int i) {
        List<String>list=new ArrayList<String>();
        dongposition=(dongposition+eupdong.length+i)%eupdong.length;
        try {
            list.addAll(new GetEupDong().execute(sigugun[siguposition]).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        eupdong=new String[list.size()];
        listToArray(eupdong, list);
        numP(eupdong, tveupdong, dongposition);
        String addr = tvsido.getText().toString()+" "+tvsigugun.getText().toString()+" "+tveupdong.getText().toString();

        aparts.clear();
        try {
            aparts.addAll(new GetApartListSpe().execute(addr).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String text = apart_name.getText().toString();
        search(text);
    }
    View.OnClickListener onClickListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CheckBox vb = (CheckBox) v;
            if (vb.isChecked() == true) {
                switch (v.getId()) {
                    case R.id.cb_search_bus:
                        sbus = "버스정류장";
                        break;
                    case R.id.cb_search_subway:
                        ssubway = "지하철역";
                        break;
                    case R.id.cb_search_nursery:
                        snursery = "어린이집";
                        break;
                    case R.id.cb_search_bank:
                        sbank = "은행";
                        break;
                    case R.id.cb_search_preschool:
                        spreschool = "유치원";
                        break;
                    case R.id.cb_search_school:
                        sschool = "학교";
                        break;
                    case R.id.cb_search_parking:
                        sparking = "주차장";
                        break;
                    case R.id.cb_search_market:
                        smarket = "마트";
                        break;
                    case R.id.cb_search_convinience_store:
                        sconvinience_store = "편의점";
                        break;
                    case R.id.cb_search_laundry:
                        slaundry = "세탁소";
                        break;
                    case R.id.cb_search_hospital:
                        shospital = "병원";
                        break;
                }
            }
            else {
                switch (v.getId()) {
                    case R.id.cb_search_bus:
                        sbus = "null";
                        break;
                    case R.id.cb_search_subway:
                        ssubway = "null";
                        break;
                    case R.id.cb_search_nursery:
                        snursery = "null";
                        break;
                    case R.id.cb_search_bank:
                        sbank = "null";
                        break;
                    case R.id.cb_search_preschool:
                        spreschool = "null";
                        break;
                    case R.id.cb_search_school:
                        sschool = "null";
                        break;
                    case R.id.cb_search_parking:
                        sparking = "null";
                        break;
                    case R.id.cb_search_market:
                        smarket = "null";
                        break;
                    case R.id.cb_search_convinience_store:
                        sconvinience_store = "null";
                        break;
                    case R.id.cb_search_laundry:
                        slaundry = "null";
                        break;
                    case R.id.cb_search_hospital:
                        shospital = "null";
                        break;
                }
            }
        }
    };
    void listToArray(String[] strings, List<String> arrayList){

        for(int i=0;i<arrayList.size();i++){
            strings[i]=arrayList.get(i);
        }
    }
    void numP(String[] arrayList,TextView tv, int position){
        tv.setText(arrayList[position]);
    }
    void putApartName(){
        aparts=new ArrayList<SerialApartInfo.SearchApartList>();
        resultAparts=new ArrayList<SerialApartInfo.SearchApartList>();
        total_aparts=new ArrayList<SerialApartInfo.SearchApartList>();
        try {
            total_aparts.addAll(new GetApartList().execute().get());
            aparts.addAll(total_aparts);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        resultAparts.addAll(aparts);
        adapterSearchApt=new AdapterSearchApt(getApplicationContext(), resultAparts );
        lv_search_list.setAdapter(adapterSearchApt);


        lv_search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                apart_name.setText(resultAparts.get(position).apt_name);
            }
        });
    }


    void search(String text){
        resultAparts.clear();
        if (text.length() == 0) {
            resultAparts.addAll(aparts);
        }else {
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < aparts.size(); i++) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (aparts.get(i).apt_name.toLowerCase().contains(text)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    resultAparts.add(aparts.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapterSearchApt.notifyDataSetChanged();
    }
    @SuppressLint("StaticFieldLeak")
    private class GetApartList extends AsyncTask<Void, Void,List<SerialApartInfo.SearchApartList>> {
        @Override
        protected List<SerialApartInfo.SearchApartList> doInBackground(Void... voids) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<List<SerialApartInfo.SearchApartList>> call = httpInterface.getApartList();
            List<SerialApartInfo.SearchApartList> response = null;
            try {
                response=call.execute().body();
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            }
            return response;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetApartListSpe extends AsyncTask<String, Void,List<SerialApartInfo.SearchApartList>> {
        @Override
        protected List<SerialApartInfo.SearchApartList> doInBackground(String... strings) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<List<SerialApartInfo.SearchApartList>> call = httpInterface.getApartListSpe(strings[0]);
            List<SerialApartInfo.SearchApartList> response = new ArrayList<>();
            try {
                response.addAll(call.execute().body());
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            }
            return response;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetSiGuGun extends AsyncTask<Void, Void,List<String>> {
        @Override
        protected List<String> doInBackground(Void... voids) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<List<String>> call = httpInterface.getSiGuGun();
            List<String> response = null;
            try {
                response=call.execute().body();
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            }
            return response;
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class GetEupDong extends AsyncTask<String, Void,List<String>> {
        @Override
        protected List<String> doInBackground(String... strings) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<List<String>> call = httpInterface.getEupMyeonDong(strings[0]);
            List<String> response = null;
            try {
                response=call.execute().body();
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            }
            return response;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class SearchApt extends AsyncTask<String, Void,List<SerialApartInfo.ApartRecommend>> {
        @Override
        protected List<SerialApartInfo.ApartRecommend> doInBackground(String... strings) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);

            Call<List<SerialApartInfo.ApartRecommend>> call = httpInterface.searchApt(apt_name, minprice, maxprice, minarea, maxarea, total_addr, sbus, ssubway, snursery, sbank, spreschool, sschool, sparking, smarket, sconvinience_store, slaundry, shospital);
            List<SerialApartInfo.ApartRecommend> response = null;
            try {
                response=call.execute().body();
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            }
            return response;

        }
    }
}