package co.com.pragma.api;

import co.com.pragma.api.dto.CreateUserDTO;
import co.com.pragma.api.dto.UserDTO;
import co.com.pragma.api.mapper.UserMapper;
import co.com.pragma.api.util.ValidationUtil;
import co.com.pragma.model.user.User;
import co.com.pragma.usecase.user.IUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {RouterRest.class, Handler.class})
@WebFluxTest
@ExtendWith(MockitoExtension.class)
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private UserMapper userMapper;

    @MockitoBean
    private ValidationUtil<CreateUserDTO> validationUtil;

    @MockitoBean
    private IUserUseCase userUseCase;

    private CreateUserDTO request;
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void init() {
        request = new CreateUserDTO("123", "Test", "Test", LocalDate.of(1996, 10, 9), "test",
                "123", "test@test.com", 123);

        user = new User(BigInteger.ONE, "123", "Test", "Test", LocalDate.of(1996, 10, 9), "test",
                "123", "test@test.com", 123);

        userDTO = new UserDTO(BigInteger.ONE, "123", "Test", "Test", LocalDate.of(1996, 10, 9), "test",
                "123", "test@test.com", 123);
    }

    @Test
    void testSaveOKUser() {
        when(validationUtil.validate(request)).thenReturn(Mono.just(request));
        when(userUseCase.saveUser(user)).thenReturn(Mono.just(user));
        when(userMapper.toUser(request)).thenReturn(user);
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        webTestClient.post()
                .uri("/api/v1/usuarios")
                .body(Mono.just(request), CreateUserDTO.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void testFindByIdentificationNumber() {
        when(userUseCase.findByIdentificationNumber(anyString())).thenReturn(Mono.just(user));
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        webTestClient.get()
                .uri("/api/v1/usuarios/1")
                .exchange()
                .expectBody(UserDTO.class)
                .isEqualTo(userDTO);
    }

}
