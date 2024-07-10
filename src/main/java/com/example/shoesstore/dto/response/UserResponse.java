package com.example.shoesstore.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    Long id;
    String name;
    Boolean gender;
    Integer status;
    String address;
    String urlAvatar;
    private List<String> roleNames;
}
