package org.techtown.apt_1025.MainScreen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.apt_1025.KActivity.RankingActivity;
import org.techtown.apt_1025.MyApart.MyApartActivity;
import org.techtown.apt_1025.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MenuActivity extends AppCompatActivity {
    private int u_id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent getI = getIntent();
        u_id = getI.getIntExtra("u_id", -1);

        Button mypage_button = (Button) findViewById(R.id.my_page_button);
        mypage_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                startActivity(intent);
            }
        });
        Button myapt_button = (Button) findViewById(R.id.my_apt_button);
        myapt_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });
        Button ranking_button=findViewById(R.id.ranking_button);
        ranking_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RankingActivity.class);
                intent.putExtra("u_id", u_id);
                startActivity(intent);
            }
        });
    }
    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                // 로그인시 받아온 사용자의 u_id를 넘겨준다
                // 지금은 임시로 u_id가 6인 사용자
                // 관심아파트 등록 경로 : 메인->관심아파트->상세정보(관심아파트 등록 되어있음), 메인->검색->상세정보(관심아파트 등록 되어있을수도 있고 안되어있을수도 있음)
                target = "http://3.35.217.139/my_select1_bm.php?u_id=" + URLEncoder.encode(String.valueOf(u_id), "UTF-8");
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
            Intent intent = new Intent(MenuActivity.this, MyApartActivity.class);
            intent.putExtra("apartList", result);
            intent.putExtra("u_id", u_id);
            MenuActivity.this.startActivity(intent);
        }
    }
}