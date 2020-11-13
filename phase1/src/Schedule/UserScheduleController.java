package Schedule;
import UserLogin.*;

import java.io.Serializable;
import java.util.*;

/**
 * A controller class describing the actions a user can perform in the program
 */
public class UserScheduleController implements Actions, Observer {
    /**
     * The user of the program
     */
    UserScheduleManager attendee ;
    TalkManager talkManager;
    SignUpAttendeesManager signUpList;

    /**
     * Initializes a new controller for the user
     * @param user the user of the program
     */
    public UserScheduleController(UserScheduleManager user, TalkManager talkManager, SignUpAttendeesManager signUpList
    ){
        this.attendee = user;
        this.talkManager = talkManager;
        this.signUpList = signUpList;
    }

    @Override
    public boolean signUp(Talk talk) {

        return attendee.addTalk(talk);
    }

    @Override
    public boolean cancelRegistration(Talk talk) {
        return attendee.removeTalk(talk);
    }

    @Override
    public ArrayList<User> allAttending() {
        return signUpList.userList;
    }

    @Override
    public ArrayList<Talk> allRegistered() {
        return attendee.getTalkList();
    }
//trying to make it return any type, tried using generic type doesn't work so just using object for now
    // Dont know what serializable just what Java reccomended to do to get rid of the errors related to ^
    public Serializable run(){
        System.out.println("1. register for a talk, 2. see the guest list, 3. see all talks currently registered, " +
                "4. cancel a registration");
        System.out.println("Please input a command");
        Scanner scan = new Scanner(System.in);
        while(true) {
            int command = scan.nextInt();
            if (command == 1) {
                System.out.println("What event would you like to register for?");
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
                    this.signUp(toRegister);
                    break;
                }
                System.out.println("Not a valid talk");
            } else if (command == 2) {
                return this.allAttending();
            } else if (command == 3) {
                return this.allRegistered();
            } else if (command == 4) {
                System.out.println("What event would you like to cancel for?");
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
                    return this.cancelRegistration(toCancel);
                }
                System.out.println("Not a valid talk");
                //duplicate code, should I make a private method?
            }
        }
        scan.close();
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
