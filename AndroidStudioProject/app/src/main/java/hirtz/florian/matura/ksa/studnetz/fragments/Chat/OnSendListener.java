package hirtz.florian.matura.ksa.studnetz.fragments.Chat;

import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;

import hirtz.florian.matura.ksa.studnetz.R;
import hirtz.florian.matura.ksa.studnetz.activities.main.MainActivity;
import hirtz.florian.matura.ksa.studnetz.models.ChatModel;

/**
 * Created by Florian Hirtz on 12.08.2018.
 */

class OnSendListener implements View.OnClickListener {

    private MainActivity mActivity;
    private View view;
    private OpenChatFragment mFragment;
    private ChatModel mChatModel;

    public OnSendListener(View view, OpenChatFragment mFragment) {
        this.view = view;
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
        this.mChatModel = mActivity.getOpenChat();
    }

    @Override
    public void onClick(View view) {

        final EditText textInput = this.view.findViewById(R.id.chat_message_edittext);

        final String messageText = textInput.getText().toString().trim();
        final String messageUser = mChatModel.getMainprofileModel().getFirstname() + " " + mChatModel.getMainprofileModel().getName();
        final String messageTime = new SimpleDateFormat("MMM dd - hh:mm:dd").format(System.currentTimeMillis()).toString();

        if (!messageText.isEmpty()) {
            final DatabaseReference newPost = mChatModel.getChatRef().child("Messages").push();
            newPost.child("messageUserId").setValue(mChatModel.getMainprofileModel().getId());
            newPost.child("messageUser").setValue(messageUser);
            newPost.child("messageText").setValue(messageText);
            newPost.child("messageTime").setValue(messageTime);


            final DatabaseReference syncSender = mChatModel.getMainprofileRef();
            syncSender.child("latestMessage").setValue(messageText);
            syncSender.child("latestMessageTime").setValue(messageTime);
            syncSender.child("read").setValue(1);
            final DatabaseReference syncRec = mChatModel.getUserprofileRef();
            syncRec.child("latestMessage").setValue(messageText);
            syncRec.child("latestMessageTime").setValue(messageTime);
            syncRec.child("read").setValue(0);

            textInput.setText("");
        }
    }
}
