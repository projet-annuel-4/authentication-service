package fr.esgi.app.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String accessToken;
    private String tokenType;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;
    private String id;
    private String userRole;

}
