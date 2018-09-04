package pizzaco.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pizzaco.common.Constants;
import pizzaco.domain.entities.Log;
import pizzaco.domain.entities.User;
import pizzaco.domain.models.service.LogServiceModel;
import pizzaco.repository.LogRepository;
import pizzaco.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LogServiceImpl(LogRepository logRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @JmsListener(destination = "message-queue")
    public void addEvent(Message<String> event) {
        Log logEntity = this.prepareLogEntity(event.getPayload().split(";"));

        this.logRepository.save(logEntity);
    }

    @Override
    public List<LogServiceModel> getLogsOrderedByDate() {
        return this.logRepository.findAllOrderedByDateDesc()
                .stream()
                .map(log ->{
                    LogServiceModel logServiceModel = this.modelMapper.map(log, LogServiceModel.class);
                    logServiceModel.getUser().setEmail(log.getUser().getUsername());

                    return logServiceModel;
                })
                .collect(Collectors.toList());
    }

    private Log prepareLogEntity(String[] eventParams) {
        Log logEntity = new Log();
        logEntity.setDateTime(this.formatDate(eventParams[0]));

        User userEntity = this.userRepository.findByUsername(eventParams[1]).orElse(null);

        this.checkUserExistence(userEntity);

        logEntity.setUser(userEntity);
        logEntity.setEvent(eventParams[2]);

        return logEntity;
    }

    private LocalDateTime formatDate(String dateTimeStr) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return LocalDateTime
                .parse(dateTimeStr.replace("T", " ")
                        .substring(0, dateTimeStr.lastIndexOf(".")), format);
    }

    private void checkUserExistence(User userEntity) {
        if (userEntity == null) {
            throw new UsernameNotFoundException(Constants.WRONG_NON_EXISTENT_EMAIL);
        }
    }
}
