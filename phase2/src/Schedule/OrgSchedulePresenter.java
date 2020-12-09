package Schedule;

public class OrgSchedulePresenter extends UserSchedulePresenter {

    public void printMenu(){
        printUserMenu();
        System.out.println("Press 7 to request to add an Event " + System.lineSeparator() +
                "Press 8 to register a room " + System.lineSeparator() +
                "Press 9 to request to register a user" + System.lineSeparator() +
                "Press 10 to cancel an event" + System.lineSeparator() +
                "Press 11 to review user requests");
        printGoBack();
    }

    public void printReviewRequests(int i){
        switch (i){
            case 1:
                System.out.println("Which attendee's request would you like to review?");
            case 2:
                System.out.println("What status would like to set request to? approved or rejected?");
        }
    }

    public void cancelEvent(int i, String event){
        switch (i){
            case 1:
                choose(event);
                System.out.println("Which event would you like to cancel");
                break;
            case 2:
                System.out.println("Event " + event + " cancelled successfully");
                break;
            case 3:
                System.out.println("No events available to cancel.");
                }}

    public void registerUserMenu(int i){
        switch (i){
            case 1:
                System.out.println("Enter the credentials of the new user to register" + System.lineSeparator() +
                "Enter the name" + System.lineSeparator() +
                "Enter the password" + System.lineSeparator() +
                "Enter the e-mail" + System.lineSeparator() +
                "Enter the type of user (Attendee/ Speaker/ Organizer)");
                break;
            case 2:
                System.out.println("Enter VIP to allow the new user into VIP restricted events" +
                        System.lineSeparator()+ "Press any other key to continue");
                break;
    }}

    public void printRegisterRoom(int i){
        switch (i) {
            case 1:
                System.out.println("Enter the name of the new room to register, press 0 to go back");
                break;
            case 2:
                System.out.println("enter the capacity for this room:");
                break;
            case 3:
                System.out.println("Room already exists in the system, try registering a different room.");
                break;
        } }

    public void printRequestEventMenu(int i){
        switch (i){
            case 1:
                System.out.println("Enter the capacity for this event");
                break;
            case 2:
                System.out.println("Capacity above room capacity");
                break;
            case 3:
                System.out.println("Enter the title of the event.");
                break;
            case 4:
                System.out.println("Enter VIP if the event is restricted, none otherwise");
                break;
            case 5:
                System.out.println("Invalid room or time. Please select new room and time");
                break;
            case 6:
                System.out.println("Valid speakers, room, time and capacity for an event.");
                break;
            case 7:
                System.out.println("Pick starting time of event:");
                break;
            case 8:
                System.out.println("Pick end time for event");
                break;
            case 9:
                System.out.println("End time must be after the start time. Pick another end time");
                break;
        }
    }

    /**
     * A series of phrases output depending on what the organizer does.
     * @param i An int corresponding to specific output.
     */
    public void printRequestEventProcess(int i){
        switch (i) {
            case 1:
                System.out.println("Please choose a day of the conference for your requested talk (By indicating day NOVEMBER 21, 22 or 23)");
                break;
            case 2:
                System.out.println("Choose a time (by hour) for your requested talk." + System.lineSeparator() +
                        "Talks may start or from 9:00 to 16:00 and end from 10:00 to 17:00" + System.lineSeparator() +
                        "Indicate your chosen time by a number from 9 to 17");
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

    public void printChoosingSpeakersProcess(int i, String str){
        switch (i){
            case 1:
                if (str.equals("1")){
                    System.out.println(str + " speaker chosen");
                }
                else if(str.equals("0")){
                    System.out.println("No speakers chosen");
                }
                else{System.out.println( str + " speakers chosen");}
                break;
            case 2:
                System.out.println(str + " added successfully");
                break;
            case 3:
                System.out.println("Too many speakers. Please choose at most " + str + " speakers.");
                break;
        }
    }

    public void printChooseSpeakers(int i){
        switch (i){
            case 1:
                System.out.println("Enter the number of speakers for the event:");
                break;
            case 2:
                System.out.println("Speaker added");
                break;
            case 3:
                System.out.println("Speaker already chosen. Please choose another speaker.");
                break;
            case 4:
                System.out.println("Please choose at least 0 speakers for the event.");
                break;
        }
    }

    public void printSchedule(String object){
        System.out.println("Schedule for chosen " + object + ":");
    }
}
