package co.com.pragma.api.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateUserDTO(@NotNull(message = "El campo nombre no debe ser null") @NotEmpty(message = "El campo nombre no debe estar vacío") String name,
                            @NotNull(message = "El campo apellido no debe ser null") @NotEmpty(message = "El campo apellido no debe estar vacío") String lastname,
                            LocalDate dateBirthday, String address, String phone,
                            @NotNull(message = "El campo correo no debe ser null")
                            @NotEmpty(message = "El campo correo no debe estar vacío")
                            @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El campo correo debe tener un formato válido")
                            String email,
                            @NotNull(message = "El campo salario base no debe ser null")
                            @PositiveOrZero(message = "El salario base no puede ser menor a 0")
                            @Max(value = 15000000, message = "El salario base no puede ser mayor a 15000000")
                            Integer baseSalary) {
}
