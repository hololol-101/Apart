package org.techtown.apt_1025.MainScreen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.apt_1025.KActivity.RankingActivity;
import org.techtown.apt_1025.KActivity.SearchApartActivity;
import org.techtown.apt_1025.KActivity.SearchMapActivity;
import org.techtown.apt_1025.Login.FindModifyActivity;
import org.techtown.apt_1025.MainActivity;
import org.techtown.apt_1025.MyApart.MyApartActivity;
import org.techtown.apt_1025.R;


import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class MainScreenClass extends AppCompatActivity {

    private static String IP_ADDRESS = "3.35.217.139";
    private static String TAG = "phptest";

    private ArrayList<PersonalData> mArrayList;
    private UsersAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private String mJsonString;
    private TextView main_id, main_email;

    private int u_id;
    private String my_email, my_id, my_pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        final View drawerView = (View) findViewById(R.id.drawer);

        Intent intent = getIntent();
        u_id = intent.getIntExtra("u_id", -1);
        my_id = intent.getStringExtra("mem_id");
        my_pw = intent.getStringExtra("mem_pw");
        my_email = intent.getStringExtra("email");
        main_id = findViewById(R.id.main_id);
        main_email = findViewById(R.id.main_email);
        main_id.setText(my_id);
        main_email.setText(my_email);

        mRecyclerView = (RecyclerView)findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageButton btnCloseDrawer = (ImageButton) findViewById(R.id.btn_CloseDrawer);
        btnCloseDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
            }
        });

        // 기본 구분선 추가
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(mRecyclerView.getContext(),new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        RecyclerDecoration spaceDecoration = new RecyclerDecoration(20);
        mRecyclerView.addItemDecoration(spaceDecoration);


        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PersonalData pers = mArrayList.get(position);
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pers.getMember_url()));
                startActivity(myIntent);
                /*PersonalData pers = mArrayList.get(position);
                Intent intent = new Intent(getBaseContext(), ResultActivity.class);
                intent.putExtra("url", pers.getMember_url());
                startActivity(intent);*/
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));


        mArrayList = new ArrayList<>();

        mAdapter = new UsersAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/getnews_jhy.php", "");

        Button my_page = (Button) findViewById(R.id.my_page);
        my_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainScreenClass.this, MypageActivity.class);
                intent.putExtra("u_id",u_id);
                intent.putExtra( "mem_id", my_id );
                intent.putExtra( "mem_pw", my_pw );
                intent.putExtra( "email",my_email );
                startActivity( intent );
            }
        });

        Button menu_button = (Button) findViewById(R.id.menu_button);
        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        Button my_apt = (Button) findViewById(R.id.my_apt);
        my_apt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new MainScreenClass.BackgroundTask().execute();
            }
        });
        Button apt_rank = (Button) findViewById(R.id.apt_rank);
        apt_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RankingActivity.class);
                intent.putExtra("u_id", u_id);
                startActivity(intent);
            }
        });

        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button search_map_button = (Button) findViewById(R.id.search_map_button);
        search_map_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchMapActivity.class);
                intent.putExtra("u_id", u_id);
                startActivity(intent);
            }
        });

        Button search_filter_button = (Button) findViewById(R.id.search_filter_button);
        search_filter_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchApartActivity.class);
                intent.putExtra("u_id", u_id);

                startActivity(intent);
            }
        });


    }
    private long backKeyPressedTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "한번 더 뒤로가기 버튼을 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
        }
    }


    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainScreenClass.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

                // mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();

            }

        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){

        String TAG_JSON="webnautes";
        String TAG_NEWS_TITLE = "news_title";
        String TAG_NEWS_URL = "url";




        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String news_title = item.getString(TAG_NEWS_TITLE);
                int len=0;
                for(int j = 0; j<news_title.length();j++)
                {
                    if(((news_title.charAt(j)>31)&&(news_title.charAt(j)<48))||((news_title.charAt(j)>91)&&(news_title.charAt(j)<127)))
                        len++;
                    else if(((news_title.charAt(j)>47)&&(news_title.charAt(j)<58))||((news_title.charAt(j)>96)&&(news_title.charAt(j)<123)))
                        len+=2;
                    else
                        len+=3;
                    if(len>70) {
                        news_title = news_title.substring(0, j) + "···";
                        break;
                    }
                }
                String news_url = item.getString(TAG_NEWS_URL);


                PersonalData personalData = new PersonalData();

                personalData.setMember_news_title(news_title);
                personalData.setMember_url(news_url);


                mArrayList.add(personalData);
                mAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MainScreenClass.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainScreenClass.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
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
            Intent intent = new Intent(MainScreenClass.this, MyApartActivity.class);
            intent.putExtra("apartList", result);
            intent.putExtra("u_id", u_id);
            MainScreenClass.this.startActivity(intent);
        }
    }



}


