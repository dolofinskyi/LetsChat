package ua.dolofinskyi.letschat.features.user;

public enum UserStatus {
    ONLINE("Online"),
    OFFLINE("Offline");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
