package org.techtown.apt_1025.MyApart;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
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
import java.util.List;

public class CheckBoxActivity extends AppCompatActivity {

    ListView listview;
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbox);
        Button checkback = (Button) findViewById(R.id.checkback);
        checkback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        listview = (ListView)findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, list);
        listview.setAdapter(adapter);
        Button tt = (Button)findViewById(R.id.tt);

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("apartList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count1 = 0;
            String a_id, apart_name, area;
            while (count1 < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count1);
                a_id = object.getString("a_id");
                apart_name = object.getString("apart_name");
                area = object.getString("area");
                Item apart = new Item(a_id, null, null, area, null, null, null, apart_name);
                list.add(apart_name + " " + area + "m2");
                count1++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tt.setOnClickListener(new View.OnClickListener() {
            String t1=null, t2=null;
            @Override
            public void onClick(View view) {
                SparseBooleanArray checkedItemPositions = listview.getCheckedItemPositions();
                int count = 0;

                for( int i=0; i<checkedItemPositions.size(); i++){
                    int pos = checkedItemPositions.keyAt(i);
                    if (checkedItemPositions.valueAt(i)) {
                        count++;
                    }
                }

                if(count != 2) {
                    Toast.makeText(getApplicationContext(), "2개를 체크하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    for( int i=0; i<checkedItemPositions.size(); i++){
                        int pos = checkedItemPositions.keyAt(i);
                        if (checkedItemPositions.valueAt(i)) {
                            if(t1 == null) {
                                t1 = listview.getItemAtPosition(pos).toString();
                            } else {
                                t2 = listview.getItemAtPosition(pos).toString();
                            }
                        }
                    }
                    new BackgroundTask(t1.substring(0, t1.indexOf(" ")), t2.substring(0, t2.indexOf(" ")), t1.substring(t1.indexOf(" "), t1.indexOf("m2")), t2.substring(t2.indexOf(" "), t2.indexOf("m2"))).execute();
                    t1 = null;
                    t2 = null;
                }
            }
        });
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;
        String name1, name2;
        String area1, area2;

        public BackgroundTask(String name1, String name2, String area1, String area2) {
            this.name1 = name1;
            this.name2 = name2;
            this.area1 = area1;
            this.area2 = area2;
        }

        @Override
        protected void onPreExecute() {
            try {
                target = "http://3.35.217.139/area_select3_bbb.php?name1=" + URLEncoder.encode(name1, "UTF-8") + "&name2=" + URLEncoder.encode(name2, "UTF-8") + "&area1=" + URLEncoder.encode(area1, "UTF-8") + "&area2=" + URLEncoder.encode(area2, "UTF-8");
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
            Intent intent = new Intent(CheckBoxActivity.this, CompareActivity.class);
            intent.putExtra("userList", result);
            CheckBoxActivity.this.startActivity(intent);
        }
    }
}

