package co.com.pragma.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record CreateUserDTO(
        String documentNumber,
        @NotNull(message = "El campo nombre no debe ser nulo")
        @NotEmpty(message = "El campo nombre no debe ser vacío")
        String name,
        @NotNull(message = "El campo apellido no debe ser nulo")
        @NotEmpty(message = "El campo apellido no debe ser vacío")
        String lastname, LocalDate dateBirthday, String address, String phone,
        @NotNull(message = "El campo correo no debe ser nulo")
        @NotEmpty(message = "El campo correo no debe ser vacío")
        String email,
        @NotNull(message = "El campo salario base no debe ser nulo")
        Integer baseSalary,
        @NotNull(message = "El campo rol no debe ser nulo")
        @Positive(message = "El campo rol debe tener un valor válido")
        Integer role) {
}
