package co.com.pragma.model.user.gateways;

public interface LoggerGateway {

    void logInfo(String message, Object obj);
    void logError(String message, Object obj);

}
