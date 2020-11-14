package Schedule;

public class Presenter {
    public void print(int i) {
        if (i == 1) {
            System.out.println("1. register for a talk, 2. see schedule of events, 3. see all talks currently registered, " +
                    "4. cancel a registration");
        } else if (i == 2) {
            System.out.println("Please input a command");
        } else if (i == 3) {
            System.out.println("What event would you like to register for? Enter the number corresponding " +
                    "to each talk");
        } else if (i == 4) {
            System.out.println("Not a valid talk");
        } else if (i == 5) {
            System.out.println("What event would you like to cancel for?");
        } else if (i == 6){
            System.out.println("Success");
        }
    }
    public void print(String s){
        System.out.println(s);
    }
}