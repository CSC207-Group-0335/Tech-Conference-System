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
    }

    public void printTalk(Integer num, Talk t, TalkManager talkManager){
        System.out.println(num.toString() + ") " + talkManager.toStringTalk(t));
    }

    public void printAllTalks(TalkManager talkManager){
        System.out.println(talkManager.talkMapStringRepresentation());
    }

    public void printHello(UserScheduleManager userScheduleManager){
        System.out.println("Hello " + userScheduleManager.getUser().getName());
    }
    public void printRegistrationBlocked(int i){
        if (i==1){
            System.out.println("You have already registered for this talk," +System.lineSeparator()+
                    "If you would like to register for another talk, please enter the corresponding number.");
        }
        else if (i ==2){
            System.out.println("Event is at full capacity"+System.lineSeparator()+
                    "If you would like to register for another talk, please enter the corresponding number.");
        }
    }
}