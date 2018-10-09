package hirtz.florian.matura.ksa.studnetz.models;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by Florian Hirtz on 12.08.2018.
 */

public class ChatModel {

    private UserModel mMainprofileModel, mUserprofileModel;
    private DatabaseReference mMainprofileRef, mUserprofileRef, mChatRef;


    public ChatModel(UserModel mMainprofileModel, UserModel mUserprofileModel, DatabaseReference mMainprofileRef, DatabaseReference mUserprofileRef, DatabaseReference mChatRef) {
        this.mMainprofileModel = mMainprofileModel;
        this.mUserprofileModel = mUserprofileModel;
        this.mMainprofileRef = mMainprofileRef;
        this.mUserprofileRef = mUserprofileRef;
        this.mChatRef = mChatRef;
    }

    public UserModel getMainprofileModel() {
        return mMainprofileModel;
    }

    public void setMainprofileModel(UserModel mMainprofileModel) {
        this.mMainprofileModel = mMainprofileModel;
    }

    public UserModel getUserprofileModel() {
        return mUserprofileModel;
    }

    public void setUserprofileModel(UserModel mUserprofileModel) {
        this.mUserprofileModel = mUserprofileModel;
    }

    public DatabaseReference getMainprofileRef() {
        return mMainprofileRef;
    }

    public void setMainprofileRef(DatabaseReference mMainprofileRef) {
        this.mMainprofileRef = mMainprofileRef;
    }

    public DatabaseReference getUserprofileRef() {
        return mUserprofileRef;
    }

    public void setUserprofileRef(DatabaseReference mUserprofileRef) {
        this.mUserprofileRef = mUserprofileRef;
    }

    public DatabaseReference getChatRef() {
        return mChatRef;
    }

    public void setChatRef(DatabaseReference mChatRef) {
        this.mChatRef = mChatRef;
    }
}
