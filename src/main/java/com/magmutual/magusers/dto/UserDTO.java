package com.magmutual.magusers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.annotation.Aspect;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotNull
    private int id;

    @NotEmpty(message = "First name cannot be null or empty.")
    @Size(min = 5, max = 50)
    private String firstName;

    private String lastName;

    @Email
    private String email;

    private String profession;

    // This field is managed automatically by the service layer.
    private LocalDate dateCreated;

    private String country;

    private String city;

}
