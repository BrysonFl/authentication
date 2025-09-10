package co.com.pragma.api;

import co.com.pragma.api.dto.CreateUserDTO;
import co.com.pragma.api.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.*;

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
                operationId = "save",
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
        ),
        @RouterOperation(
            path = "/api/v1/usuarios/identification",
            method = RequestMethod.GET,
            beanClass = Handler.class,
            beanMethod = "findByIdentificationNumber",
            operation = @Operation(
                operationId = "findByIdentificationNumber",
                summary = "Find user by dni",
                parameters = {
                    @Parameter(
                        name = "identification"
                    )
                },
                responses = {
                    @ApiResponse(
                        responseCode = "200",
                        description = "User found",
                        content = @Content(schema = @Schema(implementation = UserDTO.class))
                    )
                }
            )
        )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return RouterFunctions.nest(RequestPredicates.path("/api/v1/usuarios"), RouterFunctions.route()
                .POST("", handler::save)
                .GET("{identification}", handler::findByIdentificationNumber)
                .POST("login", handler::login)
                .build());
    }

}
