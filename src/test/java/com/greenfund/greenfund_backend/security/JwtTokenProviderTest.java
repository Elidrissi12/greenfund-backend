package com.greenfund.greenfund_backend.security;

import com.greenfund.greenfund_backend.model.entity.User;
import com.greenfund.greenfund_backend.model.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private Authentication authentication;
    private User user;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", "GreenFundSecretKeyForJWTTokenGenerationMustBeAtLeast256BitsLongForSecurityPurposes2024");
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationInMs", 3600000); // 1 heure

        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole(Role.INVESTOR);
        user.setActive(true);

        UserPrincipal principal = UserPrincipal.create(user);
        authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
    }

    @Test
    void generateToken_ShouldReturnValidToken() {
        String token = jwtTokenProvider.generateToken(authentication);

        assertThat(token).isNotBlank();
        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
        assertThat(jwtTokenProvider.getUserIdFromToken(token)).isEqualTo(user.getId());
    }

    @Test
    void validateToken_ShouldReturnFalseForInvalidToken() {
        String invalidToken = "invalid.token.value";

        assertThat(jwtTokenProvider.validateToken(invalidToken)).isFalse();
    }

    @Test
    void getUserIdFromToken_ShouldReturnUserId() {
        String token = jwtTokenProvider.generateToken(authentication);

        Long userId = jwtTokenProvider.getUserIdFromToken(token);

        assertThat(userId).isEqualTo(user.getId());
    }
}
