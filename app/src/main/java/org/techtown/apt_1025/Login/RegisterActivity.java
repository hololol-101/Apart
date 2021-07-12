package org.techtown.apt_1025.Login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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


public class RegisterActivity extends AppCompatActivity {

    private EditText join_id, join_password, join_pwck, join_email,join_input_authcode;
    private Button join_button, check_button, cancel_button,join_authcode_button, send_code_button,back_button, testtest;
    private AlertDialog dialog;
    private LinearLayout lin_id;
    private TextView id_error, pw_error,pwck_error, eauth_counter;
    private String code;
    private boolean validate = false;
    private boolean form_error = false;
    private boolean pw_form_error = false;
    private boolean e_validate = false;
    private boolean time_ongoing = false;
    private boolean after_touch = false;

    CountDownTimer countDownTimer;
    final int MILLISINFUTURE = 180 * 1000; //총 시간 (300초 = 5분)
    final int COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)

    //if(!(((id.charAt(i)>='0')&&(id.charAt(i)<='9'))||((id.charAt(i)>='a')&&(id.charAt(i)<='z'))))
    //        return false;

    public void countDownTimer() { //카운트 다운 메소드
        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {

                long emailAuthCount = millisUntilFinished / 1000;
                time_ongoing = true;
                Log.d("Alex", emailAuthCount + "");

                if ((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10) {
                    eauth_counter.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                } else {
                    eauth_counter.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }
            }

            @Override
            public void onFinish() { //시간이 다 되면 다이얼로그 종료
                eauth_counter.setText("시간만료");
                eauth_counter.setTextColor(Color.parseColor("#ff0000"));
                time_ongoing = false;
                code="------";
            }
        }.start();
    }

    public boolean idFormCheck(String id){
        if((id.length()<5)||(id.length()>20))
            return false;
        for(int i = 0; i<id.length();i++){
            if(!(((id.charAt(i)>='0')&&(id.charAt(i)<='9'))||((id.charAt(i)>='a')&&(id.charAt(i)<='z'))))
                return false;
        }
        return true;
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
        setContentView(R.layout.activity_join );
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        //아이디값 찾아주기
        join_id = findViewById( R.id.join_id );
        join_password = findViewById( R.id.join_password );
        join_pwck = findViewById(R.id.join_pwck);
        join_email = findViewById(R.id.join_email);
        id_error=findViewById(R.id.id_error);
        pw_error=findViewById(R.id.pw_error);
        pwck_error=findViewById(R.id.pwck_error);
        send_code_button = findViewById(R.id.send_ecode_button);
        eauth_counter = findViewById(R.id.eauth_counter);
        join_authcode_button = findViewById(R.id.join_eauthcode_button);
        join_input_authcode = findViewById(R.id.join_input_authcode);
        lin_id = findViewById(R.id.lin_id);
        testtest = findViewById(R.id.testtest);


        //Spinner spinner = (Spinner)findViewById(R.id.spinner_field);
        //spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           // @Override
        //    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         //       email_domain = spinner.getSelectedItem().toString();
         //   }
        //    @Override
         //   public void onNothingSelected(AdapterView<?> parent) {
          //  }
        //});

        join_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!idFormCheck(s.toString())){
                    form_error = true;
                }
                else{
                    id_error.setText("");         //에러 메세지 제거
                    join_password.setBackgroundResource(R.drawable.line_edittext);
                    form_error = false;
                }
            }// afterTextChanged()..
        });

        join_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!pwFormCheck(join_password.getText().toString())&&pw_form_error){
                    pw_error.setText("6~16자의 영문/숫자 조합으로 만드세요.(대/소문자 구분, 공백은 입력불가)");
                    join_password.setBackgroundResource(R.drawable.red_edittext);
                    pw_form_error = true;
                }
                else {
                    pw_error.setText("");         //에러 메세지 제거
                    join_password.setBackgroundResource(R.drawable.line_edittext);
                    pw_form_error = false;
                }
                if(!(join_pwck.getText().toString().equals(s.toString()))&&!(join_pwck.getText().toString().length()==0)){
                    pwck_error.setText("비밀번호가 일치하지 않습니다.");    // 경고 메세지
                    join_pwck.setBackgroundResource(R.drawable.red_edittext);
                }
                else {
                    pwck_error.setText("");         //에러 메세지 제거
                    join_pwck.setBackgroundResource(R.drawable.line_edittext);
                }
            }
        });


        join_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        if(!after_touch){
                            after_touch = true;
                        }
                    }
                }
                return false;
            }
        });

        join_pwck.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        if(!pwFormCheck(join_password.getText().toString())){
                            if(after_touch&&!(join_password.getText().toString().length()==0)) {
                                pw_error.setText("6~16자의 영문/숫자 조합으로 만드세요.(대/소문자 구분, 공백은 입력불가)");
                                join_password.setBackgroundResource(R.drawable.red_edittext);
                                pw_form_error = true;
                            }
                        }
                    }
                }
                return false;
            }
        });


        join_pwck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!(join_password.getText().toString().equals(s.toString()))){
                    pwck_error.setText("비밀번호가 일치하지 않습니다.");    // 경고 메세지
                    join_pwck.setBackgroundResource(R.drawable.red_edittext);
                }
                else {
                    pwck_error.setText("");         //에러 메세지 제거
                    join_pwck.setBackgroundResource(R.drawable.line_edittext);
                }
            }
        });


        //아이디 중복 체크
        check_button = findViewById(R.id.check_button);
        check_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String UserId = join_id.getText().toString();
                if (validate) {
                    return; //검증 완료
                }
                if(form_error){
                    id_error.setText("5~20자의 영문 소문자, 숫자만 사용 가능합니다.");    // 경고 메세지
                    lin_id.setBackgroundResource(R.drawable.red_edittext);
                    return;
                }

                if (UserId.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                join_id.setEnabled(false); //아이디값 고정
                                validate = true; //검증 완료
                                lin_id.setBackgroundResource(R.drawable.line_edittext);
                                check_button.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("이미 사용중인 아이디입니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(UserId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });

        send_code_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idEmail = join_email.getText().toString();

                if (idEmail.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
                                try {
                                    GMailSender gMailSender2 = new GMailSender();
                                    //der.sendMail(제목, 본문내용, 받는사람);
                                    code = gMailSender2.getEmailCode();
                                    String mailContents = "아래의 인증번호를 어플리케이션에 입력해주세요.\n"+code;
                                    gMailSender2.sendMail("대구아파트 인증번호입니다.", mailContents, idEmail);
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
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("이미 등록된 이메일입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                EValidateRequest evalidateRequest = new EValidateRequest(idEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(evalidateRequest);
            }
        });

        join_authcode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(e_validate)
                {
                    return;
                }
                if(join_input_authcode.getText().toString().equals(code)) {
                    countDownTimer.cancel();
                    eauth_counter.setText("인증되었습니다.");
                    join_authcode_button.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    send_code_button.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    e_validate = true;
                    send_code_button.setEnabled(false);
                    join_email.setEnabled(false);
                    join_authcode_button.setEnabled(true);
                    //spinner.setEnabled(false);
                    send_code_button.setText("인증완료");
                    return;
                }
                else {
                    Toast.makeText(getApplicationContext(), "잘못된 인증번호입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        cancel_button = findViewById( R.id.cancel_button);
        cancel_button.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

        //회원가입 버튼 클릭 시 수행
        join_button = findViewById( R.id.join_button );
        join_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String UserId = join_id.getText().toString();
                final String UserPwd = join_password.getText().toString();
                final String UserEmail = join_email.getText().toString();
                final String PassCk = join_pwck.getText().toString();


                //아이디 중복체크 했는지 확인
                if (!validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("중복된 아이디가 있는지 확인하세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                if(!e_validate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("이메일 인증을 진행해 주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                //한 칸이라도 입력 안했을 경우
                if (UserId.equals("") || UserPwd.equals("") || UserEmail.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );

                            //회원가입 성공시
                            if(UserPwd.equals(PassCk)) {
                                if (success) {
                                    Toast.makeText(getApplicationContext(), String.format("%s님 가입을 환영합니다.", UserId), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);

                                    //회원가입 실패시
                                } else {
                                    Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("비밀번호가 동일하지 않습니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                //서버로 Volley를 이용해서 요청
                RegisterRequest registerRequest = new RegisterRequest( UserId, UserPwd, UserEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue( RegisterActivity.this );
                queue.add( registerRequest );
            }
        });
    }
}