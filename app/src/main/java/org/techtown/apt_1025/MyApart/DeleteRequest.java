package org.techtown.apt_1025.MyApart;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DeleteRequest extends StringRequest {
    final static private String URL = "http://3.35.217.139/my_delete_bm.php";
    private Map<String, String> parameters;

    public DeleteRequest(String total_address, int u_id, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("total_address", total_address);
        parameters.put("u_id", u_id+"");
    }

    @Override
    public Map <String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
