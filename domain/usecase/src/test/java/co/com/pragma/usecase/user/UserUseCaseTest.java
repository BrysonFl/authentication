package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserUseCase userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@email.com");
    }

    @Test
    void shouldReturnErrorWhenEmailAlreadyExists() {
        when(repository.findByEmail(user.getEmail())).thenReturn(Mono.just(user));

        Mono<User> result = userService.saveUser(user);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("El correo " + user.getEmail() + " ya existe en el sistema"))
                .verify();

        verify(repository, times(1)).findByEmail(user.getEmail());
        verify(repository, never()).save(any());
    }

    @Test
    void shouldSaveUserWhenEmailDoesNotExist() {
        when(repository.findByEmail(anyString())).thenReturn(Mono.just(user));
        when(repository.save(user)).thenReturn(Mono.just(user));

        Mono<User> result = userService.saveUser(user);

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();

        verify(repository, times(1)).findByEmail(user.getEmail());
        verify(repository, times(1)).save(user);
    }
}
