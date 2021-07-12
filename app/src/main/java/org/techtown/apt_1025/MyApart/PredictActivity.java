package org.techtown.apt_1025.MyApart;

import android.content.Intent;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PredictActivity extends AppCompatActivity {

    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);
        ArrayList<HashMap<String, String>> userList;

        TextView tt = (TextView)findViewById(R.id.tt);
        Intent intent = getIntent();
        userList = new ArrayList<>();

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        ///////////////////////////////////////////////

        lineChart = (LineChart) findViewById(R.id.linechart);

        List<Entry> entries1 = new ArrayList<>();
        List<Entry> entries2 = new ArrayList<>();

        entries1.add(new Entry(0, Float.parseFloat(userList.get(0).get("a20_11_min"))));
        entries1.add(new Entry(1, Float.parseFloat(userList.get(0).get("a21_02_min"))));
        entries1.add(new Entry(2, Float.parseFloat(userList.get(0).get("a21_05_min"))));
        entries1.add(new Entry(3, Float.parseFloat(userList.get(0).get("a21_08_min"))));

        entries2.add(new Entry(0, Float.parseFloat(userList.get(0).get("a20_11_max"))));
        entries2.add(new Entry(1, Float.parseFloat(userList.get(0).get("a21_02_max"))));
        entries2.add(new Entry(2, Float.parseFloat(userList.get(0).get("a21_05_max"))));
        entries2.add(new Entry(3, Float.parseFloat(userList.get(0).get("a21_08_max"))));

        Button backtoto = (Button) findViewById(R.id.backtoto);
        backtoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        LineDataSet lineDataSet1 = new LineDataSet(entries1, "min_price");
        LineDataSet lineDataSet2 = new LineDataSet(entries2, "max_price");

        lineDataSet1.setLineWidth(3f); // 선의 두께
        lineDataSet1.setCircleRadius(4); // 원의 두께
        lineDataSet1.setCircleColor(Color.parseColor("#6699ff")); // 원의 색상 지정
        lineDataSet1.setDrawCircleHole(false);
        lineDataSet1.setCircleColorHole(Color.BLACK); // 원 안의 지점 원 색상 지정
        lineDataSet1.setColor(Color.parseColor("#6699ff")); // 선의 색상
        lineDataSet1.setValueTextSize(10f);

        lineDataSet2.setLineWidth(3f); // 선의 두께
        lineDataSet2.setCircleRadius(4); // 원의 두께
        lineDataSet2.setCircleColor(Color.parseColor("#f15f5f")); // 원의 색상 지정
        lineDataSet2.setDrawCircleHole(false);
        lineDataSet2.setCircleColorHole(Color.BLACK); // 원 안의 지점 원 색상 지정
        lineDataSet2.setColor(Color.parseColor("#f15f5f")); // 선의 색상
        lineDataSet2.setValueTextSize(10f);

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
        lineChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));
        lineChart.setData(lineData);
        lineChart.invalidate();
    }
}
