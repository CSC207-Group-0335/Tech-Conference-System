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
}
