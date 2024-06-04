package com.example.shoesstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    @NotBlank(message = "Username is not empty")
    @Size(min = 4 , max = 50 , message = "Username must be between 4 and 50 characters")
    String username;
    @NotBlank(message = "Password is not empty")
    @Size(min = 4 , max = 50 , message = "Password must be between 4 and 50 characters")
    String password;
}
