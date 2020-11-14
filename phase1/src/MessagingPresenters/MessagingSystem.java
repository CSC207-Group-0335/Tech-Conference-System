package MessagingPresenters;

import java.util.Observable;

/**
 * A class that represents a messaging system.
 */

public class MessagingSystem extends Observable {
    public ConversationStorage conversationStorage;
    public MessengerController messengerController; // ?
    public SpeakerMessengerController speakerMessengerController;
    public OrganizerMessengerController organizerMessengerController;

    /**
     * Instantiates OrganizerMessengerController, SpeakerMessengerController, MessengerController, and
     * ConversationStorage.
     */

    public MessagingSystem() {
        // parameters?
        this.organizerMessengerController = new OrganizerMessengerController();
        this.speakerMessengerController = new SpeakerMessengerController();
        //this.messengerController = new MessengerController();
        this.conversationStorage = new ConversationStorage();
    }

    /**
     * Requests conversationStorage to create a ConversationManager.
     */

    public void createConversationManager(){
        //request conversationStorage to create conversation manager
    }
}

