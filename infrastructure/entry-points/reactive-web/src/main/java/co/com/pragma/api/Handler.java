package co.com.pragma.api;

import co.com.pragma.api.dto.CreateUserDTO;
import co.com.pragma.api.exceptions.ExistUserException;
import co.com.pragma.api.exceptions.RequiredFieldsException;
import co.com.pragma.api.mapper.UserMapper;
import co.com.pragma.usecase.user.IUserUseCase;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Handler {

    private final UserMapper mapper;
    private final IUserUseCase userUseCase;
    private final Validator validator;

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateUserDTO.class)
            .flatMap(dto -> {
                Set<ConstraintViolation<CreateUserDTO>> violations = validator.validate(dto);

                if (!violations.isEmpty())
                    return Mono.error(new RequiredFieldsException(violations.iterator().next().getMessage()));

                return userUseCase.saveUser(mapper.toUser(dto));
            })
            .flatMap(user -> ServerResponse.created(URI.create("")).bodyValue(mapper.toUserDTO(user)));
    }

}
