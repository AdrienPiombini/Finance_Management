package com.finance_management.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    private final String username = "tester";

    private final String SECRET_KEY = "fc5bcbc2-6009-4ccd-b9c7-ac0c00392bef";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        setField(jwtService, "secretKey", SECRET_KEY);
        setField(jwtService, "jwtExpirationTime", 9000000L);
        when(userDetails.getUsername()).thenReturn(username);
    }

    @Test
    void testGenerateToken() {

        String token = jwtService.generateToken(username);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testValidateToken() {
        String token = jwtService.generateToken(username);

        boolean isValid = jwtService.validateToken(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void testExtractUsername() {
        String token = jwtService.generateToken(username);

        String extractedUsername = jwtService.extractUsername(token);

        assertEquals(username, extractedUsername);
    }

    @Test
    void testExtractExpirationDate() {
        String token = jwtService.generateToken(username);

        Date expirationDate = jwtService.extractExpirationDate(token);
        Date currentDate = new Date();

        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(currentDate));
    }



    @Test
    void testTokenExpired() throws InterruptedException {
        setField(jwtService, "jwtExpirationTime", 1L);
        String token = jwtService.generateToken(username);

        Thread.sleep(600);

        assertThrows(ExpiredJwtException.class, () -> {
            boolean isExpired = jwtService.validateToken(token, userDetails);
            assertFalse(isExpired); // This code is never reach
        });

    }

    @Test
    void testExtractClaim() {
        String token = jwtService.generateToken(username);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals(username, claims.getSubject());
    }
}
