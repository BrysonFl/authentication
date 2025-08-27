package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserUseCase implements IUserUseCase {

    private final UserRepository repository;

    public void saveUser(User user) {
        repository.save(user);
    }

}
