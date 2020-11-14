package Schedule;

public class Presenter {
    public void print(int i) {
        if (i == 1) {
            System.out.println("1. register for a talk, 2. see the guest list, 3. see all talks currently registered, " +
                    "4. cancel a registration");
        } else if (i == 2) {
            System.out.println("Please input a command");
        } else if (i == 3) {
            System.out.println("What event would you like to register for?");
        } else if (i == 4) {
            System.out.println("Not a valid talk");
        } else if (i == 5) {
            System.out.println("What event would you like to cancel for?");
        }
    }
    public void print(String s){
        System.out.println(s);
    }
}