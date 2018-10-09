package hirtz.florian.matura.ksa.studnetz.fragments.UserProfile;

import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import hirtz.florian.matura.ksa.studnetz.activities.main.MainActivity;
import hirtz.florian.matura.ksa.studnetz.fragments.Chat.OpenChatFragment;
import hirtz.florian.matura.ksa.studnetz.models.ChatModel;
import hirtz.florian.matura.ksa.studnetz.models.UserModel;

/**
 * Created by ingli on 12.08.2018.
 */

class OnOpenChatListener implements View.OnClickListener {

    private UserModel mMainprofileModel, mOpenuserModel;
    private OpenProfileFragment mFragment;
    private MainActivity mActivity;

    public OnOpenChatListener(OpenProfileFragment mFragment) {
        this.mFragment = mFragment;
        this.mActivity = (MainActivity)mFragment.getActivity();
        this.mOpenuserModel = mActivity.getOpenprofileModel();
        this.mMainprofileModel = mActivity.getMainprofileModel();
    }

    @Override
    public void onClick(View view) {

        //DatabaseReference databaseSenderReference = FirebaseDatabase.getInstance().getReference(String.format("Users/%d/%d>>%d", mMainprofileModel.getId(), mMainprofileModel.getId(), mMainprofileModel.getId()));
        //DatabaseReference databaseReceiverReference = FirebaseDatabase.getInstance().getReference(String.format("Users/%d/%d>>%d", mOpenuserModel.getId(), mOpenuserModel.getId(), mOpenuserModel.getId()));
        DatabaseReference chatsRef = FirebaseDatabase.getInstance().getReference("Chats");

        chatsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (dataSnapshot.hasChild(String.format("%d->%d", mMainprofileModel.getId(), mOpenuserModel.getId()))) {
                    DatabaseReference databaseSenderReference = mActivity.getMainProfileFirebaseRef().child(String.format("chats/%d->%d", mMainprofileModel.getId(), mOpenuserModel.getId()));
                    DatabaseReference databaseReceiverReference = FirebaseDatabase.getInstance().getReference(String.format("Users/%d/chats/%d->%d", mOpenuserModel.getId(), mMainprofileModel.getId(), mOpenuserModel.getId()));
                    DatabaseReference openChatReference = FirebaseDatabase.getInstance().getReference(String.format("Chats/%d->%d", mMainprofileModel.getId(), mOpenuserModel.getId()));

                    mActivity.setOpenChat(new ChatModel(mMainprofileModel, mOpenuserModel, databaseSenderReference, databaseReceiverReference, openChatReference));
                    mActivity.setFragment(new OpenChatFragment());

                } else if (dataSnapshot.hasChild(String.format("%d->%d", mOpenuserModel.getId(), mMainprofileModel.getId()))) {
                    DatabaseReference databaseSenderReference = mActivity.getMainProfileFirebaseRef().child(String.format("chats/%d->%d", mMainprofileModel.getId(), mOpenuserModel.getId()));
                    DatabaseReference databaseReceiverReference = FirebaseDatabase.getInstance().getReference(String.format("Users/%d/chats/%d->%d", mOpenuserModel.getId(), mMainprofileModel.getId(), mOpenuserModel.getId()));
                    DatabaseReference openChatReference = FirebaseDatabase.getInstance().getReference(String.format("Chats/%d->%d", mOpenuserModel.getId(), mMainprofileModel.getId()));

                    mActivity.setOpenChat(new ChatModel(mMainprofileModel, mOpenuserModel, databaseSenderReference, databaseReceiverReference, openChatReference));
                    mActivity.setFragment(new OpenChatFragment());
                } else {

                    DatabaseReference databaseSenderReference = mActivity.getMainProfileFirebaseRef().child(String.format("chats/%d->%d", mMainprofileModel.getId(), mOpenuserModel.getId()));
                    DatabaseReference databaseReceiverReference = FirebaseDatabase.getInstance().getReference(String.format("Users/%d/chats/%d->%d", mOpenuserModel.getId(), mMainprofileModel.getId(), mOpenuserModel.getId()));

                    databaseSenderReference.child("receiverID").setValue(mOpenuserModel.getId());
                    try {
                        databaseSenderReference.child("receiverJSON").setValue(mActivity.getOpenprofileModel().getCleanJSON());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    databaseSenderReference.child("senderRef").setValue(String.format("Users/%d/chats/%d->%d", mMainprofileModel.getId(), mMainprofileModel.getId(), mOpenuserModel.getId()));
                    databaseSenderReference.child("receiverRef").setValue(String.format("Users/%d/chats/%d->%d", mOpenuserModel.getId(), mMainprofileModel.getId(), mOpenuserModel.getId()));
                    databaseSenderReference.child("chatRef").setValue(String.format("Chats/%d->%d", mMainprofileModel.getId(), mOpenuserModel.getId()));
                    databaseSenderReference.child("latestMessage").setValue("");
                    databaseSenderReference.child("latestMessageTime").setValue("");
                    databaseSenderReference.child("read").setValue(1);

                    databaseReceiverReference.child("receiverID").setValue(mMainprofileModel.getId());
                    try {
                        databaseReceiverReference.child("receiverJSON").setValue(mActivity.getMainprofileModel().getCleanJSON());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    databaseReceiverReference.child("senderRef").setValue(String.format("Users/%d/chats/%d->%d", mOpenuserModel.getId(), mMainprofileModel.getId(), mOpenuserModel.getId()));
                    databaseReceiverReference.child("receiverRef").setValue(String.format("Users/%d/chats/%d->%d", mMainprofileModel.getId(), mMainprofileModel.getId(), mOpenuserModel.getId()));
                    databaseReceiverReference.child("chatRef").setValue(String.format("Chats/%d->%d", mMainprofileModel.getId(), mOpenuserModel.getId()));
                    databaseReceiverReference.child("latestMessage").setValue("");
                    databaseReceiverReference.child("latestMessageTime").setValue("");
                    databaseReceiverReference.child("read").setValue(0);

                    DatabaseReference openChatReference = FirebaseDatabase.getInstance().getReference(String.format("Chats/%d->%d", mMainprofileModel.getId(), mOpenuserModel.getId()));

                    mActivity.setOpenChat(new ChatModel(mMainprofileModel, mOpenuserModel, databaseSenderReference, databaseReceiverReference, openChatReference));
                    mActivity.setFragment(new OpenChatFragment());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
