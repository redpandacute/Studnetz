package hirtz.florian.matura.ksa.studnetz.fragments.Chatoverview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hirtz.florian.matura.ksa.studnetz.R;
import hirtz.florian.matura.ksa.studnetz.models.ProfilePictureModel;
import hirtz.florian.matura.ksa.studnetz.models.UserModel;


/**
 * Created by Florian Hirtz on 13.08.2018.
 */


class OpenChatViewHolder extends RecyclerView.ViewHolder {

    public View view;
    private UserModel userModel;
    private ProfilePictureModel picture;
    private OpenChatModel mModel;

    private static final int DISPLAY_CHARS = 40;

    public OpenChatViewHolder(View itemView) {
        super(itemView);
        view = itemView;
    }

    public void validate(OpenChatModel model) {
        this.mModel = model;
        this.userModel = mModel.getUserModel();

        TextView cName_tv = view.findViewById(R.id.openchat_name_textview);
        cName_tv.setText(userModel.getFirstname() + " " + userModel.getName());
        TextView cTime_tv = view.findViewById(R.id.openchat_time_textview);
        cTime_tv.setText(mModel.getTime());
        TextView cLatest_tv = view.findViewById(R.id.openchat_latest_textview);

        if(mModel.getLatestMessage().length() < DISPLAY_CHARS) {
            cLatest_tv.setText(mModel.getLatestMessage());
        } else {
            char[] chars = new char[DISPLAY_CHARS];
            mModel.getLatestMessage().getChars(0, DISPLAY_CHARS - 1, chars, 0);

            String latest = "";
            for(int l = 0; l < chars.length; l++) {
                latest = latest + chars[l];
            }

            cLatest_tv.setText(latest + "...");
        }

        if(!mModel.isRead()) {
            view.findViewById(R.id.notifyunread_imageview).setVisibility(ImageView.VISIBLE);
        } else {
            view.findViewById(R.id.notifyunread_imageview).setVisibility(ImageView.INVISIBLE);
        }
    }

    public void setProfilePicture(ProfilePictureModel picture) {
        ImageView profilePicture_iv = view.findViewById(R.id.openchat_profilepicture_imageView);
        profilePicture_iv.setImageBitmap(picture.getImageBitmap());
    }

    public void setUserModel(UserModel userModel) {
            this.userModel = userModel;
        }

    public UserModel getUserModel(){
            return this.userModel;
    }

    public View getView() {
        return view;
    }
}
