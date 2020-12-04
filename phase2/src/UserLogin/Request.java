package UserLogin;

/**
 * A class that represents a request.
 */

public class Request {   // Made request into it's own class but can change if it doesn't work
    private String email;
    private String request;

    /**
     * An email and a request is required to create an instance of Request.
     *
     * @param email a String representing the email of the user who sent this request
     * @param request a String describing the request
     */

    public Request(String email, String request) {
        this.email = email;
        this.request = request;
    }

    /**
     * Returns the email of the user who sent this request.
     *
     * @return a String representing an email address
     */

    public String getEmail() {
        return email;
    }

    /**
     * Returns the request.
     *
     * @return a String describing the request
     */

    public String getRequest() {
        return request;
    }
}
