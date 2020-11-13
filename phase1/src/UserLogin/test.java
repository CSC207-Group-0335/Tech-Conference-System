package UserLogin;

public class test {

    public static void main(String[] args) {
        AccountSystem accountSystem = new AccountSystem();
        LogInManager logInManager = new LogInManager();

        accountSystem.setUserStorage("Attendee", "Nathan", "coding123", "nathan@mail.com");
        System.out.println(accountSystem.userStorage.getUserList());
        System.out.println(logInManager.userList);
    }
}
