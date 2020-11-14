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
//should all these methods return void?
    public boolean signUp(Talk talk) { return attendee.addTalk(talk); }

    public boolean cancelRegistration(Talk talk) {
        return attendee.removeTalk(talk);
    }

    public String allTalks() { return talkManager.talkMapStringRepresentation(); }
    
    public ArrayList<ArrayList<Object>> allRegistered() {
        //list of all the talks they're enrolled for
        ArrayList<Talk> talkList = attendee.getTalkList();
        // new list with the same length as talkList
        ArrayList<ArrayList<Object>> registeredFor = new ArrayList<>();
        //for loop to populate the new list
        for (int i = 0; i < talkList.size(); i++) {
            //this will be the "tuple" that's stored
            // the 0 index will contain the string representation of talk
            // the 1 index will have the speaker name
            // the 2 index will have the room name
            // we will append this to the new list
            ArrayList<Object> talkSpeakerRoom = new ArrayList<>();
            Talk speech = talkList.get(i);
            talkSpeakerRoom.set(0, speech.toString());
            talkSpeakerRoom.set(1, talkManager.getTalkSpeaker(speech).getName());
            talkSpeakerRoom.set(2, talkManager.getTalkRoom(speech).getRoomName());
            registeredFor.set(i, talkSpeakerRoom);
        }
        return registeredFor;
    }
    private void registerTalk(Presenter presenter, Scanner scan){
        // show them a list of all available talks
        presenter.print(this.allTalks());
        //they will pick the number corresponding to each talk
        presenter.print(3);
        //assuming they will have asked to see all talks they could register before selecting command 1
        int talkIndex = scan.nextInt();
        //converting talkMap to a list of the keys in talkMap
        Set<Talk> keys = talkManager.talkMap.keySet();
        List<Talk> listKeys = new ArrayList<Talk>( keys );
        Talk talkToRegister = listKeys.get(talkIndex - 1);
        this.signUp(talkToRegister);
        // prints "Success"
        presenter.print(6);
    }
    private void seeAllTalks(Presenter presenter, Scanner scan){
        //use the string representation in TalkManager
        presenter.print(this.allTalks());
        scan.close();
    }
    private void seeAllRegistered(Presenter presenter, Scanner scan){
        //each line shows talk, time, speaker, room
        for (ArrayList<Object> tup : this.allRegistered() ){
            String line = (String) tup.get(0) + tup.get(1) + tup.get(2);
            presenter.print(line);
        }
        scan.close();
    }
    private void cancelATalk(Presenter presenter, Scanner scan){
        presenter.print(5);
        String talk = scan.nextLine();
        //assuming they enter in a valid talk and the talk's title is unique, should be a fair assumption
        //since which user for sigh up for the same talk twice?
        //could change so it takes it in a time and room as well? We'll see
        Talk toCancel = null;
        for (Talk t : this.attendee.talkList){
            if(t.getTitle().equals(talk)){
                toCancel = t;
            }
        }
        //check to see they entered valid talk
        if (toCancel != null){
            this.cancelRegistration(toCancel);
            presenter.print(6);
            scan.close();
        }
        presenter.print(4);
    }
//changed the method name to go because there already other methods named run in the MessagingPresenterPackage
    public void go(){
        Presenter presenter = new Presenter();
        presenter.print(1);
        presenter.print(2);
        Scanner scan = new Scanner(System.in);
        while(true) {
            int command = scan.nextInt();
            //if they want to register for a talk
            if (command == 1) {
                this.registerTalk(presenter, scan);
                //If they want to see all available talks
            } else if (command == 2) {
                this.seeAllTalks(presenter, scan);
                //if they want to see all the talks they are currently registered for
            } else if (command == 3) {
                this.seeAllRegistered(presenter, scan);
                // if they want to cancel a registration
            } else if (command == 4) {
                this.cancelATalk(presenter, scan);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
