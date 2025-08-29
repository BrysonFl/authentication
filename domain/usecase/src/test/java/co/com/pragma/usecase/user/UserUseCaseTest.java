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
    private UserUseCase userUseCase;

    //private User user;

    /*@BeforeEach
    void init() {
        user = new User();
        user.setEmail("test@test.com");
    }*/

    @Test
    void shouldReturnErrorWhenEmailAlreadyExists() {
        User user = new User();
        user.setEmail("test@test.com");

        Mono<User> userMono = Mono.just(user);
        when(repository.findByEmail(anyString())).thenReturn(user);
        //when(userUseCase.saveUser(user)).thenReturn(userMono);

        StepVerifier.create(userUseCase.saveUser(user))
                .expectError()
                .verify();

        //verify(repository, times(1)).findByEmail(user.getEmail());
        //verify(repository, never()).save(any());
    }

}
