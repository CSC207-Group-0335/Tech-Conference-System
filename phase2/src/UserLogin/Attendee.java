package UserLogin;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A class that represents an Attendee, a specific type of User that attends the conference and can sign up to
 * attend events.
 */

public class Attendee extends User {
    public boolean VIP;
    //make it a list of strings for multiple requests?
    // strings are in format of "request, status", with status only being one of two "pending" or "addressed"
    //public LinkedHashMap<String, String> requests;

    /**
     * A constructor for an Attendee.
     * @param name the name of the attendee
     * @param password the password of the attendee
     * @param email the email of the attendee
     * @param VIP a boolean representing whether or not this attendee is of VIP status
     * @param requestMap a HashMap containing this attendee's requests
     */

    public Attendee(String name, String password, String email, boolean VIP, LinkedHashMap<String, String> requestMap) {
        super(name, password, email);
        this.VIP = VIP;
        this.setupRequests(requestMap);
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

    /**
     * Returns true if and only if this user is VIP.
     *
     * @return a Boolean representing whether or not this user is VIP
     */

    public boolean getVIPStatus() {
        return this.VIP;
    }

    /**
     * Returns requests and their statuses (pending, approved, or rejected).
     *
     * @return a HashMap containing requests and their statuses
     */

    public LinkedHashMap<String, String> getRequests() {
        return this.requestMap;
    }

    /**
     * Returns true if and only if the request has been set.
     *
     * @param request a String representing the request
     * @return a boolean representing whether or not the request has been set
     */

    public boolean setRequests(String request) {
        requestMap.put(request, "pending");
        return true;
    }

    /**
     * Returns true if the request exists and has been approved. Returns false otherwise.
     *
     * @param request a String representing the request
     * @return a boolean representing whether or not the request has been successfully approved
     */

    public boolean requestComplete(String request) {
        if (requestMap.containsKey(request)) {
            requestMap.put(request, "approved");
            return true;
        }
        return false;
    }

    /**
     * Returns true if the request exists and has been refused. Returns false otherwise.
     *
     * @param request a String representing the request
     * @return a boolean representing whether or not the request has been successfully refused
     */

    public boolean requestDeny(String request) {
        if (requestMap.containsKey(request)) {
            requestMap.put(request, "rejected");
            return true;
        }
        return false;
    }

    /**
     * Returns the number of pending requests this attendee has.
     *
     * @return an int representing the number of pending requests
     */

    public int getNumberOfPending(){
        int pendingLeft = 0;
        LinkedHashMap<String, String> userRequests = this.getRequests();
        for (Map.Entry<String, String> entry : userRequests.entrySet()) {
            String value = entry.getValue();
            // now work with key and value...
            if (value.equals("pending")){
                pendingLeft = pendingLeft + 1;
            }
        }
        return pendingLeft;
    }
}