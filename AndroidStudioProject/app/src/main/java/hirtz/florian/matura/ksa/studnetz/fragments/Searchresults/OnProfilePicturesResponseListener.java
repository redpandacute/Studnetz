package hirtz.florian.matura.ksa.studnetz.fragments.Searchresults;


import org.json.JSONArray;
import org.json.JSONObject;

import hirtz.florian.matura.ksa.studnetz.activities.main.MainActivity;
import hirtz.florian.matura.ksa.studnetz.interfaces.ProfilePicturesOnResponseListener;
import hirtz.florian.matura.ksa.studnetz.util.TempFileGenerator;

/**
 * Created by ingli on 13.08.2018.
 */

public class OnProfilePicturesResponseListener implements ProfilePicturesOnResponseListener {

    private MainActivity mActivity;
    private SearchresultsFragment mFragment;
    private int start, amount;

    public OnProfilePicturesResponseListener(SearchresultsFragment mFragment) {
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
    }

    @Override
    public void setRange(int start, int amount) {
        this.start = start;
        this.amount = amount;
    }

    @Override
    public void onResponse(String response) {
        try {
            System.out.println(response);
            JSONObject jsn = new JSONObject(response);
            if(jsn.getBoolean("success")) {
                JSONArray data = jsn.getJSONArray("data");
                String[] paths = new String[data.length()];
                TempFileGenerator gen = new TempFileGenerator();
                for(int n = 0; n < data.length(); n++) {
                    paths[n] = gen.getTempFilePath(mActivity.getBaseContext(), data.getJSONObject(n).getString("blob_profilepicture_small"));
                }
                mActivity.refreshDataset(start, amount, paths);
                mFragment.refreshList();
                mFragment.setReadyState(true);
            } else {
                System.out.println("::ERROR WITH REQUESTING IDS:: ");
                System.out.println(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
