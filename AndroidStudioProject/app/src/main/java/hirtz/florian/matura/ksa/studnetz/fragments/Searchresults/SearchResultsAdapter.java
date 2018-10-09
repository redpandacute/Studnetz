package hirtz.florian.matura.ksa.studnetz.fragments.Searchresults;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.io.File;

import hirtz.florian.matura.ksa.studnetz.R;
import hirtz.florian.matura.ksa.studnetz.activities.main.MainActivity;
import hirtz.florian.matura.ksa.studnetz.models.ProfilePictureModel;
import hirtz.florian.matura.ksa.studnetz.models.UserModel;
import hirtz.florian.matura.ksa.studnetz.util.TempFileGenerator;

/**
 * Created by ingli on 12.08.2018.
 */

class SearchResultsAdapter extends RecyclerView.Adapter<ResultViewHolder> {

    private Bitmap placeholderBitmap;
    private SearchresultsFragment mFragment;
    private MainActivity mActivity;
    private UserModel[] mDataset;

    public SearchResultsAdapter(UserModel[] mDataset, SearchresultsFragment mFragment) {
        this.mDataset = mDataset;
        this.mFragment = mFragment;
        this.mActivity = (MainActivity) mFragment.getActivity();

        this.placeholderBitmap = new ProfilePictureModel(mActivity.getBaseContext(), new File(new TempFileGenerator().getTempFilePath(mActivity.getBaseContext(), "0"))).getImageBitmap();
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchresult_listelement, parent, false);
        ResultViewHolder viewHolder = new ResultViewHolder(view);
        viewHolder.getView().setOnClickListener(new OnOpenProfileListener(mFragment, viewHolder));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        UserModel model = mDataset[position];
        holder.validate(model);

        ImageView profilepicture_iv = holder.getView().findViewById(R.id.result_profilepicture_imageview);

        if(model.getTempProfilePicturePath() != null) {
            profilepicture_iv.setImageBitmap(new ProfilePictureModel(mActivity.getBaseContext(), new File(model.getTempProfilePicturePath())).getImageBitmap());
        } else {
            profilepicture_iv.setImageBitmap(placeholderBitmap);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public void refresh(int start, String[] paths) {
        for(int n = 0; n < paths.length; n++) {
            mDataset[start + n].setTempProfilePicturePath(paths[n]);
        }
    }
}
