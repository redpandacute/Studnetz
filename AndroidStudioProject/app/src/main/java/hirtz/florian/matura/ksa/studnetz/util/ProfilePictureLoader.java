package hirtz.florian.matura.ksa.studnetz.util;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import hirtz.florian.matura.ksa.studnetz.activities.main.MainActivity;
import hirtz.florian.matura.ksa.studnetz.interfaces.LoaderFragment;
import hirtz.florian.matura.ksa.studnetz.interfaces.ProfilePicturesOnResponseListener;
import hirtz.florian.matura.ksa.studnetz.models.UserModel;
import hirtz.florian.matura.ksa.studnetz.requests.SmallProfilePicturesRequest;


/**
 * Created by Florian Hirtz on 13.08.2018.
 */

public class ProfilePictureLoader {

    private LoaderFragment mFragment;
    private MainActivity mActivity;

    public ProfilePictureLoader(LoaderFragment mFragment) {
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
    }


    public void load(int start, int amount, UserModel[] mDataset, ProfilePicturesOnResponseListener mResponseListener) {
        int[] ids = new int[amount];
        mFragment.setReadyState(false);
        mResponseListener.setRange(start, amount);

        for(int n = start; n < amount; n++) {
            ids[n] = mDataset[n].getId();
        }

        SmallProfilePicturesRequest request = new SmallProfilePicturesRequest(ids, mResponseListener);
        RequestQueue queue = Volley.newRequestQueue(mActivity.getBaseContext());
        queue.add(request);
    }
}
