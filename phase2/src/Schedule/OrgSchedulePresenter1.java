package Schedule;

public class OrgSchedulePresenter1 extends UserSchedulePresenter1{

    public void printMenu(){
        printUserMenu();
        System.out.println("Press 7 to request to add a talk " + System.lineSeparator() +
                "Press 8 to register a room " + System.lineSeparator() +
                "Press 9 to request to register a user" + System.lineSeparator() +
                "Press 10 to cancel an event" + System.lineSeparator() +
                "Press 11 to change room capacity");
        printGoBack();
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
                System.out.println("enter the capacity for this event");
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
        }
    }

    public void printRequestEvent(int i, String str){
        switch (i){
            case 1:
                if (str == "1"){
                    System.out.println(str + "speaker chosen");
                }
                else if(str == "0"){
                    System.out.println("No speakers chosen");
                }
                else{System.out.println( str + " speakers chosen");}
                break;
            case 2:
                System.out.println(str + " added successfully");
                break;
        }
    }
}
