package UserLogin;

import java.util.ArrayList;

/**
 * A class that represents an Attendee, a specific type of User that attends the conference and can sign up to
 * attend Talks.
 */

public class Attendee extends User {
    public boolean VIP;
    //make it a list of strings for multiple requests?
    // strings are in format of "request, status", with status only being one of two "pending" or "addressed"
    public ArrayList<String> requests;

    /**
     * A constructor for an Attendee
     *
     * @param name     the name of the Attendee
     * @param password the password of the Attendee
     * @param email    the email of the Attendee
     */
    public Attendee(String name, String password, String email) {
        super(name, password, email);
        this.VIP = false;
        this.requests = new ArrayList<>();
    }

    public Attendee(String name, String password, String email, boolean VIP) {
        super(name, password, email);
        this.VIP = VIP;
        this.requests = new ArrayList<>();
    }


    /**
     * A method used to get the type of User.
     *
     * @return a string specifying the type of the User.
     */
    @Override
    public String getType() {
        return "Attendee";
    }

    public boolean getVIPStatus() {
        return this.VIP;
    }

    public ArrayList<String> getRequests() {
        return this.requests;
    }

    public boolean setRequests(String request) {
        String toAdd = request + ", pending";
        this.requests.add(toAdd);
        return true;
    }

    public boolean setRequests(ArrayList<String> requests) {
        for (String req : requests) {
            String toAdd = req + ", pending";
            this.requests.add(toAdd);
        }
        return true;
    }

    public boolean requestComplete(String request) {
        int i = 0;
        for (String req : this.requests) {
            if (request.equals(req)) {
                int indexOfComma = req.indexOf(",");
                String content = req.substring(0, indexOfComma);
                String toAdd = content + ", addressed";
                this.requests.set(i, toAdd);
            }
            i++;
        }
        return true;
    }
    public boolean requestCompleteAll(String request) {
        int i = 0;
        for (String req : this.requests) {
            int indexOfComma = req.indexOf(",");
            String content = req.substring(0, indexOfComma);
            String toAdd = content + ", addressed";
            this.requests.set(i, toAdd);
            i++;
        }
        return true;
    }
}
