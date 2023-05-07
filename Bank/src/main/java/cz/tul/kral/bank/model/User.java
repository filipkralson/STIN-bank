package cz.tul.kral.bank.model;

import jakarta.persistence.*;
@Entity
public class User {
    @Id
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String _email) {
        this.email = _email;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String _password) {
        this.password = _password;
    }
}

