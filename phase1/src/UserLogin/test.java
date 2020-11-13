package UserLogin;

public class test {

    public static void main(String[] args) {
        User nathan = new Attendee("Nathan", "coding123", "nathan@mail.com");
        TechConferenceSystem techSystem = new TechConferenceSystem(nathan);

        techSystem.setUserStorage("Attendee", "Nathan", "coding123", "nathan@mail.com");
        System.out.println(techSystem.userStorage.getUserList());
        System.out.println(techSystem.logInController.logInManager.userList);
    }
}
