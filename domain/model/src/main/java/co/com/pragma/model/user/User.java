package co.com.pragma.model.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class User {

    private String name;
    private String lastname;
    private LocalDate dateBirthday;
    private String address;
    private String phone;
    private String email;
    private Integer baseSalary;

}
