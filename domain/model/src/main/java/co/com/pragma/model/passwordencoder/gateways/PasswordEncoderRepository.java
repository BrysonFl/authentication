package co.com.pragma.model.passwordencoder.gateways;

public interface PasswordEncoderRepository {

    String passwordEncoder(String password);
    Boolean validatePassword(String rawPassword, String encodedPassword);

}
