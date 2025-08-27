package co.com.pragma.api;

import co.com.pragma.api.dto.CreateUserDTO;
import co.com.pragma.api.dto.UserDTO;
import co.com.pragma.api.mapper.UserMapper;
import co.com.pragma.usecase.user.IUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final UserMapper mapper;
    private final IUserUseCase userUseCase;

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateUserDTO.class)
                .doOnNext(dto -> userUseCase.saveUser(mapper.toUser(dto)))
                .then(ServerResponse.ok().build());
    }

}
