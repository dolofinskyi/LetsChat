package ua.dolofinskyi.letschat.features.user;

public class UserFoundException extends Exception {
    public UserFoundException() {
        super("User already exists.");
    }
}
