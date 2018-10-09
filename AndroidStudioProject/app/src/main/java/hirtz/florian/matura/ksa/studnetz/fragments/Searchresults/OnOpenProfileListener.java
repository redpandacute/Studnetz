package hirtz.florian.matura.ksa.studnetz.fragments.Searchresults;

import android.view.View;

import hirtz.florian.matura.ksa.studnetz.activities.main.MainActivity;
import hirtz.florian.matura.ksa.studnetz.fragments.UserProfile.OpenProfileFragment;

/**
 * Created by Florian Hirtz on 12.08.2018.
 */

class OnOpenProfileListener implements View.OnClickListener {

    private ResultViewHolder mHolder;
    private SearchresultsFragment mFragment;
    private MainActivity mActivity;

    public OnOpenProfileListener(SearchresultsFragment mFragment, ResultViewHolder mHolder) {
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
        this.mHolder = mHolder;
    }

    @Override
    public void onClick(View view) {
        mActivity.setOpenprofileModel(mHolder.getModel());
        mActivity.setOpenprofileFragment(new OpenProfileFragment());
        mActivity.changeFragmentwithBackstack(mActivity.getOpenprofileFragment(), "search");
    }
}
