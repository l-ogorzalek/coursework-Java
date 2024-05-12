package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthorAfterCreationResponse {
    private String name;
    private String surname;
}
