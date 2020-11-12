package Schedule;
import UserLogin.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A controller class describing the actions a user can perform in the program
 */
public class UserScheduleController implements Actions{
    /**
     * The user of the program
     */
    UserScheduleManager attendee ;

    /**
     * Initializes a new controller for the user
     * @param user the user of the program
     */
    public UserScheduleController(UserScheduleManager user){
        this.attendee = user;
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
        return null;
    }

    @Override
    public ArrayList<Talk> allRegistered() {
        return attendee.getTalkList();
    }

    public void run(){
        System.out.println("1. register for a talk, 2. see the guest list, 3. see all talks currently registered, " +
                "4. cancel a registration");
        System.out.println("Please input a command");
        Scanner scan = new Scanner(System.in);
        while(true) {
            String command = scan.nextLine();
            if (command == "1") {
                System.out.println("What event would you like to register for?");
                String talk = scan.nextLine();
                Talk toRegister = null;
                ArrayList<Talk> talkList = this.allRegistered();
                for (Talk t : talkList){
                    if(t.getTitle() == talk){
                        toRegister = t;
                    }
                }
                this.signUp(toRegister);
                break;
            } else if (command == "2") {
                break;
            } else if (command == "3") {
                break;
            } else if (command == "4") {
                break;
            }
        }

    }
}
