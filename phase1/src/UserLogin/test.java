package UserLogin;

public class test {

    public static void main(String[] args) {
        AccountSystem accountSystem = new AccountSystem();

        accountSystem.setUserStorage("Attendee", "Nathan", "coding123", "nathan@mail.com");
        System.out.println(accountSystem.userStorage.getUserList());
        System.out.println(accountSystem.logInController.logInManager.userList);
    }
}
