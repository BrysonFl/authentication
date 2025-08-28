package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase implements IUserUseCase {

    private final UserRepository repository;

    public Mono<User> saveUser(User user) {
        return repository.findByEmail(user.getEmail())
                .flatMap(data -> Mono.<User>error(new RuntimeException("El correo " + data.getEmail() + " ya existe en el sistema")))
                .switchIfEmpty(repository.save(user));
    }

    @Override
    public Mono<User> findByIdentificationNumber(String identificationNumber) {
        return repository.findByDocumentNumber(identificationNumber);
    }
}
