package hirtz.florian.matura.ksa.studnetz.activities.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import hirtz.florian.matura.ksa.studnetz.R;
import hirtz.florian.matura.ksa.studnetz.activities.login.LoginActivity;
import hirtz.florian.matura.ksa.studnetz.requests.BcryptRegisterRequest;
import hirtz.florian.matura.ksa.studnetz.util.SchoolMapper;

public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.register_toolbar);
        toolbar.setTitle(R.string.register_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Spinner grade_sp = findViewById(R.id.registeract_grade_spinner);
        final Spinner school_sp = findViewById(R.id.registeract_school_spinner);

        new SchoolMapper(getBaseContext(), school_sp, grade_sp).startDisplay("schoollist.txt");

        final Button registerbutton_bu = findViewById(R.id.registeract_signup_button);


        registerbutton_bu.setOnClickListener(new BcryptOnRegisterListener());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                System.out.println("::BACK BUTTON::");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //OnResponseListener Register ------------------------------------------------------------------
    private class onResponseListener implements Response.Listener<String> {

        @Override
        public void onResponse(String response) {

            try {
                System.out.println("Register Response: " + response);
                JSONObject json_response = new JSONObject(response);
                boolean success = json_response.getBoolean("success");

                if (success) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class); //if success, change to login screen
                    RegisterActivity.this.startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Ooops, something went wrong with your registration, retry? Errorcodes:" + json_response.getString("error_log"), Toast.LENGTH_LONG).show();
                }                                                                                                                                                   // retry button.
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class BcryptOnRegisterListener implements View.OnClickListener {

        //Objects
        final EditText username_et = findViewById(R.id.registeract_username_edittext);
        final EditText name_et = findViewById(R.id.registeract_name_edittext);
        final EditText firstname_et = findViewById(R.id.registeract_firstname_edittext);
        final EditText email_et = findViewById(R.id.registeract_email_edittext);
        final EditText password_et = findViewById(R.id.registeract_password_edittext);
        final EditText confpassword_et = findViewById(R.id.registeract_confpassword_edittext);

        final Spinner grade_sp = findViewById(R.id.registeract_grade_spinner);
        final Spinner school_sp = findViewById(R.id.registeract_school_spinner);

        final CheckBox termsofservice_cb = findViewById(R.id.registeract_termsofservice_checkbox);

        @Override
        public void onClick(View view) {

            String username = username_et.getText().toString();
            String name = name_et.getText().toString();
            String firstname = firstname_et.getText().toString();
            String email = email_et.getText().toString();
            String password = password_et.getText().toString();
            String confpassword = confpassword_et.getText().toString();
            String school = "";

            if (password.equals(confpassword)) {
                if (termsofservice_cb.isChecked() && !username.isEmpty() && !name.isEmpty() && !firstname.isEmpty() && !email.isEmpty() && !password.isEmpty() && school_sp != null && school_sp.getSelectedItem() != null && school_sp.getSelectedItemPosition() != 0) {

                    school = school_sp.getSelectedItem().toString();
                    int grade = 0;

                    if(grade_sp.getSelectedItem() != null && grade_sp.getSelectedItem() != null) {
                        grade = Integer.parseInt(grade_sp.getSelectedItem().toString());
                    }

                    System.out.println("Creating request");
                    //Creating Request
                    BcryptRegisterRequest reg_request = new BcryptRegisterRequest(username, name, firstname, school, grade, email, password, new onResponseListener());
                    RequestQueue request_queue = Volley.newRequestQueue(RegisterActivity.this); //Makeing a Requestqueue
                    request_queue.add(reg_request);

                }
            }
        }
    }
}