package co.com.pragma.api.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValidationUtil<T> {

    private final Validator validator;

    public Mono<T> validate(T t) {
        Set<ConstraintViolation<T>> violations = validator.validate(t);

        if (!violations.isEmpty())
            return Mono.error(() -> new RuntimeException(violations.iterator().next().getMessage()));

        return Mono.just(t);
    }

}
