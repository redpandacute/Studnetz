package hirtz.florian.matura.ksa.studnetz.requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Florian Hirtz on 06.10.2018.
 */

public class BcryptSaveSettingsRequest extends StringRequest {

    private static final String savesettings_pw_URL = "http://ef-informatik.umbach.ch/students/hirtzf/PHP/final/savesettings_bcrypt.php";
    private Map<String, String> params;

    public BcryptSaveSettingsRequest(int id,
                                      String firstname,
                                      String name,
                                      String email,
                                      String school,
                                      int grade,
                                      String description,
                                      String hash_old_password,
                                      String oldpassword,
                                      String newpassword,
                                      boolean german,
                                      boolean spanish,
                                      boolean english,
                                      boolean french,
                                      boolean biology,
                                      boolean chemistry,
                                      boolean music,
                                      boolean maths,
                                      boolean physics,
                                      Response.Listener<String> listener) {

        super(Request.Method.POST, savesettings_pw_URL, listener, null /*Errorlistener*/);

        params = new HashMap<>();
        params.put("user_id", id + "");
        params.put("user_name", name);
        params.put("user_firstname", firstname);
        params.put("user_school", school);
        params.put("user_grade", grade + "");
        params.put("user_description", description);
        params.put("user_email", email);
        params.put("hash_old_password", hash_old_password);
        params.put("old_password", oldpassword);
        params.put("new_password", newpassword);

        params.put("subj_german",  ((german) ? 1 : 0) + "");
        params.put("subj_spanish", ((spanish) ? 1 : 0) + "");
        params.put("subj_english", ((english) ? 1 : 0) + "");
        params.put("subj_french", ((french) ? 1 : 0) + "");
        params.put("subj_biology", ((biology) ? 1 : 0) + "");
        params.put("subj_chemistry", ((chemistry) ? 1 : 0) + "");
        params.put("subj_music", ((music) ? 1 : 0) + "");
        params.put("subj_maths", ((maths) ? 1 : 0) + "");
        params.put("subj_physics", ((physics) ? 1 : 0) + "");
        params.put("bool_image_changed", 0 + "");
        params.put("bool_password_changed", 1 + "");
    }

    public BcryptSaveSettingsRequest(int id,
                                     String firstname,
                                     String name,
                                     String email,
                                     String school,
                                     int grade,
                                     String description,
                                     String hash_old_password,
                                     boolean german,
                                     boolean spanish,
                                     boolean english,
                                     boolean french,
                                     boolean biology,
                                     boolean chemistry,
                                     boolean music,
                                     boolean maths,
                                     boolean physics,
                                     Response.Listener<String> listener) {

        super(Request.Method.POST, savesettings_pw_URL, listener, null /*Errorlistener*/);

        params = new HashMap<>();
        params.put("user_id", id + "");
        params.put("user_name", name);
        params.put("user_firstname", firstname);
        params.put("user_school", school);
        params.put("user_grade", grade + "");
        params.put("user_description", description);
        params.put("user_email", email);
        params.put("hash_old_password", hash_old_password);

        params.put("subj_german",  ((german) ? 1 : 0) + "");
        params.put("subj_spanish", ((spanish) ? 1 : 0) + "");
        params.put("subj_english", ((english) ? 1 : 0) + "");
        params.put("subj_french", ((french) ? 1 : 0) + "");
        params.put("subj_biology", ((biology) ? 1 : 0) + "");
        params.put("subj_chemistry", ((chemistry) ? 1 : 0) + "");
        params.put("subj_music", ((music) ? 1 : 0) + "");
        params.put("subj_maths", ((maths) ? 1 : 0) + "");
        params.put("subj_physics", ((physics) ? 1 : 0) + "");
        params.put("bool_image_changed", 0 + "");
        params.put("bool_password_changed", 0 + "");
    }

    public BcryptSaveSettingsRequest(int id,
                                     String firstname,
                                     String name,
                                     String email,
                                     String school,
                                     int grade,
                                     String description,
                                     String hash_old_password,
                                     boolean german,
                                     boolean spanish,
                                     boolean english,
                                     boolean french,
                                     boolean biology,
                                     boolean chemistry,
                                     boolean music,
                                     boolean maths,
                                     boolean physics,
                                     String blob_profilepicture_big,
                                     String blob_profilepicture_small,
                                     Response.Listener<String> listener) {

        super(Request.Method.POST, savesettings_pw_URL, listener, null /*Errorlistener*/);

        params = new HashMap<>();
        params.put("user_id", id + "");
        params.put("user_name", name);
        params.put("user_firstname", firstname);
        params.put("user_school", school);
        params.put("user_grade", grade + "");
        params.put("user_description", description);
        params.put("user_email", email);
        params.put("hash_old_password", hash_old_password);

        params.put("subj_german",  ((german) ? 1 : 0) + "");
        params.put("subj_spanish", ((spanish) ? 1 : 0) + "");
        params.put("subj_english", ((english) ? 1 : 0) + "");
        params.put("subj_french", ((french) ? 1 : 0) + "");
        params.put("subj_biology", ((biology) ? 1 : 0) + "");
        params.put("subj_chemistry", ((chemistry) ? 1 : 0) + "");
        params.put("subj_music", ((music) ? 1 : 0) + "");
        params.put("subj_maths", ((maths) ? 1 : 0) + "");
        params.put("subj_physics", ((physics) ? 1 : 0) + "");
        params.put("bool_image_changed", 1 + "");
        params.put("bool_password_changed", 0 + "");

        params.put("blob_profilepicture_small", blob_profilepicture_small);
        params.put("blob_profilepicture_big", blob_profilepicture_big);
    }

    public BcryptSaveSettingsRequest(int id,
                                     String firstname,
                                     String name,
                                     String email,
                                     String school,
                                     int grade,
                                     String description,
                                     String hash_old_password,
                                     String oldpassword,
                                     String newpassword,
                                     boolean german,
                                     boolean spanish,
                                     boolean english,
                                     boolean french,
                                     boolean biology,
                                     boolean chemistry,
                                     boolean music,
                                     boolean maths,
                                     boolean physics,
                                     String blob_profilepicture_big,
                                     String blob_profilepicture_small,
                                     Response.Listener<String> listener) {

        super(Request.Method.POST, savesettings_pw_URL, listener, null /*Errorlistener*/);

        params = new HashMap<>();
        params.put("user_id", id + "");
        params.put("user_name", name);
        params.put("user_firstname", firstname);
        params.put("user_school", school);
        params.put("user_grade", grade + "");
        params.put("user_description", description);
        params.put("user_email", email);
        params.put("hash_old_password", hash_old_password);
        params.put("old_password", oldpassword);
        params.put("new_password", newpassword);

        params.put("subj_german",  ((german) ? 1 : 0) + "");
        params.put("subj_spanish", ((spanish) ? 1 : 0) + "");
        params.put("subj_english", ((english) ? 1 : 0) + "");
        params.put("subj_french", ((french) ? 1 : 0) + "");
        params.put("subj_biology", ((biology) ? 1 : 0) + "");
        params.put("subj_chemistry", ((chemistry) ? 1 : 0) + "");
        params.put("subj_music", ((music) ? 1 : 0) + "");
        params.put("subj_maths", ((maths) ? 1 : 0) + "");
        params.put("subj_physics", ((physics) ? 1 : 0) + "");
        params.put("bool_image_changed", 1 + "");
        params.put("bool_password_changed", 1 + "");

        params.put("blob_profilepicture_small", blob_profilepicture_small);
        params.put("blob_profilepicture_big", blob_profilepicture_big);
    }

    @Override
    public Map<String, String> getParams() { return params; }
}
