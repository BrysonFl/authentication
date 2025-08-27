package co.com.pragma.api.dto;

import java.time.LocalDate;

public record CreateUserDTO(String name, String lastname, LocalDate dateBirthday, String address, String phone, String email, Integer baseSalary) {
}
