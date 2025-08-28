package co.com.pragma.api;

import co.com.pragma.api.dto.CreateUserDTO;
import co.com.pragma.api.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Bean
    @RouterOperations({
        @RouterOperation(
            path = "/api/v1/usuarios",
            method = RequestMethod.POST,
            beanClass = Handler.class,
            beanMethod = "save",
            operation = @Operation(
                summary = "Create a user",
                requestBody = @RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreateUserDTO.class))
                ),
                responses = {
                    @ApiResponse(
                        responseCode = "200",
                        description = "User created",
                        content = @Content(schema = @Schema(implementation = UserDTO.class))
                    ),
                    @ApiResponse(
                        responseCode = "400",
                        description = "Bad request"
                    )
                }
            )
        )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return nest(
                path("/api/v1/usuarios"),
                route(
                    POST("/"), handler::save)
                    .andRoute(GET("{identification}"), handler::findByIdentificationNumber)
        );
    }

}
