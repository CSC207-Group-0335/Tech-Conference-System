package UserLogin;

public abstract class User {
    private String name;
    private String password;
    private String email;

    public User(String name, String password, String email){
        this.name = name;
        this.password = password;
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
