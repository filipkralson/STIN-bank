package cz.tul.kral.bank.model;

public class User {
    private int clientNum;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User(int clientNum, String firstName, String lastName, String email, String password) {
        this.clientNum = clientNum;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getClientNum() {
        return clientNum;
    }

    public String getPassword() {
        return password;
    }
}

