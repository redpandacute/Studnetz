package hirtz.florian.matura.ksa.studnetz.requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Florian Hirtz on 04.10.2018.
 */

public class BcryptRegisterRequest extends StringRequest {


    private static final String register_URL = "http://ef-informatik.umbach.ch/students/hirtzf/PHP/register_bcrypt.php";
    private Map<String, String> params;

    public BcryptRegisterRequest(String username, String name, String firstname, String school, int grade, String email, String password, Response.Listener<String> listener) {
        super(Request.Method.POST, register_URL, listener, null /*Errorlistener*/);
        params = new HashMap<>();
        params.put("user_username", username);
        params.put("user_name", name);
        params.put("user_firstname", firstname);
        params.put("user_school", school);
        params.put("user_grade", grade + "");
        params.put("user_email", email);

        params.put("user_password", password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
