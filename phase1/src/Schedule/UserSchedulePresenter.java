package Schedule;

public class UserSchedulePresenter {
    public void printMenu(int i) {
        if (i == 1) {
            System.out.println("Welcome to the scheduling Menu" + System.lineSeparator() +
                    "Press 1 to register for a talk " + System.lineSeparator() +
                    "Press 2 to see the schedule of events " + System.lineSeparator() +
                    "Press 3 see all talks currently registered for " + System.lineSeparator() +
                    "Press 4 to  cancel a registration" + System.lineSeparator()+
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
            System.out.println("What event would you like to cancel your registration for?" +
                    "Enter the number corresponding to each talk" + System.lineSeparator() +
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
    }

    public void printTalk(Integer num, Talk t, TalkManager talkManager){
        System.out.println(num.toString() + ") " + talkManager.toStringTalk(t));
    }

    public void printAllTalks(TalkManager talkManager){
        System.out.println(talkManager.talkMapStringRepresentation());
    }

    public void printHello(UserScheduleManager userScheduleManager){
        System.out.println("Hello " + userScheduleManager.getUser());
    }
}