package MessagingPresenters;

import UserLogin.Attendee;
import UserLogin.Organizer;
import UserLogin.Speaker;
import UserLogin.User;

import java.util.Observable;
import java.util.Observer;

/**
 * A class that represents a messaging system.
 */

public class MessagingSystem extends Observable implements Observer {
    public ConversationStorage conversationStorage;
    public User user;
    public AttendeeMessengerController attendeeMessengerController;
    public SpeakerMessengerController speakerMessengerController;
    public OrganizerMessengerController organizerMessengerController;

    /**
     * Instantiates OrganizerMessengerController, SpeakerMessengerController, MessengerController, and
     * ConversationStorage.
     */

    public MessagingSystem() {
        this.conversationStorage = new ConversationStorage();
        this.attendeeMessengerController = new AttendeeMessengerController((Attendee) this.user);
        this.speakerMessengerController = new SpeakerMessengerController((Speaker) this.user);
        this.organizerMessengerController = new OrganizerMessengerController((Organizer) this.user);
        this.addObserver(this.attendeeMessengerController);
        this.addObserver(this.speakerMessengerController);
        this.addObserver(this.organizerMessengerController);
        setStorage(conversationStorage);
    }

    //Method to notify that ConversationStorage has been updated
    public void setStorage(ConversationStorage conversationStorage) {
        setChanged();
        notifyObservers(conversationStorage);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof User) {
            this.user = (User) arg;
        }
    }

    public void run(){
        /* runs code */
    }
}

