package hirtz.florian.matura.ksa.studnetz.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flo on 28.01.2018.
 */

public class SearchRequest extends StringRequest{

    private static final String search_URL = "http://ef-informatik.umbach.ch/students/hirtzf/PHP/search_php_v3.php";
    private Map<String, String> params;

    public SearchRequest(int id, String name, String school, int grade, Map<String, Boolean> map, Response.Listener<String> listener) {
        super(Method.POST, search_URL, listener, null /*Errorlistener*/);
        params = new HashMap<>();
        params.put("user_name", name);
        params.put("user_id" , id + "");

        //TODO IMPLEMENT THE SCHOOL SPINNER STUFF FLO !!
        params.put("user_school", school);
        params.put("user_grade", grade + "");

        params.put("subj_german", (map.get("subj_german")? 1 : 0) + "");
        params.put("subj_spanish", (map.get("subj_spanish")? 1 : 0) + "");
        params.put("subj_english", (map.get("subj_english")? 1 : 0) + "");
        params.put("subj_french",(map.get("subj_french")? 1 : 0) + "");
        params.put("subj_biology",(map.get("subj_biology")? 1 : 0) + "");
        params.put("subj_maths", (map.get("subj_maths")? 1 : 0) + "");
        params.put("subj_chemistry", (map.get("subj_chemistry")? 1 : 0) + "");
        params.put("subj_music", (map.get("subj_music")? 1 : 0) + "");
        params.put("subj_physics", (map.get("subj_physics")? 1 : 0) + "");
    }

    @Override
    public Map<String, String> getParams() { return params; }
}
