package org.techtown.apt_1025.Login;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EValidateRequest extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static  private String URL="http://ec2-3-35-217-139.ap-northeast-2.compute.amazonaws.com/je/EValidate.php";
    private Map<String, String> map;

    public EValidateRequest(String email, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);

        map = new HashMap<>();
        map.put("email", email);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}