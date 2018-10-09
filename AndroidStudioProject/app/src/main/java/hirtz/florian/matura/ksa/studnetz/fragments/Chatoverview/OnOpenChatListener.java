package hirtz.florian.matura.ksa.studnetz.fragments.Chatoverview;

import android.view.View;

import com.google.firebase.database.DatabaseReference;

import hirtz.florian.matura.ksa.studnetz.activities.main.MainActivity;
import hirtz.florian.matura.ksa.studnetz.fragments.Chat.OpenChatFragment;
import hirtz.florian.matura.ksa.studnetz.models.ChatModel;


/**
 * Created by Florian Hirtz on 20.08.2018.
 */

class OnOpenChatListener implements View.OnClickListener {

    private OpenChatModel mModel;
    private MainActivity mActivity;
    private ChatoverviewFragment mFragment;

    public OnOpenChatListener(ChatoverviewFragment mFragment, OpenChatModel mModel) {
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
        this.mModel = mModel;
    }

    @Override
    public void onClick(View view) {
        DatabaseReference senderRef = mModel.getSenderRef();
        DatabaseReference receiverRef = mModel.getReceiverRef();
        DatabaseReference chatRef = mModel.getChatRef();
        mActivity.setOpenChat(new ChatModel(mActivity.getMainprofileModel(), mModel.getUserModel(), senderRef, receiverRef, chatRef));
        mActivity.setFragment(new OpenChatFragment());
        mFragment.onPause();
    }
}
