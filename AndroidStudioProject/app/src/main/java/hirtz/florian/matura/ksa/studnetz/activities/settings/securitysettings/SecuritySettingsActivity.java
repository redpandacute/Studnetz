package hirtz.florian.matura.ksa.studnetz.activities.settings.securitysettings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import hirtz.florian.matura.ksa.studnetz.R;
import hirtz.florian.matura.ksa.studnetz.activities.settings.SettingsoverviewActivity;
import hirtz.florian.matura.ksa.studnetz.models.UserModel;
import hirtz.florian.matura.ksa.studnetz.requests.BcryptSaveSettingsRequest;
import hirtz.florian.matura.ksa.studnetz.util.JSONtoInfo;

public class SecuritySettingsActivity extends AppCompatActivity {

    private Bundle extras;
    private UserModel clientInfo;
    private EditText email_et, oldPW_et, newPW_et, confPW_et;
    private Button save_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_settings);

        Toolbar toolbar = findViewById(R.id.security_toolbar);
        toolbar.setTitle(R.string.securitySettings_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email_et = findViewById(R.id.secsettings_email_et);
        oldPW_et = findViewById(R.id.secsettings_oldpw_et);
        newPW_et = findViewById(R.id.secsettings_newpw_et);
        confPW_et = findViewById(R.id.secsettings_confpw_et);
        save_bt = findViewById(R.id.secsettings_save_bt);
        save_bt.setOnClickListener(new onSaveListener());

        extras = getIntent().getExtras();

        try {
            clientInfo = new JSONtoInfo(getBaseContext()).createNewItem(new JSONObject(extras.getString("clientInfo")));
            email_et.setText(clientInfo.getEmail());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(SecuritySettingsActivity.this, SettingsoverviewActivity.class);
                intent.putExtra("clientInfo", extras.getString("clientInfo"));
                startActivity(intent);
                System.out.println("::BACK BUTTON::");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class onSaveListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            String email = email_et.getText().toString();
            String oldPW = oldPW_et.getText().toString();
            String newPW = newPW_et.getText().toString();
            String confPW = confPW_et.getText().toString();

            if(!email.isEmpty() && oldPW.isEmpty() && newPW.isEmpty() && confPW.isEmpty() && email.contains(".") && email.contains("@")) {

                System.out.println("Making save request");

                BcryptSaveSettingsRequest save_request = new BcryptSaveSettingsRequest(clientInfo.getId(),
                        clientInfo.getFirstname(),
                        clientInfo.getName(),
                        email,
                        clientInfo.getSchool(),
                        clientInfo.getGrade(),
                        clientInfo.getDescription(),
                        clientInfo.getPasswordHash(),
                        clientInfo.isGerman(),
                        clientInfo.isSpanish(),
                        clientInfo.isEnglish(),
                        clientInfo.isFrench(),
                        clientInfo.isBiology(),
                        clientInfo.isChemistry(),
                        clientInfo.isMusic(),
                        clientInfo.isMaths(),
                        clientInfo.isPhysics(),
                        new SettingsoverviewActivity.onSaveResponseListener(getBaseContext(), 0));

                RequestQueue request_queue = Volley.newRequestQueue(SecuritySettingsActivity.this); //Request Queue
                request_queue.add(save_request);

            } else if(!email.isEmpty() && !oldPW.isEmpty() && !newPW.isEmpty() && !confPW.isEmpty() && (newPW.equals(confPW)) && email.contains(".") && email.contains("@")) {
                //WITH PW

                String oldPassword = oldPW_et.getText().toString();
                String newPassword = newPW_et.getText().toString();

                System.out.println("Making save request");

                BcryptSaveSettingsRequest save_request = new BcryptSaveSettingsRequest(clientInfo.getId(),
                        clientInfo.getFirstname(),
                        clientInfo.getName(),
                        email,
                        clientInfo.getSchool(),
                        clientInfo.getGrade(),
                        clientInfo.getDescription(),
                        clientInfo.getPasswordHash(),
                        oldPassword,
                        newPassword,
                        clientInfo.isGerman(),
                        clientInfo.isSpanish(),
                        clientInfo.isEnglish(),
                        clientInfo.isFrench(),
                        clientInfo.isBiology(),
                        clientInfo.isChemistry(),
                        clientInfo.isMusic(),
                        clientInfo.isMaths(),
                        clientInfo.isPhysics(),
                        new SettingsoverviewActivity.onSaveResponseListener(getBaseContext(), 0));

                RequestQueue request_queue = Volley.newRequestQueue(SecuritySettingsActivity.this); //Request Queue
                request_queue.add(save_request);
            }

        }
    }
}
