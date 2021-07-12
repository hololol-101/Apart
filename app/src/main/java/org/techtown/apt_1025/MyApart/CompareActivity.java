package org.techtown.apt_1025.MyApart;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

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
import java.util.List;

public class CompareActivity extends AppCompatActivity {

    LineChart lineChart;

    TextView apart1Name;
    TextView apart2Name;
    TextView apart1YearBuilt;
    TextView apart2YearBuilt;
    TextView apart1Area;
    TextView apart2Area;
    TextView apart1Scale;
    TextView apart2Scale;

    TextView apart1Bus;
    TextView apart2Bus;
    TextView apart1Subway;
    TextView apart2Subway;
    TextView apart1Child;
    TextView apart2Child;
    TextView apart1Kindergarten;
    TextView apart2Kindergarten;
    TextView apart1School;
    TextView apart2School;
    TextView apart1Parking;
    TextView apart2Parking;
    TextView apart1Mart;
    TextView apart2Mart;
    TextView apart1ConvenienceStore;
    TextView apart2ConvenienceStore;
    TextView apart1Laundry;
    TextView apart2Laundry;
    TextView apart1Bank;
    TextView apart2Bank;
    TextView apart1Hospital;
    TextView apart2Hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        ArrayList<HashMap<String, String>> userList;
        Intent intent = getIntent();
        userList = new ArrayList<>();

