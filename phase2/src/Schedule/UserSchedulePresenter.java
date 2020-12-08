package Schedule;

import java.util.ArrayList;

public class UserSchedulePresenter extends SchedulePresenter{

    public void printUserMenu(){
        System.out.println("Welcome to the scheduling Menu" + System.lineSeparator() +
                "Press 1 to register for an event " + System.lineSeparator() +
                "Press 2 to see the schedule of events " + System.lineSeparator() +
                "Press 3 see all events currently registered for " + System.lineSeparator() +
                "Press 4 to  cancel a registration" + System.lineSeparator() +
                "Press 5 to see schedule by speaker" + System.lineSeparator() +
                "Press 6 to see schedule by day");
    }

    public void printMenu() {
        printUserMenu();
        System.out.println("Press 7 to review requests" );
        printGoBack();
    }

     public void printEvents(String events){
         System.out.println(events);
     }

     public void ChoosingEvent(int i){
        switch(i){
            case 1:
                System.out.println("What event would you like to register for?");
                break;
            case 2:
                System.out.println("What event would you like to cancel your registration for?");
                break;
        }}

    /**
     * If the registration was not successful lets the user know.
     * @param i An integer corresponding to a specific action.
     */
    public void printRegistrationBlocked(int i){
        switch (i) {
            case 1:
                System.out.println("You have already registered for this talk," + System.lineSeparator() +
                        "If you would like to register for another talk, please enter the corresponding number.");
                break;
            case 2:
                System.out.println("Event is at full capacity" + System.lineSeparator() +
                        "If you would like to register for another talk, please enter the corresponding number.");
                break;
            case 3:
                System.out.println("You are registered for a different event during this event's time" +
                        System.lineSeparator() +
                        "If you would like to register for another talk, please enter the corresponding number.");
                break;
            case 4:
                System.out.println("Sorry, this event is restricted" + System.lineSeparator() +
                        "If you would like to register for another talk, please enter the corresponding number.");
                break;

    }}

    public void scheduleBy(int i){
        switch (i){
            case 1:
                System.out.println("There are no speakers talking at the conference yet.");
            case 2:
                System.out.println("There are no events in the conference yet.");
    }}

    public void printAllRequests(ArrayList<String> requests) {
        Integer i = 1;
        for (String s : requests){
            System.out.println(Integer.toString(i) + ") " + s);
            i++;
        }
    }
}
