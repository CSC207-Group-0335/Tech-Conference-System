package Schedule;

public class SpeakerSchedulePresenter {

    public void printSchedule(SpeakerScheduleManager speaker, TalkManager talkManager){
        for(Talk t: speaker.getTalkList()) {
            System.out.println(talkManager.toStringTalk(t));
        }
    }

    public void printHelloMessage(SpeakerScheduleManager speaker){
        System.out.println("Hello " + speaker.speaker.getName() + ", ");
        System.out.println("Press 1 to view the list of talks you are planned to give");
        System.out.println("Press 0 to go back to the main menu");
    }

    public void printGoodbye(){
        System.out.println("Returning to main menu...");
    }

public void printTryAgain(){
    System.out.println("Invalid command, please try again.");
}

public void printNoTalks(){
    System.out.println("You are not currently booked to give any talks at the conference.");
}
}

