package hirtz.florian.matura.ksa.studnetz.fragments.UserProfile;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import hirtz.florian.matura.ksa.studnetz.activities.main.MainActivity;
import hirtz.florian.matura.ksa.studnetz.util.TempFileGenerator;

/**
 * Created by Florian Hirtz on 12.08.2018.
 */

class OnBigPictureResponseListener implements Response.Listener<String> {

    private OpenProfileFragment mFragment;
    private MainActivity mActivity;

    public OnBigPictureResponseListener(OpenProfileFragment mFragment) {
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
    }

    @Override
    public void onResponse(String response) {

        try {

            JSONObject jsn = new JSONObject(response);
            boolean success = jsn.getBoolean("success");

            if (success) {
                String tempPath = new TempFileGenerator().getTempFilePath(mActivity.getBaseContext(), jsn.getString("blob_profilepicture_big"));
                mFragment.validate(tempPath);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
