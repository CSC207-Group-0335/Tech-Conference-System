package UserLogin;

import java.util.ArrayList;
import java.util.HashMap;
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
     * A constructor for an Attendee
     *
     * @param name     the name of the Attendee
     * @param password the password of the Attendee
     * @param email    the email of the Attendee
     */
    public Attendee(String name, String password, String email) {
        super(name, password, email);
        this.VIP = false;
        //this.requests = new LinkedHashMap<>();
        //testing purposes
        //this.setRequests("handicap");
    }

    public Attendee(String name, String password, String email, boolean VIP, LinkedHashMap<String, String> requestMap) {
        super(name, password, email);
        this.VIP = VIP;
        this.setupRequests(requestMap);
        //this.requests = requestMap;
        //this.requests = new LinkedHashMap<>();
        //testing purposes
        //this.setRequests("vegan");
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

    public void setVIPStatus(boolean bool){
        this.VIP = bool;
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
     * Returns true if and only if the requests have been set.
     *
     * @param requests an ArrayList containing multiple requests
     * @return a boolean representing whether or not the requests have been set
     */

    public boolean setRequests(ArrayList<String> requests) {
        for (String req : requests) {
            this.requestMap.put(req, "pending");
        }
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
     * Returns true if each request exists and has been approved. Returns false otherwise.
     *
     * @param requests an ArrayList containing multiple requests
     * @return a boolean representing whether or not the requests have been successfully approved
     */

    public boolean requestCompleteAll(ArrayList<String> requests) {
        for (String req : requests) {
            if (!this.requestMap.containsKey(req)) {
                return false;
            }
            else {
                this.requestMap.put(req, "approved");
            }
        }
        return true;
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
     * Returns true if each request exists and has been denied. Returns false otherwise.
     *
     * @param requests an ArrayList containing multiple requests
     * @return a boolean representing whether or not the requests have been successfully refused
     */

    public boolean requestDenyAll(ArrayList<String> requests) {
        for (String req : requests) {
            if (!this.requestMap.containsKey(req)) {
                return false;
            }
            else {
                this.requestMap.put(req, "rejected");
            }
        }
        return true;
    }

    /**
     * Returns the number of requests this attendee has.
     *
     * @return an int representing the amount of requests
     */

    public int getNumberOfRequests() {
        return requestMap.size();
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
            String key = entry.getKey();
            String value = entry.getValue();
            // now work with key and value...
            if (value.equals("pending")){
                pendingLeft = pendingLeft + 1;
            }
        }
        return pendingLeft;
    }
}