package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tokens {
    private String accessToken;
    private String refreshToken;
    private User user;
}
