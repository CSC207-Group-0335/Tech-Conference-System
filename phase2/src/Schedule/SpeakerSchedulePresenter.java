package Schedule;
/**
 * Prints to the console so that the speaker can have something to interact with.
 */
public class SpeakerSchedulePresenter {
    /**
     * Prints the entire schedule of the speaker.
     * @param speaker The Speaker schedule manager.
     * @param eventManager The talkManager.
     */
    public void printSchedule(SpeakerScheduleManager speaker, EventManager eventManager){
        for(Event t: speaker.getTalkList()) {
            System.out.println(eventManager.toStringTalk(t));
        }
    }

    /**
     * Prints a series of phrases to greet the speaker and give instructions on how to navigate the menu.
     * @param speaker The speaker schedule manager.
     */
    public void printHelloMessage(SpeakerScheduleManager speaker){
        System.out.println("Hello " + speaker.speaker.getName() + ", ");
        System.out.println("Press 1 to view the list of talks you are planned to give");
        System.out.println("Press 0 to go back to the main menu");
    }

    /**
     * Prints a message letting the speaker it's returning to the main menu.
     */
    public void printGoodbye(){
        System.out.println("Returning to main menu...");
    }

    /**
     * On an unsuccessful attempt.
     */
    public void printTryAgain(){
    System.out.println("Invalid command, please try again.");
}

    /**
     * If the speaker is not currently booked to give talks.
     */
    public void printNoTalks(){
    System.out.println("You are not currently booked to give any talks at the conference.");
}
}

