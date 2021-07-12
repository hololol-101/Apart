package org.techtown.apt_1025.Login;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ModifypwRequest extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://ec2-3-35-217-139.ap-northeast-2.compute.amazonaws.com/je/Modifypw.php";
    private Map<String, String> map;
    //private Map<String, String>parameters;

    public ModifypwRequest(String mem_id, String mem_pw, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("mem_id", mem_id);
        map.put("mem_pw", mem_pw);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}