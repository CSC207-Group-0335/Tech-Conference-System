package Schedule;

import UserLogin.User;

import java.util.ArrayList;

public class SignUpAttendeesManager {
    public Talk talk;
    public ArrayList<User> userList;
    public int roomCapacity;

    public SignUpAttendeesManager(Talk talk, int roomCapacity){
        this.talk = talk;
        this.roomCapacity = roomCapacity;
        this.userList = new ArrayList<User>();
    }

    public boolean addUser(User user){
        if (!userList.contains(user) && userList.size()+1 <= roomCapacity){
            userList.add(user);
            return true;
        }
        else{
            return false;
        }
    }

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
