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

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserUseCase userUseCase;

    private User user;

    @BeforeEach
    void init() {
        user = new User();
        user.setId(BigInteger.ONE);
        user.setDocumentNumber("1");
        user.setEmail("test@test.com");
    }

    @Test
    void testEmailAlreadyExists() {
        when(repository.findByEmail(anyString())).thenReturn(Mono.just(user));

        StepVerifier.create(userUseCase.saveUser(user))
                .expectError()
                .verify();

        verify(repository, times(1)).findByEmail(user.getEmail());
        verify(repository, never()).save(any());
    }

    @Test
    void testEmailDoesntExist() {
        user.setBaseSalary(1);
        when(repository.findByEmail(anyString())).thenReturn(Mono.empty());
        when(repository.save(any())).thenReturn(Mono.just(user));

        StepVerifier.create(userUseCase.saveUser(user))
                .assertNext(savedUser -> assertEquals(user, savedUser))
                .verifyComplete();

        verify(repository, times(1)).findByEmail(user.getEmail());
        verify(repository, times(1)).save(any());
    }

    @Test
    void testValidBaseSalary() {
        user.setBaseSalary(-1);
        when(repository.findByEmail(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(userUseCase.saveUser(user))
                .expectErrorMatches(throwable -> throwable.getMessage().contains("Valida el campo salario base"))
                .verify();

        verify(repository, times(1)).findByEmail(user.getEmail());
        verify(repository, never()).save(any());
    }

    @Test
    void testMajorBaseSalary() {
        user.setBaseSalary(15000001);
        when(repository.findByEmail(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(userUseCase.saveUser(user))
                .expectErrorMatches(throwable -> throwable.getMessage().contains("Valida el campo salario base"))
                .verify();

        verify(repository, times(1)).findByEmail(user.getEmail());
        verify(repository, never()).save(any());
    }

    @Test
    void testFindIdentificationNumber() {
        String dni = "1";

        when(repository.findByDocumentNumber(anyString())).thenReturn(Mono.just(user));

        StepVerifier.create(userUseCase.findByIdentificationNumber(dni))
                .assertNext(userFound -> assertEquals(user, userFound))
                .verifyComplete();

        verify(repository, times(1)).findByDocumentNumber(user.getDocumentNumber());
    }

}
