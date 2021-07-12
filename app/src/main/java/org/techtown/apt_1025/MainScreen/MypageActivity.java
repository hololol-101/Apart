package org.techtown.apt_1025.MainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.apt_1025.Login.ModifypwRequest;
import org.techtown.apt_1025.MainActivity;
import org.techtown.apt_1025.R;

public class MypageActivity extends AppCompatActivity {
    private TextView my_id, my_email,confirm_pw_error,new_pw_error;
    private EditText new_pw, confirm_new_pw;
    private Button back, modify;
    private AlertDialog dialog;
    private String UserEmail, UserID, UserPW;

    public boolean pwFormCheck(String pw){
        int clsfr[] = {-1,0,0};
        if((pw.length()<6)||(pw.length()>20))
            return false;
        for(int i = 0; i<pw.length(); i++){
            if(((pw.charAt(i)>='0')&&(pw.charAt(i)<='9')))
                clsfr[0] = 0;
            else if(((pw.charAt(i)>='A')&&(pw.charAt(i)<='Z'))||((pw.charAt(i)>='a')&&(pw.charAt(i)<='z')))
                clsfr[0] = 1;
            switch(clsfr[0]){
                case 0:
                    clsfr[1]++;
                    break;
                case 1:
                    clsfr[2]++;
                    break;
                case -1:
                    return false;
            }
        }
        if((clsfr[1]==0)||(clsfr[2]==0))
            return false;
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mypage );
        new_pw = findViewById(R.id.my_input_new_pw);
        confirm_new_pw = findViewById(R.id.my_confirm_new_pw);
        back = findViewById(R.id.my_back);
        modify = findViewById(R.id. my_modify);
        my_email = findViewById(R.id.my_email);
        my_id = findViewById(R.id.my_id);
        confirm_pw_error = findViewById(R.id.my_confirm_pw_error);
        new_pw_error = findViewById(R.id.my_new_pw_error);

        Intent intent = getIntent();
        UserID = intent.getStringExtra("mem_id");
        UserPW = intent.getStringExtra("mem_pw");
        UserEmail = intent.getStringExtra("email");
        my_id.setText(UserID);
        my_email.setText(UserEmail);



        confirm_new_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!(new_pw.getText().toString().equals(s.toString()))){
                    confirm_pw_error.setText("비밀번호가 일치하지 않습니다.");    // 경고 메세지
                    //confirm_new_pw.setBackgroundResource(R.drawable.red_edittext);
                }
                else {
                    confirm_pw_error.setText("");         //에러 메세지 제거
                    //confirm_new_pw.setBackgroundResource(R.drawable.white_edittext);
                }
            }
        });

        new_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //input_new_pw.setBackgroundResource(R.drawable.white_edittext);
                new_pw_error.setText("");
            }
        });

        modify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String newUserPw = new_pw.getText().toString();
                String confirm_pw = confirm_new_pw.getText().toString();
                //한 칸이라도 입력 안했을 경우
                if (newUserPw.equals("") || confirm_pw.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MypageActivity.this);
                    dialog = builder.setMessage("변경할 비밀번호 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                if(!pwFormCheck(new_pw.getText().toString()))
                {
                    new_pw_error.setText("6~16자의 영문+숫자 조합(대/소문자 구분)");
                    //input_new_pw.setBackgroundResource(R.drawable.red_edittext);
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );
                            if(newUserPw.equals(confirm_pw)) {
                                if (success) {
                                    Toast.makeText(getApplicationContext(), "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "비밀번호 변경을 실패했습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MypageActivity.this);
                                dialog = builder.setMessage("비밀번호가 동일하지 않습니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ModifypwRequest modifyRequest = new ModifypwRequest( UserID, newUserPw, responseListener);
                RequestQueue queue = Volley.newRequestQueue( MypageActivity.this );
                queue.add( modifyRequest );
            }
        });

        back.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

    }
}