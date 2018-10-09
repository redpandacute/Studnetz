package hirtz.florian.matura.ksa.studnetz.fragments.Chatoverview;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.Map;

import hirtz.florian.matura.ksa.studnetz.R;
import hirtz.florian.matura.ksa.studnetz.activities.main.MainActivity;
import hirtz.florian.matura.ksa.studnetz.interfaces.LoaderFragment;
import hirtz.florian.matura.ksa.studnetz.models.ProfilePictureModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatoverviewFragment extends Fragment implements LoaderFragment {

    private RecyclerView mRecyclerView;
    private MainActivity mActivity;
    private boolean readyState;

    public static final int HEAPSIZE = 10;
    private ChatsValueEventListener mValueListener;

    public ChatoverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chatoverview, container, false);

        mRecyclerView = view.findViewById(R.id.chatoverview_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity.getBaseContext());
        linearLayoutManager.setStackFromEnd(false);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        Toolbar toolbar = view.findViewById(R.id.chatoverview_toolbar);
        toolbar.setTitle(R.string.chatoverview_title);
        mActivity.setSupportActionBar(toolbar);

        readyState = true;

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ChatoverviewAdapter mAdapter = new ChatoverviewAdapter(this);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.addOnScrollListener(new OnScrollListener(this, (LinearLayoutManager) mRecyclerView.getLayoutManager()));
        mValueListener = new ChatsValueEventListener(this);
        mActivity.getMainProfileFirebaseRef().child("chats/").addValueEventListener(new ChatsValueEventListener(this));
    }


    @Override
    public void onStop() {
        super.onStop();
        mActivity.getMainProfileFirebaseRef().child("chats/").removeEventListener(mValueListener);
    }


    @Override
    public void onPause() {
        super.onPause();
        mActivity.getMainProfileFirebaseRef().child("chats/").removeEventListener(mValueListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.getMainProfileFirebaseRef().child("chats/").addValueEventListener(mValueListener);
    }

    public void refreshList(String[] paths, int start, int amount) {
        for(int n = 0; n < amount; n++) {
            OpenChatViewHolder holder = (OpenChatViewHolder) mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(start + n));
            holder.getUserModel().setTempProfilePicturePath(paths[n]);
            holder.setProfilePicture(new ProfilePictureModel(mActivity.getBaseContext(), new File(paths[n])));
        }
    }


    @Override
    public boolean getReadyState() {
        return this.readyState;
    }

    @Override
    public void setReadyState(boolean readyState) {
        this.readyState = readyState;
    }

    public Map<Integer, OpenChatModel> getDataset() {
        return mActivity.getOpenChatsDataset();
    }

    public void setDataset(Map<Integer, OpenChatModel> mDataset) {
        mActivity.setOpenChatsDataset(mDataset);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setPaths(Map<Integer, String> map) {
        mActivity.setOpenChatsPaths(map);
    }

    public Map<Integer,String> getPaths() {
        return mActivity.getOpenChatsPaths();
    }
}
