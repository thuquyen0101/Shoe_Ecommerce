package com.example.shoesstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserCreateRequest {

    @NotBlank(message = "Username is not empty")
    @Size(min = 4 , max = 50 , message = "Username must be between 4 and 50 characters")
    String username;
    @NotBlank(message = "Password is not empty")
    @Size(min = 4 , max = 50 , message = "Password must be between 4 and 50 characters")
    String password;
    @NotBlank(message = "Name is not empty")
    @Size(min = 2 , max = 50 , message = "Name must be between 2 and 50 characters")
    String name;
    @NotNull(message = "Gender is not empty")
    Boolean gender;
    @NotBlank(message = "Address is not empty")
    @Size(min = 2 , max = 50 , message = "Address must be between 2 and 50 characters")
    String address;

}
