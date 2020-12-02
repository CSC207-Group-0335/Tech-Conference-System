package Schedule;

import java.util.ArrayList;

/**
 * Prints to the console so that the user can have something to interact with.
 */
public class UserSchedulePresenter {
    /**
     * A series of phrases output depending on what the organizer does.
     * @param i An int corresponding to specific output.
     */
    public void printMenu(int i) {
        if (i == 1) {
            System.out.println("Welcome to the scheduling Menu" + System.lineSeparator() +
                    "Press 1 to register for a talk " + System.lineSeparator() +
                    "Press 2 to see the schedule of events " + System.lineSeparator() +
                    "Press 3 see all talks currently registered for " + System.lineSeparator() +
                    "Press 4 to  cancel a registration" + System.lineSeparator()+
                    "Press 5 to see schedule by speaker" + System.lineSeparator()+
                    "Press 6 to see schedule by day" + System.lineSeparator()+
                    "Press 0 to go back to the main menu");
        } else if (i == 2) {
            System.out.println("Please input a command");
        } else if (i == 3) {
            System.out.println("What event would you like to register for?" + System.lineSeparator()+
                    "Enter the number corresponding " +
                    "to each talk" + System.lineSeparator() +
                    "Press 0 to go back to the scheduling menu");
        } else if (i == 4) {
            System.out.println("Not a valid talk");
        } else if (i == 5) {
            System.out.println("What event would you like to cancel your registration for?" + System.lineSeparator()+
                    "Enter the number corresponding to each talk" + System.lineSeparator() + System.lineSeparator() +
                    "Press 0 to go back to the scheduling menu");
        } else if (i == 6){
            System.out.println("Success");
        }
        else if (i==7){
            System.out.println("Invalid talk number, try again");
        }
        else if (i==8){
            System.out.println("Invalid command, try again");
        }
        else if (i==9){
            System.out.println("Returning to main menu...");
        }
        else if (i==10){
            System.out.println("Returning to scheduling menu...");
        }
        else if (i==11){
            System.out.println(System.lineSeparator() + "Press 0 to go back to the scheduling menu");
        }
        else if(i==12){
            System.out.println("Talk ");
        }
        else if (i==13){
            System.out.println("You are not currently registered for talks.");
        }
        else if (i==14){
            System.out.println("Pick speaker by Index, press 0 to go back");
        }
        else if (i==15){
            System.out.println("There are no speakers talking at the conference yet.");
        }
        else if (i == 16){
            System.out.println("Invalid speaker number, try again");
        }
    }

    /**
     * Prints the specified talk, with a specified number attached to it.
     * @param num The integer attached to the string representation of talk.
     * @param event The event.
     * @param eventManager The eventManager.
     */
    public void printTalk(Integer num, String event, EventManager eventManager){
        System.out.println(num.toString() + ") " + eventManager.toStringEvent(event));
    }

    /**
     * Prints al the talks in the conference.
     * @param eventManager The talkManager.
     */
    public void printAllTalks(EventManager eventManager){
        System.out.println(eventManager.EventMapStringRepresentation());
    }

    /**
     * A string representation of the speaker's name and corresponding position in the speaker list.
     * @param speakerNameList The list of speakers names.
     */
    public void printAllSpeakers(ArrayList<String> speakerNameList) {
        Integer i = 1;
        for (String s : speakerNameList){
            System.out.println(Integer.toString(i) + ")" + s);
            i++;
        }
    }




    /**
     * Greets the user with their name.
     *
     */
    public void printHello(String name){
        System.out.println("Hello " + name);
    }

    /**
     * If the registration was not successful lets the user know.
     * @param i An integer corresponding to a specific action.
     */
    public void printRegistrationBlocked(int i){
        if (i==1){
            System.out.println("You have already registered for this talk," +System.lineSeparator()+
                    "If you would like to register for another talk, please enter the corresponding number.");
        }
        else if (i ==2){
            System.out.println("Event is at full capacity"+System.lineSeparator()+
                    "If you would like to register for another talk, please enter the corresponding number.");
        }
        else if (i==3){
            System.out.println("You are registered for a different event during this event's time"+System.lineSeparator()+
                    "If you would like to register for another talk, please enter the corresponding number.");
        }
        else if (i==4){
            System.out.println("Sorry, this event is restricted"+System.lineSeparator()+
                    "If you would like to register for another talk, please enter the corresponding number.");
        }
    }

    /**
     * Output the schedule for the chosen room or chosen speaker if the talkArrayList is not empty.
     * @param eventIDArrayList The talkArraylist.
     * @param eventManager The TalkManager.
     * @param i The integer corresponding to a specific action.
     */
    public void printSchedule(ArrayList<String> eventIDArrayList, EventManager eventManager, int i){
        if(i==1) {
            System.out.println("Schedule for the chosen room:");
        }
        else if (i==2){
            System.out.println("Schedule for the chosen speaker:");
        }
        if (eventIDArrayList.size() ==0){
            System.out.println("The schedule is empty.");
        }
        else{
            for (String t: eventIDArrayList){
                System.out.println(eventManager.toStringEvent(t) + System.lineSeparator());
            }}
    }
}