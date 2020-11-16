package Schedule;

import UserLogin.Speaker;
import UserLogin.User;

import java.util.ArrayList;

public class OrgSchedulePresenter extends UserSchedulePresenter{
    public void printMenu(int i){
        if (i==1){
            System.out.println("Welcome to the scheduling Menu" + System.lineSeparator() +
                    "Press 1 to register for a talk " + System.lineSeparator() +
                    "Press 2 to see the schedule of events " + System.lineSeparator() +
                    "Press 3 see all talks currently registered for " + System.lineSeparator() +
                    "Press 4 to  cancel a registration" + System.lineSeparator()+
                    "Press 5 to add a talk " + System.lineSeparator() +
                    "Press 6 to add a room " + System.lineSeparator() +
                    "Press 7 to add a speaker" + System.lineSeparator()+
                    "Press 0 to go back to the main menu");
        } else if (i==2){
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
        } else if (i==6) {
            System.out.println("What new talk would you like to add to the schedule of events?" +
                    "Enter the number corresponding " +
                    "to each talk" + System.lineSeparator() +
                    "Press 0 to go back to the scheduling menu");
        } else if (i==7){
            System.out.println("Pick room by Index, press 0 to go back");
        } else if (i == 8){
            System.out.println("Pick speaker by Index, press 0 to go back");
        }
        else if (i == 9){
            System.out.println("Success");
        }
        else if (i==10){
            System.out.println("Invalid talk number, try again");
        }
        else if (i == 11){
            System.out.println("Invalid room number, try again");
        }
        else if (i == 12){
            System.out.println("Invalid speaker number, try again");
        }
        else if (i==13){
            System.out.println("Invalid command, try again");
        }
        else if (i==14){
            System.out.println("Returning to main menu...");
        }
        else if (i==15){
            System.out.println("Returning to scheduling menu...");
        }
    }


    public void printAllSpeakers(ArrayList<Speaker> speakerList) {
        //print index + 1
        Integer i = 1;
        for (Speaker s : speakerList){
            System.out.println(Integer.toString(i) + ")" + s.getName());
            i++;
        }
    }
    public void printAllRooms(ArrayList<Room> roomList){
        Integer i = 1;
        for (Room r : roomList){
            System.out.println(Integer.toString(i) + ")" + r.getRoomName());
            i++;
        }
    }
}

