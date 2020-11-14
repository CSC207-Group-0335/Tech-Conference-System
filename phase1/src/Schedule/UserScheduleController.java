package Schedule;
import UserLogin.*;

import java.io.Serializable;
import java.util.*;

/**
 * A controller class describing the actions a user can perform in the program
 */
public class UserScheduleController implements Observer {
    /**
     * The user of the program
     */
    UserScheduleManager attendee ;
    TalkManager talkManager;

    /**
     * Initializes a new controller for the user
     * @param user the user of the program
     */
    public UserScheduleController(UserScheduleManager user, TalkManager talkManager){
        this.attendee = user;
        this.talkManager = talkManager;
    }

    public boolean signUp(Talk talk) { return attendee.addTalk(talk); }

    public boolean cancelRegistration(Talk talk) {
        return attendee.removeTalk(talk);
    }

    public String allAttending() { return talkManager.talkMapStringRepresentation(); }
    
    public ArrayList<ArrayList<Object>> allRegistered() {
        ArrayList<Talk> talkList = attendee.getTalkList();
        ArrayList<ArrayList<Object>> registeredFor = new ArrayList<>();
        for (int i = 0; i < talkList.size(); i++) {
            ArrayList<Object> talkSpeakerRoom = new ArrayList<>();
            Talk speech = talkList.get(i);
            talkSpeakerRoom.set(0, speech);
            talkSpeakerRoom.set(1, talkManager.getTalkSpeaker(speech));
            talkSpeakerRoom.set(2, talkManager.getTalkRoom(speech));
            registeredFor.set(i, talkSpeakerRoom);
        }
        return registeredFor;
    }
//trying to make it return any type, tried using generic type doesn't work so just using object for now
    // Dont know what serializable just what Java reccomended to do to get rid of the errors related to ^
    public Serializable run(){
        Presenter presenter = new Presenter();
        presenter.print(1);
        presenter.print(2);
        Scanner scan = new Scanner(System.in);
        while(true) {
            int command = scan.nextInt();
            if (command == 1) {
                presenter.print(this.allAttending());
                presenter.print(3);
                String talk = scan.nextLine();
                //assuming they enter in a valid talk
                Talk toRegister = null;
                Map<Talk, ArrayList<Object>> talkList = this.talkManager.talkMap;
                for (Talk t : talkList.keySet()){
                    if(t.getTitle().equals(talk)){
                       toRegister = t;
                   }
                }
                //check to see they entered valid talk
                if (toRegister != null){
                    scan.close();
                    return this.signUp(toRegister);
                }
                presenter.print(4);
            } else if (command == 2) {
                scan.close();
                return this.allAttending();
            } else if (command == 3) {
                scan.close();
                return this.allRegistered();
            } else if (command == 4) {
                presenter.print(5);
                String talk = scan.nextLine();
                //assuming they enter in a valid talk
                Talk toCancel = null;
                for (Talk t : this.attendee.talkList){
                    if(t.getTitle().equals(talk)){
                        toCancel = t;
                    }
                }
                //check to see they entered valid talk
                if (toCancel != null){
                    scan.close();
                    return this.cancelRegistration(toCancel);
                }
                presenter.print(4);
                //duplicate code, should I make a private method?
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
