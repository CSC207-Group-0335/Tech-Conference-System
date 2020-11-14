package MessagingPresenters;

import java.util.Observable;

/**
 * A class that represents a messaging system.
 */

public class MessagingSystem extends Observable {
    public ConversationStorage conversationStorage;
    public SpeakerMessengerController speakerMessengerController;
    public OrganizerMessengerController organizerMessengerController;
    public AttendeeMessengerController attendeeMessengerController;

    /**
     * Instantiates OrganizerMessengerController, SpeakerMessengerController, MessengerController, and
     * ConversationStorage.
     */

    public MessagingSystem() {
        // parameters?
        this.organizerMessengerController = new OrganizerMessengerController();
        this.speakerMessengerController = new SpeakerMessengerController();
        this.conversationStorage = new ConversationStorage();
        this.attendeeMessengerController = new AttendeeMessengerController();
    }

    /**
     * Requests conversationStorage to create a ConversationManager.
     */

    public void createConversationManager(){
        //request conversationStorage to create conversation manager
    }
}

