package com.example.shoesstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleCreateRequest {
    @NotBlank(message = "Role name is not empty")
    @Size(min = 2 , max = 50 , message = "Role name must be between 2 and 50 characters")
    private String roleName;
}