        apart1Name = (TextView)findViewById(R.id.apart1Name);
        apart2Name = (TextView)findViewById(R.id.apart2Name);
        apart1YearBuilt = (TextView)findViewById(R.id.apart1YearBuilt);
        apart2YearBuilt = (TextView)findViewById(R.id.apart2YearBuilt);
        apart1Area = (TextView)findViewById(R.id.apart1Area);
        apart2Area = (TextView)findViewById(R.id.apart2Area);
        apart1Scale = (TextView)findViewById(R.id.apart1Scale);
        apart2Scale = (TextView)findViewById(R.id.apart2Scale);
        apart1Bus = (TextView)findViewById(R.id.apart1Bus);
        apart2Bus = (TextView)findViewById(R.id.apart2Bus);
        apart1Subway = (TextView)findViewById(R.id.apart1Subway);
        apart2Subway = (TextView)findViewById(R.id.apart2Subway);
        apart1Child = (TextView)findViewById(R.id.apart1Child);
        apart2Child = (TextView)findViewById(R.id.apart2Child);
        apart1Kindergarten = (TextView)findViewById(R.id.apart1Kindergarten);
        apart2Kindergarten = (TextView)findViewById(R.id.apart2Kindergarten);
        apart1School = (TextView)findViewById(R.id.apart1School);
        apart2School = (TextView)findViewById(R.id.apart2School);
        apart1Parking = (TextView)findViewById(R.id.apart1Parking);
        apart2Parking = (TextView)findViewById(R.id.apart2Parking);
        apart1Mart = (TextView)findViewById(R.id.apart1Mart);
        apart2Mart = (TextView)findViewById(R.id.apart2Mart);
        apart1ConvenienceStore = (TextView)findViewById(R.id.apart1ConvenienceStore);
        apart2ConvenienceStore = (TextView)findViewById(R.id.apart2ConvenienceStore);
        apart1Laundry = (TextView)findViewById(R.id.apart1Laundry);
        apart2Laundry = (TextView)findViewById(R.id.apart2Laundry);
        apart1Bank = (TextView)findViewById(R.id.apart1Bank);
        apart2Bank = (TextView)findViewById(R.id.apart2Bank);
        apart1Hospital = (TextView)findViewById(R.id.apart1Hospital);
        apart2Hospital = (TextView)findViewById(R.id.apart2Hospital);
        Button backtocom = (Button)findViewById(R.id.backtocom);
        backtocom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String a_id = item.getString("a_id");
                String apart_name = item.getString("apart_name");
                String a20_11_max = item.getString("20_11_max");
                String a20_11_min = item.getString("20_11_min");
                String month20_11 = item.getString("month20_11");
                String a21_02_max = item.getString("21_02_max");
                String a21_02_min = item.getString("21_02_min");
                String month21_02 = item.getString("month21_02");
                String a21_05_max = item.getString("21_05_max");
                String a21_05_min = item.getString("21_05_min");
                String month21_05 = item.getString("month21_05");
                String a21_08_max = item.getString("21_08_max");
                String a21_08_min = item.getString("21_08_min");
                String month21_08 = item.getString("month21_08");
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("a_id", a_id);
                hashMap.put("apart_name", apart_name);
                hashMap.put("a20_11_max", a20_11_max);
                hashMap.put("a20_11_min", a20_11_min);
                hashMap.put("month20_11", month20_11);
                hashMap.put("a21_02_max", a21_02_max);
                hashMap.put("a21_02_min", a21_02_min);
                hashMap.put("month21_02", month21_02);
                hashMap.put("a21_05_max", a21_05_max);
                hashMap.put("a21_05_min", a21_05_min);
                hashMap.put("month21_05", month21_05);
                hashMap.put("a21_08_max", a21_08_max);
                hashMap.put("a21_08_min", a21_08_min);
                hashMap.put("month21_08", month21_08);
                userList.add(hashMap);
            }
            new CompareActivity.BackgroundTask(userList.get(0).get("a_id"), userList.get(1).get("a_id")).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ////////////////////////////////////

        lineChart = (LineChart) findViewById(R.id.linechart);

        List<Entry> entries1 = new ArrayList<>();
        List<Entry> entries2 = new ArrayList<>();
        List<Entry> entries3 = new ArrayList<>();
        List<Entry> entries4 = new ArrayList<>();

        entries1.add(new Entry(0, Float.parseFloat(userList.get(0).get("a20_11_min"))));
        entries1.add(new Entry(1, Float.parseFloat(userList.get(0).get("a21_02_min"))));
        entries1.add(new Entry(2, Float.parseFloat(userList.get(0).get("a21_05_min"))));
        entries1.add(new Entry(3, Float.parseFloat(userList.get(0).get("a21_08_min"))));

        entries2.add(new Entry(0, Float.parseFloat(userList.get(0).get("a20_11_max"))));
        entries2.add(new Entry(1, Float.parseFloat(userList.get(0).get("a21_02_max"))));
        entries2.add(new Entry(2, Float.parseFloat(userList.get(0).get("a21_05_max"))));
        entries2.add(new Entry(3, Float.parseFloat(userList.get(0).get("a21_08_max"))));

        entries3.add(new Entry(0, Float.parseFloat(userList.get(1).get("a20_11_min"))));
        entries3.add(new Entry(1, Float.parseFloat(userList.get(1).get("a21_02_min"))));
        entries3.add(new Entry(2, Float.parseFloat(userList.get(1).get("a21_05_min"))));
        entries3.add(new Entry(3, Float.parseFloat(userList.get(1).get("a21_08_min"))));

        entries4.add(new Entry(0, Float.parseFloat(userList.get(1).get("a20_11_max"))));
        entries4.add(new Entry(1, Float.parseFloat(userList.get(1).get("a21_02_max"))));
        entries4.add(new Entry(2, Float.parseFloat(userList.get(1).get("a21_05_max"))));
        entries4.add(new Entry(3, Float.parseFloat(userList.get(1).get("a21_08_max"))));


        LineDataSet lineDataSet1 = new LineDataSet(entries1, "min_price");
        LineDataSet lineDataSet2 = new LineDataSet(entries2, "max_price");
        LineDataSet lineDataSet3 = new LineDataSet(entries3, "min_price");
        LineDataSet lineDataSet4 = new LineDataSet(entries4, "max_price");

        // min
        lineDataSet1.setLineWidth(3f); // 선의 두께
        lineDataSet1.setCircleRadius(4); // 원의 두께
        lineDataSet1.setCircleColor(Color.parseColor("#5fbff0")); // 원의 색상 지정
        lineDataSet1.setDrawCircleHole(false);
        lineDataSet1.setCircleColorHole(Color.BLACK); // 원 안의 지점 원 색상 지정
        lineDataSet1.setColor(Color.parseColor("#5fbff0")); // 선의 색상
        lineDataSet1.setValueTextSize(10f);

        // max
        lineDataSet2.setLineWidth(3f); // 선의 두께
        lineDataSet2.setCircleRadius(4); // 원의 두께
        lineDataSet2.setCircleColor(Color.parseColor("#2196f3")); // 원의 색상 지정
        lineDataSet2.setDrawCircleHole(false);
        lineDataSet2.setCircleColorHole(Color.BLACK); // 원 안의 지점 원 색상 지정
        lineDataSet2.setColor(Color.parseColor("#2196f3")); // 선의 색상
        lineDataSet2.setValueTextSize(10f);

        // min
        lineDataSet3.setLineWidth(3f); // 선의 두께
        lineDataSet3.setCircleRadius(4); // 원의 두께
        lineDataSet3.setCircleColor(Color.parseColor("#ffdb5e")); // 원의 색상 지정
        lineDataSet3.setDrawCircleHole(false);
        lineDataSet3.setCircleColorHole(Color.BLACK); // 원 안의 지점 원 색상 지정
        lineDataSet3.setColor(Color.parseColor("#ffdb5e")); // 선의 색상
        lineDataSet3.setValueTextSize(10f);

        // max
        lineDataSet4.setLineWidth(3f); // 선의 두께
        lineDataSet4.setCircleRadius(4); // 원의 두께
        lineDataSet4.setCircleColor(Color.parseColor("#FFAB40")); // 원의 색상 지정
        lineDataSet4.setDrawCircleHole(false);
        lineDataSet4.setCircleColorHole(Color.BLACK); // 원 안의 지점 원 색상 지정
        lineDataSet4.setColor(Color.parseColor("#FFAB40")); // 선의 색상
        lineDataSet4.setValueTextSize(10f);

        List<String> xAxisValues = new ArrayList<>();

        xAxisValues.add(userList.get(0).get("month20_11") + "개월후");
        xAxisValues.add(userList.get(0).get("month21_02") + "개월후");
        xAxisValues.add(userList.get(0).get("month21_05") + "개월후");
        xAxisValues.add(userList.get(0).get("month21_08") + "개월후");

        Description description = new Description();
        description.setText("");
        lineChart.setDrawGridBackground(true);
        lineChart.setDescription(description);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(3, false);
        xAxis.setTextSize(10f);
        YAxis yAxis = lineChart.getAxisRight();
        yAxis.setEnabled(false);

        LineData lineData = new LineData(lineDataSet1);
        lineData.addDataSet(lineDataSet2);
        lineData.addDataSet(lineDataSet3);
        lineData.addDataSet(lineDataSet4);
        lineChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;
        String t1, t2;

        public BackgroundTask(String t1, String t2) {
            this.t1 = t1;
            this.t2 = t2;
        }

        @Override
        protected void onPreExecute() {
            try {
                // 선택한 두 항목의 a_id를 넘겨준다
                target = "http://3.35.217.139/area_select4_bm.php?t1=" + URLEncoder.encode(t1, "UTF-8") + "&t2=" + URLEncoder.encode(t2, "UTF-8");
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

            ArrayList<HashMap<String, String>> userList;
            userList = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                for(int i=0;i<jsonArray.length();i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    String a_id = item.getString("a_id");
                    String apart_name = item.getString("apart_name");
                    String year_built = item.getString("year_built");
                    String area = item.getString("area");
                    String scale = item.getString("scale");
                    String facility = item.getString("facility");
                    String distance = item.getString("distance");
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("a_id", a_id);
                    hashMap.put("apart_name", apart_name);
                    hashMap.put("year_built", year_built);
                    hashMap.put("area", area);
                    hashMap.put("scale", scale);
                    hashMap.put("facility", facility);
                    hashMap.put("distance", distance);
                    userList.add(hashMap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            apart1Name.setText(userList.get(0).get("apart_name"));
            apart2Name.setText(userList.get(userList.size()-1).get("apart_name"));
            apart1Name.setTextColor(Color.parseColor("#2196f3"));
            apart2Name.setTextColor(Color.parseColor("#FFAB40"));
            apart1YearBuilt.setText(userList.get(0).get("year_built"));
            apart2YearBuilt.setText(userList.get(userList.size()-1).get("year_built"));
            apart1Area.setText(userList.get(0).get("area"));
            apart2Area.setText(userList.get(userList.size()-1).get("area"));
            if(userList.get(0).get("scale").equals("0"))
                apart1Scale.setText("알 수 없음");
            else
                apart1Scale.setText(userList.get(0).get("scale").split("\n")[1]);

            if(userList.get(userList.size()-1).get("scale").equals("0"))
                apart2Scale.setText("알 수 없음");
            else
                apart2Scale.setText(userList.get(userList.size()-1).get("scale").split("\n")[1]);



            String dis;
            for(int i=0;i<userList.size();i++) {
                if(userList.get(i).get("facility").equals("버스정류장") && userList.get(i).get("a_id").equals(t1)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart1Bus.setText(dis+"m");
                }
                else if (userList.get(i).get("facility").equals("지하철역") && userList.get(i).get("a_id").equals(t1)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart1Subway.setText(dis+"m");
                }
                else if (userList.get(i).get("facility").equals("어린이집") && userList.get(i).get("a_id").equals(t1)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart1Child.setText(dis+"m");
                }
                else if (userList.get(i).get("facility").equals("유치원") && userList.get(i).get("a_id").equals(t1)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart1Kindergarten.setText(dis+"m");
                }
                else if (userList.get(i).get("facility").equals("학교") && userList.get(i).get("a_id").equals(t1)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart1School.setText(dis+"m");
                }
                else if (userList.get(i).get("facility").equals("주차장") && userList.get(i).get("a_id").equals(t1)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart1Parking.setText(dis+"m");
                }
                else if (userList.get(i).get("facility").equals("마트") && userList.get(i).get("a_id").equals(t1)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart1Mart.setText(dis+"m");
                }
                else if (userList.get(i).get("facility").equals("편의점") && userList.get(i).get("a_id").equals(t1)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart1ConvenienceStore.setText(dis+"m");
                }
                else if (userList.get(i).get("facility").equals("세탁소") && userList.get(i).get("a_id").equals(t1)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart1Laundry.setText(dis+"m");
                }
                else if (userList.get(i).get("facility").equals("은행") && userList.get(i).get("a_id").equals(t1)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart1Bank.setText(dis+"m");
                }
                else if (userList.get(i).get("facility").equals("병원") && userList.get(i).get("a_id").equals(t1)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart1Hospital.setText(dis+"m");
                }
            }

            for(int i=0;i<userList.size();i++) {
                if(userList.get(i).get("facility").equals("버스정류장") && userList.get(i).get("a_id").equals(t2)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart2Bus.setText(dis+"m");
                }
                else if (userList.get(i).get("facility").equals("지하철역") && userList.get(i).get("a_id").equals(t2)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart2Subway.setText(dis+"m");
                }
                else if (userList.get(i).get("facility").equals("어린이집") && userList.get(i).get("a_id").equals(t2)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart2Child.setText(dis+"m");
                }
                else if (userList.get(i).get("facility").equals("유치원") && userList.get(i).get("a_id").equals(t2)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart2Kindergarten.setText(dis+"m");
                }
                else if (userList.get(i).get("facility").equals("학교") && userList.get(i).get("a_id").equals(t2)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart2School.setText(dis+"m");
                }
                else if (userList.get(i).get("facility").equals("주차장") && userList.get(i).get("a_id").equals(t2)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart2Parking.setText(dis+"m");
                }
                else if (userList.get(i).get("facility").equals("마트") && userList.get(i).get("a_id").equals(t2)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart2Mart.setText(dis+"m");
                }
                else if (userList.get(i).get("facility").equals("편의점") && userList.get(i).get("a_id").equals(t2)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart2ConvenienceStore.setText(dis+"m");
                }
                else if (userList.get(i).get("facility").equals("세탁소") && userList.get(i).get("a_id").equals(t2)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart2Laundry.setText(dis+"m");
                }
                else if (userList.get(i).get("facility").equals("은행") && userList.get(i).get("a_id").equals(t2)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart2Bank.setText(dis+"m");
                }
                else if (userList.get(i).get("facility").equals("병원") && userList.get(i).get("a_id").equals(t2)){
                    dis = userList.get(i).get("distance");
                    for(int j = 0; j<dis.length();j++){
                        if(dis.charAt(j)==46){
                            dis = dis.substring(0,j);
                            break;
                        }
                    }
                    apart2Hospital.setText(dis+"m");
                }
            }
        }
    }
}

