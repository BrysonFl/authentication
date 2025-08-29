package co.com.pragma.api;

import co.com.pragma.api.dto.CreateUserDTO;
import co.com.pragma.api.mapper.UserMapper;
import co.com.pragma.api.util.ValidationUtil;
import co.com.pragma.usecase.user.IUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class Handler {

    private final UserMapper mapper;
    private final IUserUseCase userUseCase;

    private final ValidationUtil<CreateUserDTO> validationUtil;

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateUserDTO.class)
            .flatMap(validationUtil::validate)
            .flatMap(dto -> userUseCase.saveUser(mapper.toUser(dto)))
            .flatMap(user -> ServerResponse.created(URI.create("")).bodyValue(mapper.toUserDTO(user))
            .onErrorResume(error -> ServerResponse.badRequest().bodyValue(error)));
    }

    public Mono<ServerResponse> findByIdentificationNumber(ServerRequest serverRequest) {
        return userUseCase.findByIdentificationNumber(serverRequest.pathVariable("identification"))
                .flatMap(user -> ServerResponse.ok().bodyValue(mapper.toUserDTO(user)));
    }

}
