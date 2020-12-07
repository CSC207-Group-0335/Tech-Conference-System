package Schedule;

import UserLogin.Attendee;
import UserLogin.Speaker;
import UserLogin.UserManager;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Prints to the console so that the organizer can have something to interact with.
 */
public class OrgSchedulePresenter extends UserSchedulePresenter{
    /**
     * A series of phrases output depending on what the organizer does.
     * @param i An int corresponding to specific output.
     */
    public void printMenu(int i){
        switch (i) {
            case 1:
                System.out.println("Welcome to the scheduling Menu" + System.lineSeparator() +
                        "Press 1 to register for a talk " + System.lineSeparator() +
                        "Press 2 to see the schedule of events " + System.lineSeparator() +
                        "Press 3 see all talks currently registered for " + System.lineSeparator() +
                        "Press 4 to  cancel a registration" + System.lineSeparator() +
                        "Press 5 to request to add a talk " + System.lineSeparator() +
                        "Press 6 to register a room " + System.lineSeparator() +
                        "Press 7 to request to add a user" + System.lineSeparator() +
                        "Press 8 to cancel an event" + System.lineSeparator() +
                        "Press 9 to see schedule by speaker" + System.lineSeparator() +
                        "Press 10 to see schedule by day" + System.lineSeparator() +
                        "Press 11 to change room capacity" + System.lineSeparator() +
                        "Press 12 to Speaker and Attendee accounts" + System.lineSeparator() +
                        "Press 13 to see Attendee requests" + System.lineSeparator() + //added for requests
                        "Press 0 to go back to the main menu");
                break;
            case 2:
                System.out.println("Please input a command");
                break;
            case 3:
                System.out.println("What event would you like to register for?" + System.lineSeparator() +
                        "Enter the number corresponding " +
                        "to each talk" + System.lineSeparator() +
                        "Press 0 to go back to the scheduling menu");
                break;
            case 4:
                System.out.println("Not a valid talk");
                break;
            case 5:
                System.out.println("What event would you like to cancel your registration for?" +
                        "Enter the number corresponding to each talk" + System.lineSeparator() +
                        "Press 0 to go back to the scheduling menu");
                break;
            case 6:
                System.out.println("What new talk would you like to add to the schedule of events?" +
                        "Enter the number corresponding " +
                        "to each talk" + System.lineSeparator() +
                        "Press 0 to go back to the scheduling menu");
                break;
            case 7:
                System.out.println("Pick room by Index, press 0 to go back");
                break;
            case 8:
                System.out.println("Pick speaker by Index, press 0 to go back");
                break;
            case 9:
                System.out.println("Enter the name of the new room to register, press 0 to go back:");
                break;
            case 10:
                System.out.println("Enter the credentials of the new user to register" + System.lineSeparator() +
                        "Enter the name" + System.lineSeparator() +
                        "Enter the password" + System.lineSeparator() +
                        "Enter the e-mail" + System.lineSeparator() +
                        "Enter the type of user");

                break;
            case 11:
                System.out.println("Success");
                break;
            case 12:
                System.out.println("Invalid talk number, try again");
                break;
            case 13:
                System.out.println("Invalid room number, try again");
                break;
            case 14:
                System.out.println("Invalid speaker number, try again");
                break;
            case 15:
                System.out.println("Invalid command, try again");
                break;
            case 16:
                System.out.println("Returning to main menu...");
                break;
            case 17:
                System.out.println("Returning to scheduling menu...");
                break;
            case 18:
                System.out.println("Invalid day, try again");
                break;
            case 19:
                System.out.println("Invalid hour number, try again");
                break;
            case 20:
                System.out.println("Room already exists in the system, try registering a different room.");
                break;
            case 21:
                System.out.println("Invalid email address, try again.");
                break;
            case 22:
                System.out.println("Invalid user type, try again.");
                break;
            //cases below are for requests, feel free to remove if it messes things up
            case 23:
                System.out.println("Pick user requests by Index, press 0 to go back");
                break;
            case 24:
                System.out.println("Pick request by Index, press 0 to go back");
                break;
            case 25:
                System.out.println("Press 1 to approve request, press 2 to reject." + System.lineSeparator() +
                        "Press 0 to go back");
                break;
        }
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
     * A string representation of the room's name and corresponding position in the room list.
     * @param roomNameList a list of the rooms names in the conference.
     */
    public void printAllRooms(ArrayList<String> roomNameList){
        Integer i = 1;
        for (String r : roomNameList){
            System.out.println(Integer.toString(i) + ")" + r);
            i++;
        }
    }
    /**
     * A series of phrases output depending on what the organizer does.
     * @param i An int corresponding to specific output.
     */
    public void PrintRequestTalkProcess(int i){
        switch (i) {
            case 1:
                System.out.println("Please choose a day of the conference for your requested talk (By indicating day November 21, 22 or 23)");
                break;
            case 2:
                System.out.println("Choose a time (by hour) for your requested talk." + System.lineSeparator() +
                        "Talks may start or end from 9:00 to 16:00" + System.lineSeparator() +
                        "Indicate your chosen time by a number from 9 to 16.");
                break;
            case 3:
                System.out.println("Chosen speaker is not available in the chosen hour and day.");
                break;
            case 4:
                System.out.println("Chosen room is not available in the chosen hour and day.");
                break;
            case 5:
                System.out.println("Pick again or press 0 to go back to the scheduling menu");
                break;
            case 6:
                System.out.println("Both chosen room and speaker are not available in the chosen hour and day.");
                break;
            case 7:
                System.out.println("Talk added successfully");
                break;
            case 8:
                System.out.println("An error occurred, a talk was not added");
                break;
            case 9:
                System.out.println("Enter talk title:");
                break;
        }
    }

    /**
     * Output the schedule for the chosen room or chosen speaker if the talkArrayList is not empty.
     * @param eventIDArrayList The talkArraylist.
     * @param eventManager The TalkManager.
     * @param i The integer corresponding to a specific action.
     */
    public void printSchedule(ArrayList<String> eventIDArrayList, EventManager eventManager, int i){
        switch (i) {
            case 1:
                System.out.println("Schedule for the chosen room:");
                break;
            case 2:
                System.out.println("Schedule for the chosen speaker:");
                break;
        }
        if (eventIDArrayList.size() ==0){
            System.out.println("The schedule is empty.");
        }
        else{
        for (String t: eventIDArrayList){
            System.out.println(eventManager.toStringEvent(t) + System.lineSeparator());
        }}
    }

    public void printRequestEmails(UserManager userManager) {
        HashMap<String, ArrayList<String>> allRequests = userManager.emailToRequest();
        for (String email : allRequests.keySet()) {
            Attendee attendee = (Attendee) userManager.emailToUser(email);
            System.out.println(email + ": " + attendee.getNumberOfRequests() + " requests"+ System.lineSeparator());
        }
    }

    public void printRequests(UserManager userManager, String email) {
        HashMap<String, ArrayList<String>> allRequests = userManager.emailToRequest();
        if (allRequests.containsKey(email)) {
            for (String req : allRequests.get(email)) {
                System.out.println(req + System.lineSeparator());
            }
        }
    }
}

