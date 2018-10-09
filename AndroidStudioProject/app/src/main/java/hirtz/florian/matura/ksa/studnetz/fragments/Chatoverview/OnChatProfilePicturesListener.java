package hirtz.florian.matura.ksa.studnetz.fragments.Chatoverview;


import org.json.JSONArray;
import org.json.JSONObject;

import hirtz.florian.matura.ksa.studnetz.activities.main.MainActivity;
import hirtz.florian.matura.ksa.studnetz.interfaces.ProfilePicturesOnResponseListener;
import hirtz.florian.matura.ksa.studnetz.models.UserModel;
import hirtz.florian.matura.ksa.studnetz.util.TempFileGenerator;

/**
 * Created by ingli on 16.08.2018.
 */

public class OnChatProfilePicturesListener implements ProfilePicturesOnResponseListener {

    private UserModel[] mDataset;
    private int start, amount;
    private ChatoverviewFragment mFragment;
    private MainActivity mActivity;

    public OnChatProfilePicturesListener(ChatoverviewFragment mFragment, UserModel[] mDataset) {
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
        this.mDataset = mDataset;
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
                TempFileGenerator gen = new TempFileGenerator();
                for(int n = 0; n < data.length(); n++) {

                    mFragment.getPaths().put(
                            mDataset[n].getId(),
                            gen.getTempFilePath(mActivity.getBaseContext(), data.getJSONObject(n).getString("blob_profilepicture_small"))
                    );

                    mFragment.getDataset().get(mDataset[n].getId()).getUserModel().setTempProfilePicturePath(mFragment.getPaths().get(mDataset[n].getId()));
                }

                mFragment.getRecyclerView().getAdapter().notifyDataSetChanged();
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
