package com.banking.backend.Banking_Backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String role;     // USER / ADMIN
    private String address;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dob;
    private List <AccountDTO> accounts;

    public UserDTO(Long userId, String name, String email, String phoneNumber) {
        this.id = userId;
        this.name = name;
        this.email = email;
        this.phone = phoneNumber;
    }
    public UserDTO(Long id, String name, String email, String phone, String role, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.address = address;
    }

}
