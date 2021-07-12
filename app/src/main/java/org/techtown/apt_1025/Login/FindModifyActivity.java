package org.techtown.apt_1025.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import org.techtown.apt_1025.MainActivity;
import org.techtown.apt_1025.R;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class FindModifyActivity extends AppCompatActivity {

    private TextView find_id,find_pw,auth_counter, new_pw, new_pw_error, confirm_pw_error;
    private EditText input_email_id, input_email_pw, input_authcode, input_new_pw, confirm_new_pw, input_id_pw;
    private Button find_id_button, send_code_button, authcode_button, modify_button, cancelm_button;
    private AlertDialog dialog;
    CountDownTimer countDownTimer;
    final int MILLISINFUTURE = 180 * 1000; //총 시간 (300초 = 5분)
    final int COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)
    private boolean time_ongoing = false;
    private boolean isAuthorized = false;
    private String code;

    public void countDownTimer() { //카운트 다운 메소드
        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {

                long emailAuthCount = millisUntilFinished / 1000;
                time_ongoing = true;
                Log.d("Alex", emailAuthCount + "");

                if ((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10) {
                    auth_counter.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                } else {
                    auth_counter.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }
            }

            @Override
            public void onFinish() { //시간이 다 되면 다이얼로그 종료
                auth_counter.setText("끝~");
                time_ongoing = false;
                code="------";
            }
        }.start();
    }

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
        setContentView( R.layout.activity_findnmodify );
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        find_id=findViewById(R.id.find_id);
        find_pw=findViewById(R.id.find_pw);
        new_pw = findViewById(R.id.new_pw);
        input_email_id = findViewById( R.id.input_email_id);
        find_id_button = findViewById(R.id.find_id_button);
        input_id_pw = findViewById(R.id.input_id_pw);
        input_email_pw = findViewById(R.id.input_email_pw);
        input_new_pw = findViewById(R.id.input_new_pw);
        confirm_new_pw = findViewById(R.id.confirm_new_pw);
        send_code_button = findViewById(R.id.send_code_button);
        auth_counter=findViewById(R.id.auth_counter);
        input_authcode = findViewById(R.id.input_authcode);
        authcode_button = findViewById(R.id.authcode_button);
        modify_button = findViewById(R.id.modify_button);
        cancelm_button = findViewById(R.id.cancelm_button);
        confirm_pw_error = findViewById(R.id.confirm_pw_error);
        new_pw_error = findViewById(R.id.new_pw_error);

        input_new_pw.setEnabled(false);
        confirm_new_pw.setEnabled(false);
        modify_button.setEnabled(false);


        find_id_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idEmail = input_email_id.getText().toString();

                if (idEmail.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindModifyActivity.this);
                    dialog = builder.setMessage("이메일을 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindModifyActivity.this);
                                dialog = builder.setMessage("등록되지 않은 이메일입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindModifyActivity.this);
                                dialog = builder.setMessage(jsonResponse.getString("mem_id")).setPositiveButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                FindidRequest findidRequest = new FindidRequest(idEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(FindModifyActivity.this);
                queue.add(findidRequest);
            }
        });

        send_code_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwId = input_id_pw.getText().toString();
                String pwEmail = input_email_pw.getText().toString();
                if(isAuthorized) {
                    return;
                }
                if(pwId.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindModifyActivity.this);
                    dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                if (pwEmail.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindModifyActivity.this);
                    dialog = builder.setMessage("이메일을 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindModifyActivity.this);
                                dialog = builder.setMessage("등록되지 않은 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                            }
                            else {
                                String UserEmail = jsonResponse.getString("email");
                                if(pwEmail.equals(UserEmail))
                                {
                                    try {
                                        GMailSender gMailSender = new GMailSender();
                                        //GMailSender.sendMail(제목, 본문내용, 받는사람);
                                        code = gMailSender.getEmailCode();
                                        String mailContents = "아래의 인증번호를 어플리케이션에 입력해주세요.\n"+code;
                                        gMailSender.sendMail("대구아파트 인증번호입니다.", mailContents, input_email_pw.getText().toString());
                                        Toast.makeText(getApplicationContext(), "이메일을 성공적으로 보냈습니다.", Toast.LENGTH_SHORT).show();
                                        send_code_button.setText("재전송");
                                        if(time_ongoing) {
                                            countDownTimer.cancel();
                                            countDownTimer();
                                        }
                                        else {
                                            countDownTimer();
                                        }
                                    } catch (SendFailedException m) {
                                        Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                                    } catch (MessagingException m) {
                                        Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show();
                                    } catch (Exception m) {
                                        m.printStackTrace();
                                    }
                                }
                                else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(FindModifyActivity.this);
                                    dialog = builder.setMessage("이메일을 확인해 주세요").setPositiveButton("확인", null).create();
                                    dialog.show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                FindpwRequest findpwRequest = new FindpwRequest(pwId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(FindModifyActivity.this);
                queue.add(findpwRequest);
            }
        });

        authcode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(isAuthorized)
                {
                    return;
                }
                if(input_authcode.getText().toString().equals(code)) {
                    countDownTimer.cancel();
                    auth_counter.setText("");
                    authcode_button.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    send_code_button.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    isAuthorized = true;
                    input_id_pw.setEnabled(false);
                    input_email_pw.setEnabled(false);
                    input_authcode.setEnabled(false);
                    input_new_pw.setEnabled(true);
                    //input_new_pw.setBackgroundResource(R.drawable.line_edittext);
                    confirm_new_pw.setEnabled(true);
                    //confirm_new_pw.setBackgroundResource(R.drawable.line_edittext);
                    modify_button.setEnabled(true);
                    send_code_button.setText("인증완료");
                    return;
                }
                else {
                    Toast.makeText(getApplicationContext(), "잘못된 인증번호입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        confirm_new_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!(input_new_pw.getText().toString().equals(s.toString()))){
                    confirm_pw_error.setText("비밀번호가 일치하지 않습니다.");    // 경고 메세지
                    //confirm_new_pw.setBackgroundResource(R.drawable.red_edittext);
                }
                else {
                    confirm_pw_error.setText("");         //에러 메세지 제거
                    //confirm_new_pw.setBackgroundResource(R.drawable.white_edittext);
                }
            }
        });

        input_new_pw.addTextChangedListener(new TextWatcher() {
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

        modify_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String confirmedId = input_id_pw.getText().toString();
                String newUserPw = input_new_pw.getText().toString();
                String confirm_pw = confirm_new_pw.getText().toString();
                //한 칸이라도 입력 안했을 경우
                if (newUserPw.equals("") || confirm_pw.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindModifyActivity.this);
                    dialog = builder.setMessage("변경할 비밀번호 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                if(!pwFormCheck(input_new_pw.getText().toString()))
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
                                    Toast.makeText(getApplicationContext(), String.format("비밀번호가 변경되었습니다."), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(FindModifyActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "비밀번호 변경을 실패했습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindModifyActivity.this);
                                dialog = builder.setMessage("비밀번호가 동일하지 않습니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ModifypwRequest modifyRequest = new ModifypwRequest( confirmedId, newUserPw, responseListener);
                RequestQueue queue = Volley.newRequestQueue( FindModifyActivity.this );
                queue.add( modifyRequest );
            }
        });

        cancelm_button.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(time_ongoing)
                {
                    countDownTimer.cancel();
                    time_ongoing = false;
                }
                finish();
            }
        });

    }
}