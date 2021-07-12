package org.techtown.apt_1025.MyApart;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.apt_1025.KActivity.ChangeLikes;
import org.techtown.apt_1025.KActivity.DeleteStore;
import org.techtown.apt_1025.KNetwork.HttpClient;
import org.techtown.apt_1025.KNetwork.HttpInterface;
import org.techtown.apt_1025.KNetwork.SerialApartInfo;
import org.techtown.apt_1025.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

public class MyApartActivity extends AppCompatActivity {
    private RecyclerView rv_myapart;
    private MyApartAdapter adapter;
    private ArrayList<SerialApartInfo.ApartRecommend> apartList;
    private int u_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myapart);
        Intent intent = getIntent();
        u_id=intent.getIntExtra("u_id", -1);
        apartList = new ArrayList<SerialApartInfo.ApartRecommend>();
        try {
            apartList.addAll(new GetFavorite().execute(u_id).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Button backtototo = (Button)findViewById(R.id.backtototo);
        backtototo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rv_myapart = findViewById(R.id.rc_myapart);
        Button apartComparison = (Button)findViewById(R.id.apartComparison);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rv_myapart.setLayoutManager(linearLayoutManager);
        adapter = new MyApartAdapter(getApplicationContext(), apartList,  u_id);
        rv_myapart.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyApartAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                int aid = apartList.get(position).a_id;
                String  ta = apartList.get(position).apt_location;
                int st = apartList.get(position).store -1;
                new ChangeLikes().execute(String.valueOf(aid), String.valueOf(st));
                new DeleteStore().execute(String.valueOf(aid), String.valueOf(u_id), ta);
                apartList.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });

        apartComparison.setOnClickListener(new View.OnClickListener() { // 관심아파트 비교 버튼 클릭시
            @Override
            public void onClick(View view) {
               new MyApartActivity.BackgroundTask().execute();
            }
        });

        //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("StaticFieldLeak")
    class GetFavorite extends AsyncTask<Integer, Void, List<SerialApartInfo.ApartRecommend>> {

        @Override
        protected List<SerialApartInfo.ApartRecommend> doInBackground(Integer... integers) {
            HttpInterface httpInterface = HttpClient.getClient().create(HttpInterface.class);
            Call<List<SerialApartInfo.ApartRecommend>> call = httpInterface.getFavorite(integers[0]);
            List<SerialApartInfo.ApartRecommend> response = new ArrayList<>();
            try {
                response.addAll(call.execute().body());
            } catch (Exception e) {
                Log.d("Failed", "YOU ARE FIRED!!");
            }

            return response;

        }

    }
    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                // 로그인시 받아온 사용자의 u_id를 넘겨준다
                // 지금은 임시로 u_id가 6인 사용자
                target = "http://3.35.217.139/select_myapart.php?u_id=" + URLEncoder.encode(String.valueOf(u_id), "UTF-8");
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
        public void onProgressUpdate(Void... values) { // 사용X
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result) {
            Intent intent2 = new Intent(MyApartActivity.this, CheckBoxActivity.class);
            intent2.putExtra("apartList", result);
            MyApartActivity.this.startActivity(intent2);
        }
    }
}