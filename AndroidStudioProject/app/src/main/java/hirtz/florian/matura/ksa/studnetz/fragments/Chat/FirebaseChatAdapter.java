package hirtz.florian.matura.ksa.studnetz.fragments.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import hirtz.florian.matura.ksa.studnetz.R;
import hirtz.florian.matura.ksa.studnetz.models.ChatModel;
import hirtz.florian.matura.ksa.studnetz.models.UserModel;

/**
 * Created by ingli on 12.08.2018.
 */

class FirebaseChatAdapter extends FirebaseRecyclerAdapter<ChatMessageModel, ChatMessageHolder> {

    private static final int LAYOUT_PARTNER = 1;
    private static final int LAYOUT_LOCAL = 2;
    private final ChatModel mChatModel;
    private UserModel mMainuserModel;


    public FirebaseChatAdapter(Class<ChatMessageModel> chatMessageClass,
                                 int modelLayout,
                                 Class<ChatMessageHolder> viewHolderClass,
                                 Query reference, UserModel mMainuserModel,
                                 ChatModel mChatModel
    ) {
        super(chatMessageClass, modelLayout, viewHolderClass, reference);
        this.mMainuserModel = mMainuserModel;
        this.mChatModel = mChatModel;
    }

    @Override
    public ChatMessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message, parent, false);
        mChatModel.getMainprofileRef().child("read").setValue(1);

        if (viewType == LAYOUT_PARTNER) {
            return new ChatMessageHolder(view, false);
        } else {
            return new ChatMessageHolder(view, true);
        }
    }

    @Override
    public int getItemViewType(int position) {

        ChatMessageModel model = getItem(position);
        if(model == null) {return 0;}

        if(model.getMessageUserId() == mMainuserModel.getId()) {
            return LAYOUT_LOCAL;
        }
        return LAYOUT_PARTNER;
    }

    @Override
    protected void populateViewHolder(ChatMessageHolder viewHolder, ChatMessageModel model, int position) {
        viewHolder.setMessage(model.getMessageUser(), model.getMessageText(), model.getMessageTime());
    }

}
