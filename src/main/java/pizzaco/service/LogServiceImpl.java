package pizzaco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pizzaco.domain.entities.Log;
import pizzaco.domain.entities.User;
import pizzaco.repository.LogRepository;
import pizzaco.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final UserRepository userRepository;

    @Autowired
    public LogServiceImpl(LogRepository logRepository, UserRepository userRepository) {
        this.logRepository = logRepository;
        this.userRepository = userRepository;
    }

    @Override
    @JmsListener(destination = "message-queue")
    public void addEvent(Message<String> event) {
        String[] eventParams = event.getPayload().split(";");

        Log log = new Log();
        log.setDateTime(this.formatDate(eventParams[0]));

        User userEntity = this.userRepository.findByUsername(eventParams[1]).orElse(null);

        if (userEntity == null) {
            throw new UsernameNotFoundException("Wrong or non-existent email.");
        }

        log.setUser(userEntity);
        log.setEvent(eventParams[2]);

        this.logRepository.save(log);
    }

    private LocalDateTime formatDate(String dateTimeStr) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return LocalDateTime
                .parse(dateTimeStr.replace("T", " ").substring(0, dateTimeStr.lastIndexOf(".")), format);
    }
}
