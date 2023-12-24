package com.StudyOps.domain.user.dto;

import com.StudyOps.domain.user.entity.Authority;
import com.StudyOps.domain.user.entity.EndUser;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EndUserRequestDto {

    private String email;
    private String password;

    public EndUser toEndUser(PasswordEncoder passwordEncoder) {
        return EndUser.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .authority(Authority.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}