package org.techtown.apt_1025.MainScreen;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.apt_1025.R;
import org.w3c.dom.Text;

public class ResultActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String news_url = "";
        Bundle extras = getIntent().getExtras();

        news_url = extras.getString("url");
        TextView textView = (TextView) findViewById(R.id.textView_result);

        textView.setText(news_url);


    }
}
