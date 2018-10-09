package hirtz.florian.matura.ksa.studnetz.fragments.Searchresults;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import hirtz.florian.matura.ksa.studnetz.activities.main.MainActivity;
import hirtz.florian.matura.ksa.studnetz.util.ProfilePictureLoader;

/**
 * Created by Florian Hirtz on 12.08.2018.
 */

class OnScrollResultsListener extends RecyclerView.OnScrollListener {

    private SearchresultsFragment mFragment;
    private MainActivity mActivity;
    private LinearLayoutManager mLayoutManager;

    public OnScrollResultsListener(LinearLayoutManager mLayoutManager, SearchresultsFragment mFragment) {
        this.mLayoutManager = mLayoutManager;
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int totalItemCount = mLayoutManager.getItemCount();

        int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
        int childCount = mLayoutManager.getChildCount();

        for(int loop = 0; loop < childCount; loop++) {
            ResultViewHolder holder = (ResultViewHolder) recyclerView.findViewHolderForAdapterPosition(firstVisibleItemPosition + loop);
            if(holder.getModel().getTempProfilePicturePath() == null && mFragment.getReadyState()) {
                if(totalItemCount - firstVisibleItemPosition - 1 >= mFragment.getHeapsize()) {
                    int amount = mFragment.getHeapsize();
                    int start = firstVisibleItemPosition + loop;
                    new ProfilePictureLoader(mFragment).load(start, amount, mActivity.getSearchResultDataset(), new OnProfilePicturesResponseListener(mFragment));
                    break;
                } else {
                    int amount = totalItemCount - firstVisibleItemPosition - loop;
                    int start = firstVisibleItemPosition + loop;
                    new ProfilePictureLoader(mFragment).load(start, amount, mActivity.getSearchResultDataset(), new OnProfilePicturesResponseListener(mFragment));
                    break;
                }
            }
        }
    }
}

