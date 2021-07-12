package org.techtown.apt_1025.MainScreen;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import org.techtown.apt_1025.R;

public class drawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        // 전체화면인 DrawerLayout 객체 참조
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Drawer 화면(뷰) 객체 참조
        final View drawerView = (View) findViewById(R.id.drawer);


        // 드로어 화면을 열고 닫을 버튼 객체 참조
        Button btnCloseDrawer = (Button) findViewById(R.id.btn_CloseDrawer);



        // 드로어 닫는 버튼 리스너
        btnCloseDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
            }
        });


    }
}