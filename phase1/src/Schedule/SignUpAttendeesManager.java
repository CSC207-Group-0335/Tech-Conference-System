package Schedule;

import UserLogin.User;

import java.util.ArrayList;

/**
 * Stores all the users attending a particular talk.
 */
public class SignUpAttendeesManager {
    /**
     * The talk being stored.
     */
    public Talk talk;
    /**
     * The list of users attending the talk.
     */
    public ArrayList<User> userList;
    /**
     * The capacity of the room.
     */
    public int roomCapacity;

    /**
     * Creates a SignUpAttendeesManager with the specified talk and room capacity.
     * @param talk
     * @param roomCapacity
     */
    public SignUpAttendeesManager(Talk talk, int roomCapacity){
        this.talk = talk;
        this.roomCapacity = roomCapacity;
        this.userList = new ArrayList<User>();
    }

    /**
     * Adds a user to userList.
     * @param user The user being added.
     * @return A boolean notifying if the user has been successfully added.
     */
    public boolean addUser(User user){
        if (!userList.contains(user) && userList.size()+1 <= roomCapacity){
            userList.add(user);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Removes a user from userList.
     * @param user The user being removed.
     * @return A boolean notifying if the user has been successfully removed.
     */
    public boolean removeUser(User user){
        if (userList.contains(user)){
            userList.remove(user);
            return true;
        }
        else{
            return false;
        }
    }




}
