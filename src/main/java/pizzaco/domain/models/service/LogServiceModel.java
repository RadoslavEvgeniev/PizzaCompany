package pizzaco.domain.models.service;

import java.time.LocalDateTime;

public class LogServiceModel {

    private LocalDateTime dateTime;
    private UserServiceModel user;
    private String event;

    public LogServiceModel() {
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public UserServiceModel getUser() {
        return this.user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public String getEvent() {
        return this.event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
