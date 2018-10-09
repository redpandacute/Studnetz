package hirtz.florian.matura.ksa.studnetz.fragments.Chat;

/**
 * Created by Flo on 09.03.2018.
 */

public class ChatMessageModel {

    private int messageUserId;
    private String messageText;
    private String messageUser;
    private String messageTime;


    public ChatMessageModel(int messageUserId, String messageUser, String messageText, String messageTime) {
        this.messageUser = messageUser;
        this.messageText = messageText;
        this.messageTime = messageTime;
        this.messageUserId = messageUserId;

    }

    public ChatMessageModel() {}

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public int getMessageUserId() {
        return messageUserId;
    }
}
