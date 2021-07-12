package org.techtown.apt_1025;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.apt_1025.Login.FindModifyActivity;
import org.techtown.apt_1025.Login.LoginRequest;
import org.techtown.apt_1025.Login.RegisterActivity;
import org.techtown.apt_1025.MainScreen.MainScreenClass;

public class MainActivity extends AppCompatActivity {

    private EditText login_id, login_password;
    private TextView maintitle;
    private ImageView mainlogo;
    private Button login_button, join_button, find_button;
    private AlertDialog dialog;

    private int u_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        maintitle = findViewById(R.id.maintitle);
        mainlogo = findViewById(R.id.title_image);
        login_id = findViewById( R.id.login_id );
        login_password = findViewById( R.id.login_password );


        join_button = findViewById( R.id.join_button );
        join_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this, RegisterActivity.class );
                startActivity( intent );
            }
        });

        find_button = findViewById(R.id.find_button);
        find_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, FindModifyActivity.class );
                startActivity( intent );
            }
        });


        login_button = findViewById( R.id.login_button );
        login_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserID = login_id.getText().toString();
                String UserPwd = login_password.getText().toString();

                if (UserID.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                if (UserPwd.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    dialog = builder.setMessage("비밀번호를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );

                            if(success) {//로그인 성공시
                                u_id = jsonObject.getInt("u_id");
                                String UserID = jsonObject.getString( "mem_id" );
                                String UserPwd = jsonObject.getString( "mem_pw" );
                                String UserEmail = jsonObject.getString( "email" );

                                Toast.makeText( getApplicationContext(), String.format("%s님 환영합니다.", UserID), Toast.LENGTH_SHORT ).show();
                                Intent intent = new Intent( MainActivity.this, MainScreenClass.class);

                                intent.putExtra("u_id",u_id);
                                intent.putExtra( "mem_id", UserID );
                                intent.putExtra( "mem_pw", UserPwd );
                                intent.putExtra( "email",UserEmail );
                                startActivity( intent );
                                login_id.setText("");
                                login_password.setText("");

                            } else {//로그인 실패시
                                Toast.makeText( getApplicationContext(), "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT ).show();
                                return;
                            }

                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            dialog = builder.setMessage("아이디/비밀번호를 확인해주세요").setPositiveButton("확인", null).create();
                            dialog.show();
                            e.printStackTrace();
                            return;
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest( UserID, UserPwd, responseListener );
                RequestQueue queue = Volley.newRequestQueue( MainActivity.this );
                queue.add( loginRequest );
            }
        });

    }
}