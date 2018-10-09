package hirtz.florian.matura.ksa.studnetz.util;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import hirtz.florian.matura.ksa.studnetz.models.UserModel;

/**
 * Created by Florian Hirtz on 18.02.2018.
 */

public class JSONtoInfo {

    private Context context;

    public JSONtoInfo(Context context) {
        this.context = context;
    }


    public UserModel createNewItem(JSONObject json) {

        try {
            String user_name = json.getString("user_name");
            String user_firstname = json.getString("user_firstname");
            String user_school = json.getString("user_school");
            String user_username = json.getString("user_username");

            String user_description = "No description";
            try {
                user_description = json.getString("user_description");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("NOTE: No description");
            }

            int user_grade = json.getInt("user_grade");


            boolean subj_maths = 0 != json.getInt("subj_maths");
            boolean subj_german = 0 != json.getInt("subj_german");
            boolean subj_french = 0 != json.getInt("subj_french");
            boolean subj_spanish = 0 != json.getInt("subj_spanish");
            boolean subj_physics = 0 != json.getInt("subj_physics");
            boolean subj_chemistry = 0 != json.getInt("subj_chemistry");
            boolean subj_biology = 0 != json.getInt("subj_biology");
            boolean subj_music = 0 != json.getInt("subj_music");
            boolean subj_english = 0 != json.getInt("subj_english");

            String temp_profilepicture_path = null;

            try {
                temp_profilepicture_path = json.getString("temp_profilepicture_path");
            } catch(Exception e) {}

            UserModel item = null;
            try {
                String user_email = json.getString("user_email");
                String hash_password = json.getString("hash_password");
                String hash_salt = json.getString("hash_salt");

                item = new UserModel(
                        json.getInt("user_id"), user_username, user_name, user_firstname, user_school, user_grade, user_description, user_email,
                        subj_french, subj_spanish, subj_music, subj_english, subj_chemistry, subj_biology, subj_maths, subj_german, subj_physics,
                        hash_password, hash_salt,
                        temp_profilepicture_path, json.toString());
            } catch (Exception e) {
                item = new UserModel(
                        json.getInt("user_id"), user_username, user_name, user_firstname, user_school, user_grade, user_description,
                        subj_french, subj_spanish, subj_music, subj_english, subj_chemistry, subj_biology, subj_maths, subj_german, subj_physics,
                        temp_profilepicture_path, json.toString());
            }

            return item;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}