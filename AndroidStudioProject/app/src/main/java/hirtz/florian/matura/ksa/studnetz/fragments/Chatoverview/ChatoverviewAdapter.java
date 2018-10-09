package hirtz.florian.matura.ksa.studnetz.fragments.Chatoverview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.Map;

import hirtz.florian.matura.ksa.studnetz.R;
import hirtz.florian.matura.ksa.studnetz.models.ProfilePictureModel;

/**
 * Created by ingli on 19.08.2018.
 */

class ChatoverviewAdapter extends RecyclerView.Adapter<OpenChatViewHolder>{

    private ChatoverviewFragment mFragment;
    private Map<Integer, OpenChatModel> mDataset;

    public ChatoverviewAdapter(ChatoverviewFragment mFragment) {
        this.mFragment = mFragment;
        this.mDataset = mFragment.getDataset();
    }

    @Override
    public OpenChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.openchat_listelement, parent, false);
        OpenChatViewHolder viewHolder = new OpenChatViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OpenChatViewHolder holder, int position) {
        OpenChatModel model = mDataset.get(mDataset.keySet().toArray()[position]);
        holder.validate(model);
        holder.getView().setOnClickListener(new OnOpenChatListener(mFragment, model));
        holder.setProfilePicture(new ProfilePictureModel(mFragment.getActivity().getBaseContext(), new File(model.getUserModel().getTempProfilePicturePath())));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
