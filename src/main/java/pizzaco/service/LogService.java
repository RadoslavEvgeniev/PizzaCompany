package pizzaco.service;

import org.springframework.messaging.Message;

public interface LogService {

    void addEvent(Message<String> event);
}
