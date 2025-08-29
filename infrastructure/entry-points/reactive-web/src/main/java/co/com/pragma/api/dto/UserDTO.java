package co.com.pragma.api.dto;

import java.math.BigInteger;
import java.time.LocalDate;

public record UserDTO(BigInteger id, String documentNumber, String name, String lastname, LocalDate dateBirthday, String address, String phone, String email, Integer baseSalary) {
}
