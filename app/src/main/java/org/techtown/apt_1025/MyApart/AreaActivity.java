package org.techtown.apt_1025.MyApart;

import android.content.Intent;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;
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

public class AreaActivity extends AppCompatActivity {
    ArrayList<HashMap<String, String>> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
        layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount= 1f;
        getWindow().setAttributes(layoutParams);
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = (int) (dm.widthPixels * 0.5); // Display 사이즈의 90%
        int height = (int) (dm.heightPixels * 0.3); // Display 사이즈의 90%
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;
        setContentView(R.layout.activity_area);

        TextView textt1 = (TextView)findViewById(R.id.textt1);
        TextView textt2 = (TextView)findViewById(R.id.textt2);
        TextView textt3 = (TextView)findViewById(R.id.textt3);
        TextView textt4 = (TextView)findViewById(R.id.textt4);
        TextView textt5 = (TextView)findViewById(R.id.textt5);
        TextView textt6 = (TextView)findViewById(R.id.textt6);
        TextView textt7 = (TextView)findViewById(R.id.textt7);
        TextView textt8 = (TextView)findViewById(R.id.textt8);
        TextView textt9 = (TextView)findViewById(R.id.textt9);
        TextView textt10 = (TextView)findViewById(R.id.textt10);



        Button Button1 = (Button)findViewById(R.id.Button1);
        Button Button2 = (Button)findViewById(R.id.Button2);
        Button Button3 = (Button)findViewById(R.id.Button3);
        Button Button4 = (Button)findViewById(R.id.Button4);
        Button Button5 = (Button)findViewById(R.id.Button5);
        Button Button6 = (Button)findViewById(R.id.Button6);
        Button Button7 = (Button)findViewById(R.id.Button7);
        Button Button8 = (Button)findViewById(R.id.Button8);
        Button Button9 = (Button)findViewById(R.id.Button9);
        Button Button10 = (Button)findViewById(R.id.Button10);
        ImageButton backto = (ImageButton)findViewById(R.id.backto);
        TextView tt = (TextView)findViewById(R.id.tt);
        Intent intent = getIntent();
        userList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String a_id = item.getString("a_id");
                String total_address = item.getString("total_address");
                String area = item.getString("area");
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("a_id", a_id);
                hashMap.put("total_address", total_address);
                hashMap.put("area", area);
                userList.add(hashMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(userList.size() == 1) {
            Button1.setText(userList.get(0).get("area"));
            Button2.setVisibility(View.GONE);
            Button3.setVisibility(View.GONE);
            Button4.setVisibility(View.GONE);
            Button5.setVisibility(View.GONE);
            Button6.setVisibility(View.GONE);
            Button7.setVisibility(View.GONE);
            Button8.setVisibility(View.GONE);
            Button9.setVisibility(View.GONE);
            Button10.setVisibility(View.GONE);

            textt10.setVisibility(View.GONE);
            textt2.setVisibility(View.GONE);
            textt3.setVisibility(View.GONE);
            textt4.setVisibility(View.GONE);
            textt5.setVisibility(View.GONE);
            textt6.setVisibility(View.GONE);
            textt7.setVisibility(View.GONE);
            textt8.setVisibility(View.GONE);
            textt9.setVisibility(View.GONE);
        }
        if(userList.size() == 2) {
            Button1.setText(userList.get(0).get("area"));
            Button2.setText(userList.get(1).get("area"));
            Button3.setVisibility(View.GONE);
            Button4.setVisibility(View.GONE);
            Button5.setVisibility(View.GONE);
            Button6.setVisibility(View.GONE);
            Button7.setVisibility(View.GONE);
            Button8.setVisibility(View.GONE);
            Button9.setVisibility(View.GONE);
            Button10.setVisibility(View.GONE);

            textt3.setVisibility(View.GONE);
            textt4.setVisibility(View.GONE);
            textt5.setVisibility(View.GONE);
            textt6.setVisibility(View.GONE);
            textt7.setVisibility(View.GONE);
            textt8.setVisibility(View.GONE);
            textt9.setVisibility(View.GONE);
            textt10.setVisibility(View.GONE);

        }
        if(userList.size() == 3) {
            Button1.setText(userList.get(0).get("area"));
            Button2.setText(userList.get(1).get("area"));
            Button3.setText(userList.get(2).get("area"));
            Button4.setVisibility(View.GONE);
            Button5.setVisibility(View.GONE);
            Button6.setVisibility(View.GONE);
            Button7.setVisibility(View.GONE);
            Button8.setVisibility(View.GONE);
            Button9.setVisibility(View.GONE);
            Button10.setVisibility(View.GONE);

            textt4.setVisibility(View.GONE);
            textt5.setVisibility(View.GONE);
            textt6.setVisibility(View.GONE);
            textt7.setVisibility(View.GONE);
            textt8.setVisibility(View.GONE);
            textt9.setVisibility(View.GONE);
            textt10.setVisibility(View.GONE);


        }
        if(userList.size() == 4) {
            Button1.setText(userList.get(0).get("area"));
            Button2.setText(userList.get(1).get("area"));
            Button3.setText(userList.get(2).get("area"));
            Button4.setText(userList.get(3).get("area"));
            Button5.setVisibility(View.GONE);
            Button6.setVisibility(View.GONE);
            Button7.setVisibility(View.GONE);
            Button8.setVisibility(View.GONE);
            Button9.setVisibility(View.GONE);
            Button10.setVisibility(View.GONE);

            textt5.setVisibility(View.GONE);
            textt6.setVisibility(View.GONE);
            textt7.setVisibility(View.GONE);
            textt8.setVisibility(View.GONE);
            textt9.setVisibility(View.GONE);
            textt10.setVisibility(View.GONE);

        }
        if(userList.size() == 5) {
            Button1.setText(userList.get(0).get("area"));
            Button2.setText(userList.get(1).get("area"));
            Button3.setText(userList.get(2).get("area"));
            Button4.setText(userList.get(3).get("area"));
            Button5.setText(userList.get(4).get("area"));
            Button6.setVisibility(View.GONE);
            Button7.setVisibility(View.GONE);
            Button8.setVisibility(View.GONE);
            Button9.setVisibility(View.GONE);
            Button10.setVisibility(View.GONE);

            textt6.setVisibility(View.GONE);
            textt7.setVisibility(View.GONE);
            textt8.setVisibility(View.GONE);
            textt9.setVisibility(View.GONE);
            textt10.setVisibility(View.GONE);

        }
        if(userList.size() == 6) {
            Button1.setText(userList.get(0).get("area"));
            Button2.setText(userList.get(1).get("area"));
            Button3.setText(userList.get(2).get("area"));
            Button4.setText(userList.get(3).get("area"));
            Button5.setText(userList.get(4).get("area"));
            Button6.setText(userList.get(5).get("area"));
            Button7.setVisibility(View.GONE);
            Button8.setVisibility(View.GONE);
            Button9.setVisibility(View.GONE);
            Button10.setVisibility(View.GONE);

            textt7.setVisibility(View.GONE);
            textt8.setVisibility(View.GONE);
            textt9.setVisibility(View.GONE);
            textt10.setVisibility(View.GONE);

        }
        if(userList.size() == 7) {
            Button1.setText(userList.get(0).get("area"));
            Button2.setText(userList.get(1).get("area"));
            Button3.setText(userList.get(2).get("area"));
            Button4.setText(userList.get(3).get("area"));
            Button5.setText(userList.get(4).get("area"));
            Button6.setText(userList.get(5).get("area"));
            Button7.setText(userList.get(6).get("area"));
            Button8.setVisibility(View.GONE);
            Button9.setVisibility(View.GONE);
            Button10.setVisibility(View.GONE);

            textt8.setVisibility(View.GONE);
            textt9.setVisibility(View.GONE);
            textt10.setVisibility(View.GONE);

        }
        if(userList.size() == 8) {
            Button1.setText(userList.get(0).get("area"));
            Button2.setText(userList.get(1).get("area"));
            Button3.setText(userList.get(2).get("area"));
            Button4.setText(userList.get(3).get("area"));
            Button5.setText(userList.get(4).get("area"));
            Button6.setText(userList.get(5).get("area"));
            Button7.setText(userList.get(6).get("area"));
            Button8.setText(userList.get(7).get("area"));
            Button9.setVisibility(View.GONE);
            Button10.setVisibility(View.GONE);

            textt9.setVisibility(View.GONE);
            textt10.setVisibility(View.GONE);
        }
        if(userList.size() == 9) {
            Button1.setText(userList.get(0).get("area"));
            Button2.setText(userList.get(1).get("area"));
            Button3.setText(userList.get(2).get("area"));
            Button4.setText(userList.get(3).get("area"));
            Button5.setText(userList.get(4).get("area"));
            Button6.setText(userList.get(5).get("area"));
            Button7.setText(userList.get(6).get("area"));
            Button8.setText(userList.get(7).get("area"));
            Button9.setText(userList.get(8).get("area"));
            Button10.setVisibility(View.GONE);

            textt10.setVisibility(View.GONE);
        }
        if(userList.size() == 10) {
            Button1.setText(userList.get(0).get("area"));
            Button2.setText(userList.get(1).get("area"));
            Button3.setText(userList.get(2).get("area"));
            Button4.setText(userList.get(3).get("area"));
            Button5.setText(userList.get(4).get("area"));
            Button6.setText(userList.get(5).get("area"));
            Button7.setText(userList.get(6).get("area"));
            Button8.setText(userList.get(7).get("area"));
            Button9.setText(userList.get(8).get("area"));
            Button10.setText(userList.get(9).get("area"));

        }
        
        backto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask(userList.get(0).get("a_id")).execute();
                finish();
            }
        });

        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask(userList.get(1).get("a_id")).execute();
                finish();
            }
        });

        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask(userList.get(2).get("a_id")).execute();
                finish();
            }
        });

        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask(userList.get(3).get("a_id")).execute();
                finish();
            }
        });

        Button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask(userList.get(4).get("a_id")).execute();
                finish();
            }
        });

        Button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask(userList.get(5).get("a_id")).execute();
                finish();
            }
        });

        Button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask(userList.get(6).get("a_id")).execute();
                finish();
            }
        });

        Button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask(userList.get(7).get("a_id")).execute();
                finish();
            }
        });

        Button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask(userList.get(8).get("a_id")).execute();
                finish();
            }
        });

        Button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask(userList.get(9).get("a_id")).execute();
                finish();
            }
        });
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;
        String a_id;

        public BackgroundTask(String a_id) {
            this.a_id = a_id;
        }

        @Override
        protected void onPreExecute() {
            try {
                // 선택한 면적에 해당하는 a_id를 넘겨준다
                target = "http://3.35.217.139/area_select2_bb.php?a_id=" + URLEncoder.encode(a_id, "UTF-8");
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
            Intent intent = new Intent(AreaActivity.this, PredictActivity.class);
            intent.putExtra("userList", result);
            AreaActivity.this.startActivity(intent);
        }
    }
}

