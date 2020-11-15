package MessagingPresenters;

import UserLogin.User;

import java.util.Observable;

/**
 * A class that represents a messaging system.
 */

public class MessagingSystem extends Observable {
    public ConversationStorage conversationStorage;

    /**
     * Instantiates OrganizerMessengerController, SpeakerMessengerController, MessengerController, and
     * ConversationStorage.
     */

    public MessagingSystem() {
        // parameters?
        this.conversationStorage = new ConversationStorage();
        setStorage(conversationStorage);
    }

    //Method to notify that ConversationStorage has been updated
    public void setStorage(ConversationStorage conversationStorage) {
        setChanged();
        notifyObservers(conversationStorage);
    }

    public void run(){
        /* runs code */
    }
}

