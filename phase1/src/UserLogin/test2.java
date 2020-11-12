package UserLogin;

import java.util.Observable;
import java.util.Observer;

public class test2 extends Observable {
    private String message;

    public String getMessage() {
        return message;
    }
    public void changeMessage( String message ) {
        this.message = message;
        setChanged();
        notifyObservers( message );
    }
    public static void main( String [] args ) {
        test2 board = new test2();
        Student bob = new Student();
        Student joe = new Student();
        board.addObserver( bob );
        board.addObserver( joe );
        board.changeMessage("More Homework!");
    }
} // end of class MessageBoard

class Student implements Observer {
    public void update(Observable o, Object arg) {
        System.out.println( "Message board changed: " + arg );
    }
}