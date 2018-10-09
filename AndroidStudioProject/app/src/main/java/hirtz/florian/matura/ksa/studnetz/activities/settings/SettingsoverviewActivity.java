package hirtz.florian.matura.ksa.studnetz.activities.settings;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import hirtz.florian.matura.ksa.studnetz.R;
import hirtz.florian.matura.ksa.studnetz.activities.main.MainActivity;
import hirtz.florian.matura.ksa.studnetz.activities.settings.profilesettings.ProfileSettingsActivity;
import hirtz.florian.matura.ksa.studnetz.activities.settings.securitysettings.SecuritySettingsActivity;
import hirtz.florian.matura.ksa.studnetz.util.TempFileGenerator;

public class SettingsoverviewActivity extends AppCompatActivity {

    private String[] categories = new String[2];
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingsoverview);

        Toolbar toolbar = findViewById(R.id.settingsOverview_toolbar);
        toolbar.setTitle(R.string.settingsOverview_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext = getBaseContext();
        categories[0] = getString(R.string.profileSettings_cat);
        categories[1] = getString(R.string.securitySettings_cat);

        ListView list = findViewById(R.id.settingsOverview_settingslist);
        list.setAdapter(new settingsAdapter());
        list.setOnItemClickListener(new onItemSelectedListener());
    }


    class settingsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return categories.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View item = View.inflate(mContext, R.layout.settingscategory_listelement, null);
            TextView category = item.findViewById(R.id.settingscategory_textview);
            category.setText(categories[i]);
            return item;
        }
    }

    class onItemSelectedListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String selected = categories[i];
            if(selected.matches(categories[0])) {
                Intent intent = new Intent(mContext, ProfileSettingsActivity.class);
                intent.putExtra("clientInfo", getIntent().getExtras().getString("clientInfo"));
                startActivity(intent);
            } else if(selected.matches(categories[1])) {
                Intent intent = new Intent(mContext, SecuritySettingsActivity.class);
                intent.putExtra("clientInfo", getIntent().getExtras().getString("clientInfo"));
                startActivity(intent);
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                //Intent intent = new Intent(SettingsoverviewActivity.this, MainpageActivity.class);
                Intent intent = new Intent(SettingsoverviewActivity.this, MainActivity.class);
                intent.putExtra("clientInfo", getIntent().getExtras().getString("clientInfo"));
                startActivity(intent);
                System.out.println("::BACK BUTTON::");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class onSaveResponseListener implements Response.Listener<String> {

        private Context mContext;
        private int responseCode;

        public onSaveResponseListener(Context mContext, int responseCode) {
            this.mContext = mContext;
            this.responseCode = responseCode;
        }

        @Override
        public void onResponse(String response) {
            try {
                System.out.println("CODE ONE::" + response);
                JSONObject JSON = new JSONObject(response);
                boolean success = JSON.getBoolean("success");
                if(success && responseCode == 0) {
                    String tempPath = new TempFileGenerator().getTempFilePath(mContext, JSON.getString("blob_profilepicture_big"));
                    JSON.remove("blob_profilepicture_big");
                    JSON.put("temp_profilepicture_path", tempPath);
                    Intent settingsOverview = new Intent(mContext, SettingsoverviewActivity.class);
                    settingsOverview.putExtra("clientInfo", JSON.toString());
                    mContext.startActivity(settingsOverview);
                } else if(success && responseCode == 1) {
                    String tempPath = new TempFileGenerator().getTempFilePath(mContext, JSON.getString("blob_profilepicture_big"));
                    JSON.remove("blob_profilepicture_big");
                    JSON.put("temp_profilepicture_path", tempPath);
                    Intent profileSettings = new Intent(mContext, ProfileSettingsActivity.class);
                    profileSettings.putExtra("clientInfo", JSON.toString());
                    mContext.startActivity(profileSettings);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
