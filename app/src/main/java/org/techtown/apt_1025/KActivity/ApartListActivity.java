package org.techtown.apt_1025.KActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.techtown.apt_1025.KNetwork.SerialApartInfo;
import org.techtown.apt_1025.MainActivity;
import org.techtown.apt_1025.MainScreen.MainScreenClass;
import org.techtown.apt_1025.R;

import java.util.ArrayList;

public class ApartListActivity extends AppCompatActivity {
    int u_id;
    RecyclerView rv_apart_list;
    Button add_moreButton,search_back_2;
    AdapterRecommend adapterRecommend;
    ArrayList<SerialApartInfo.ApartRecommend> apartlist;
    ArrayList<SerialApartInfo.ApartRecommend> samplelist;
    boolean full=false;
    int cnt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apart_list);
        apartlist=new ArrayList<>();
        Intent getI = getIntent();
        u_id=getI.getIntExtra("u_id", -1);

        apartlist.clear();
        apartlist.addAll((ArrayList<SerialApartInfo.ApartRecommend>) getI.getSerializableExtra("resultApt"));
        rv_apart_list=findViewById(R.id.rv_apart_list);
        Log.d("al", "alsize = "+ apartlist.size());
        add_moreButton=findViewById(R.id.apart_add_moreButton);
        samplelist=new ArrayList<>();
        if(apartlist.size()<10){
            samplelist.addAll(apartlist);
            full=true;
        }
        else{
            for(cnt=0;cnt<10;cnt++){
                samplelist.add(apartlist.get(cnt));
            }
        }
        Log.d("al","samplesize"+samplelist.size());
        rv_apart_list.setLayoutManager(new LinearLayoutManager(ApartListActivity.this, LinearLayoutManager.VERTICAL, false ));

        adapterRecommend=new AdapterRecommend(getApplicationContext(), samplelist, u_id);
        rv_apart_list.setAdapter(adapterRecommend);
        search_back_2 = findViewById(R.id.search_back_2);
        search_back_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        add_moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(full==true) Toast.makeText(getApplicationContext(), "검색된 아파트가 더이상 없습니다.", Toast.LENGTH_SHORT).show();
                else{
                    if(apartlist.size()>samplelist.size()+10){
                        for(int i=cnt;cnt<i+10;cnt++){
                            samplelist.add(apartlist.get(i));
                            adapterRecommend.notifyItemInserted(i);
                        }
                    }
                    else {
                        for(;cnt<apartlist.size();cnt++){
                            samplelist.add(apartlist.get(cnt));
                            adapterRecommend.notifyItemInserted(cnt);
                        }
                        full=true;
                    }
                }
                adapterRecommend.notifyDataSetChanged();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        adapterRecommend.notifyDataSetChanged();
    }
}
