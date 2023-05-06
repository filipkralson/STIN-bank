package cz.tul.kral.bank.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AuthenticationManager {
    private Map<String, User> users = new HashMap<>();
    private Map<User, String> passwords = new HashMap<>();
    private Set<User> authenticatedUsers = new HashSet<>();

    public void authenticateUser(User user, String password, String verificationCode) {
        if (!users.containsValue(user)) {
            throw new IllegalArgumentException("Invalid user");
        }

        String correctPassword = passwords.get(user);
        if (!correctPassword.equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        // Verify the user's identity using a second factor (e.g. SMS or TOTP)
        boolean isVerified = verifyUser(user, verificationCode);
        if (!isVerified) {
            throw new IllegalArgumentException("Invalid verification code");
        }

        authenticatedUsers.add(user);
    }

    public boolean isUserAuthenticated(User user) {
        return authenticatedUsers.contains(user);
    }

    private boolean verifyUser(User user, String verificationCode) {
        // Perform the second factor verification
        // ...
        return true; // or false if the verification fails
    }
}
