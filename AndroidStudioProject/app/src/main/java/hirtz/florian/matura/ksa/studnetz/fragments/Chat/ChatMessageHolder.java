package hirtz.florian.matura.ksa.studnetz.fragments.Chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import hirtz.florian.matura.ksa.studnetz.R;


/**
 * Created by Florian Hirtz on 12.08.2018.
 */

class ChatMessageHolder extends RecyclerView.ViewHolder {

    View view;
    boolean isSender;

    public ChatMessageHolder(View itemView, boolean isSender) {
        super(itemView);
        view = itemView;
        this.isSender = isSender;
    }

    public void setMessage(String messageUser, String messageText, String messageTime) {

        if(isSender) {
            LinearLayout lineLayout = view.findViewById(R.id.message_right_layout);
            lineLayout.setVisibility(LinearLayout.VISIBLE);

            view.findViewById(R.id.message_left_layout).setVisibility(LinearLayout.GONE);

            TextView mText_tv = view.findViewById(R.id.message_right_textview);
            mText_tv.setText(messageText);

            TextView mTime_tv = view.findViewById(R.id.sendingtime_right_textview);
            mTime_tv.setText(messageTime);
        } else {
            LinearLayout lineLayout = view.findViewById(R.id.message_left_layout);
            lineLayout.setVisibility(LinearLayout.VISIBLE);

            view.findViewById(R.id.message_right_layout).setVisibility(LinearLayout.GONE);

            TextView mText_tv = view.findViewById(R.id.message_left_textview);
            mText_tv.setText(messageText);

            TextView mTime_tv = view.findViewById(R.id.sendingtime_left_textview);
            mTime_tv.setText(messageTime);
        }
    }
}
