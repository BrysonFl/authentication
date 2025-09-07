package co.com.pragma.model.user;

import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class User {

    private BigInteger id;
    private String documentNumber;
    private String name;
    private String lastname;
    private LocalDate dateBirthday;
    private String address;
    private String phone;
    private String email;
    private Integer baseSalary;
    private Integer role;

}
