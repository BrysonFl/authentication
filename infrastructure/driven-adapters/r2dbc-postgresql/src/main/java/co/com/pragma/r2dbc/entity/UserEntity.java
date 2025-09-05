package co.com.pragma.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
@Table("users")
@ToString
public class UserEntity {

    @Id
    private BigInteger id;
    private String documentNumber;
    private String name;
    private String lastname;
    private LocalDate dateBirthday;
    private String address;
    private String phone;
    private String email;
    private Integer baseSalary;

    @Column("id_role")
    private Integer role;

}
