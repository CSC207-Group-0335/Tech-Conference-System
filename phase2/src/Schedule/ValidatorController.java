package Schedule;

import java.util.Scanner;

/**
 * A controller class representing a Validator.
 */

public class ValidatorController {
    SchedulePresenter presenter;

    /**
     * Initiates a ValidatorController object.
     */

    public ValidatorController(){
        this.presenter = new SchedulePresenter();
    }

    /**
     * Validates a String.
     */
    
    public String userStringInputValidation(String menu, String invalid, Scanner scanner){
            String choice = scanner.nextLine();
            try { int command = Integer.parseInt(choice);
                if (command == 0){
                    presenter.printGoodbye(menu);
                    return "Zero";
                }
                else{presenter.printTryAgain(invalid);
                }
            }catch (NumberFormatException nfe){
                return choice;}
    return null;}

    /**
     * Validates an Integer.
     */

    public Integer userIntInputValidation(String menu, String invalid, Scanner scanner){
            String choice = scanner.nextLine();
            try { Integer command = Integer.parseInt(choice);
                if (command == 0){
                    presenter.printGoodbye(menu);
                    return 0;
                }
                else{return command;}
            }catch (NumberFormatException nfe){
                presenter.printTryAgain(invalid);;}
    return null;}
}

