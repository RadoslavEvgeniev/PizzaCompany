package pizzaco.service;

import org.springframework.messaging.Message;
import pizzaco.domain.models.service.LogServiceModel;

import java.util.List;

public interface LogService {

    void addEvent(Message<String> event);

    List<LogServiceModel> getLogsOrderedByDate();
}
