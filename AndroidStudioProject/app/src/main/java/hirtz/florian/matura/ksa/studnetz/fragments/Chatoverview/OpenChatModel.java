package hirtz.florian.matura.ksa.studnetz.fragments.Chatoverview;

import android.content.Context;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import hirtz.florian.matura.ksa.studnetz.models.UserModel;
import hirtz.florian.matura.ksa.studnetz.util.JSONtoInfo;

/**
 * Created by ingli on 19.08.2018.
 */

public class OpenChatModel {

    private UserModel mUserModel;
    private String mLatestMessage, mProfilePicturePath, mTime;
    private DatabaseReference mReceiverReference, mChatRef, mSenderReference;
    private boolean read;

    public OpenChatModel(Context mContext, DataSnapshot mSnapshot) throws JSONException {
        this.mUserModel = new JSONtoInfo(mContext).createNewItem(new JSONObject(mSnapshot.child("receiverJSON").getValue().toString()));
        this.mLatestMessage = mSnapshot.child("latestMessage").getValue().toString();
        this.mTime = mSnapshot.child("latestMessageTime").getValue().toString();
        this.mReceiverReference = FirebaseDatabase.getInstance().getReference(mSnapshot.child("receiverRef").getValue().toString());
        this.mChatRef = FirebaseDatabase.getInstance().getReference(mSnapshot.child("chatRef").getValue().toString());
        this.mSenderReference = FirebaseDatabase.getInstance().getReference(mSnapshot.child("senderRef").getValue().toString());
        if(Integer.parseInt(mSnapshot.child("read").getValue().toString()) == 1) { this.read = true; } else { this.read = false; }
    }

    public UserModel getUserModel() {
        return mUserModel;
    }

    public void setUserModel(UserModel mUserModel) {
        this.mUserModel = mUserModel;
    }

    public String getLatestMessage() {
        return mLatestMessage;
    }

    public void setLatestMessage(String mLatestMessage) {
        this.mLatestMessage = mLatestMessage;
    }

    public String getProfilePicturePath() {
        return mProfilePicturePath;
    }

    public void setProfilePicturePath(String mProfilePicturePath) {
        this.mProfilePicturePath = mProfilePicturePath;
    }

    public DatabaseReference getReceiverRef() {
        return mReceiverReference;
    }

    public void setReceiverRef(DatabaseReference mReceiverReference) {
        this.mReceiverReference = mReceiverReference;
    }

    public DatabaseReference getChatRef() {
        return this.mChatRef;
    }

    public void setChatRef(DatabaseReference mChatRef) {
        this.mChatRef = mChatRef;
    }

    public DatabaseReference getSenderRef() {
        return mSenderReference;
    }

    public void setSenderReference(DatabaseReference mSenderReference) {
        this.mSenderReference = mSenderReference;
    }

    public String getTime() {
        return mTime;
    }

    public boolean isRead() {
        return this.read;
    }
}
