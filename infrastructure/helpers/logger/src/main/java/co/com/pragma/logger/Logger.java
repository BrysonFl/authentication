package co.com.pragma.logger;

import co.com.pragma.model.user.gateways.LoggerGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Logger implements LoggerGateway {

    @Override
    public void logInfo(String message, Object obj) {
        log.info(message, obj);
    }

    @Override
    public void logError(String message, Object obj) {
        log.error(message, obj);
    }
}
