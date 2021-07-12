package org.techtown.apt_1025.KActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.techtown.apt_1025.KNetwork.HttpClient;
import org.techtown.apt_1025.KNetwork.HttpInterface;
import org.techtown.apt_1025.KNetwork.SerialApartInfo;
import org.techtown.apt_1025.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

public class RankingActivity extends AppCompatActivity {
    RecyclerView rv_ranking_apart;
    Button rank_back;
    AdapterRecommend adapterRecommend;
    Intent getI;
    ArrayList<SerialApartInfo.ApartRecommend> rankingList;
    private  static final String TAG = DetailInfoActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        getI=getIntent();
        int u_id = getI.getIntExtra("u_id", -1);

        rankingList=new ArrayList<>();
        try {
            rankingList.addAll(new GetTop10().execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        rank_back = findViewById(R.id.rank_back);
        rank_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rv_ranking_apart=findViewById(R.id.rv_ranking_apart);
        rv_ranking_apart.setLayoutManager(new LinearLayoutManager(RankingActivity.this, LinearLayoutManager.VERTICAL, false ));
        adapterRecommend=new AdapterRecommend(getApplicationContext(), rankingList, u_id);
        rv_ranking_apart.setAdapter(adapterRecommend);
        Log.d("size", "Size"+rankingList.size());


    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterRecommend.notifyDataSetChanged();
    }

    @SuppressLint("StaticFieldLeak")
    private class GetTop10 extends AsyncTask<Void, Void, List<SerialApartInfo.ApartRecommend>> {

        @Override
        protected List<SerialApartInfo.ApartRecommend> doInBackground(Void... voids) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<List<SerialApartInfo.ApartRecommend>> call = httpInterface.getTop10();
            List<SerialApartInfo.ApartRecommend> response = new ArrayList<>();
            try {
                response.addAll(call.execute().body());
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            }
            return response;

        }


    }

}
