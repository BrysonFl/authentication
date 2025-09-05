package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.LoggerGateway;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.usecase.user.exceptions.BusinessValidationException;
import co.com.pragma.usecase.user.exceptions.ExistUserException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase implements IUserUseCase {

    private final UserRepository repository;

    private final LoggerGateway logger;

    public Mono<User> saveUser(User user) {
        return repository.findByEmail(user.getEmail())
            .flatMap(data -> Mono.error(new ExistUserException("El correo " + data.getEmail() + " ya existe en el sistema")))
            .cast(User.class)
            .switchIfEmpty(Mono.defer(() -> {
                if (user.getBaseSalary() < 0 || user.getBaseSalary() > 15000000) {
                    logger.logError("Valida el campo salario base, esta fuera de los rangos permitidos: {}", user.getBaseSalary());
                    return Mono.error(new BusinessValidationException("Valida el campo salario base, esta fuera de los rangos permitidos"));
                }

                return repository.save(user);
            }))
            .onErrorResume(ex -> Mono.error(new RuntimeException("Error al registrar el usuario: " + ex.getMessage())));
    }

    @Override
    public Mono<User> findByIdentificationNumber(String identificationNumber) {
        return repository.findByDocumentNumber(identificationNumber);
    }
}
