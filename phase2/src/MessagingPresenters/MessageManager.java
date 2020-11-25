package MessagingPresenters;

import Schedule.TalkSystem;
import UserLogin.User;
import UserLogin.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public abstract class MessageManager implements Observer {
    private UserStorage allUsers;
    private ConversationStorage conversationStorage;

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof UserStorage) {
            this.allUsers = (UserStorage) arg;
        }
        else if (arg instanceof  ConversationStorage){
            this.conversationStorage = (ConversationStorage) arg;
        }
    }
}
