package finance_management.security.service;

import finance_management.security.entity.User;
import finance_management.security.repository.UserRepository;
import finance_management.security.utils.AuthenticationRequest;
import finance_management.security.utils.AuthenticationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;


@SpringBootTest
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;


    @InjectMocks
    private AuthenticationService authenticationService;


    private User user;
    private final String jwtToken = "zeizejfeorifjeroifejrfoiejnfq";
    private AuthenticationRequest authenticationRequest;

    @BeforeEach
    void setUp() {
        user = User.builder().username("Toto").password("FakePassword").build();
        authenticationRequest = new AuthenticationRequest(user.getUsername(), user.getPassword());
    }



    @Test
    void shouldReturnDataIsMissing() {
        AuthenticationRequest requestWithoutPassword = new AuthenticationRequest("Toto", null);

        var result = authenticationService.register(requestWithoutPassword);

        assertFalse(result.success());
        assertEquals(AuthenticationResponse.dataMissing(), result);
    }

    @Test
    void shouldReturnUserAlreadyExist(){

        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        var result = authenticationService.register(authenticationRequest);

        assertFalse(result.success());
        assertEquals(AuthenticationResponse.userAlreadyExist(), result);
    }

    @Test
    void shouldRegisterAnUser(){

        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);
        Mockito.when(passwordEncoder.encode(authenticationRequest.password())).thenReturn("encodedPassword");
        Mockito.when(jwtService.generateToken(authenticationRequest.username())).thenReturn(jwtToken);

        var  result = authenticationService.register(authenticationRequest);

        assertTrue(result.success());
        assertEquals(AuthenticationResponse.token(jwtToken), result);
    }

    @Test
    void shouldFailedLoginAttempt() {

        var authentication =
                new UsernamePasswordAuthenticationToken(authenticationRequest.username(), authenticationRequest.password());
        Mockito.when(authenticationManager.authenticate(authentication))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        var response = authenticationService.login(authenticationRequest);

        assertFalse(response.success());
        assertEquals(AuthenticationResponse.invalidCredentials(), response);
        verify(authenticationManager).authenticate(authentication);

    }

    @Test
    void shouldLoginSuccessfully(){
        var username = authenticationRequest.username();

        var authentication =
                new UsernamePasswordAuthenticationToken(username, authenticationRequest.password());
        Mockito.when(jwtService.generateToken(username)).thenReturn(jwtToken);

        AuthenticationResponse response = authenticationService.login(authenticationRequest);

        assertTrue(response.success());
        assertEquals(AuthenticationResponse.token(jwtToken), response);
        verify(authenticationManager).authenticate(authentication);
        verify(jwtService).generateToken(username);
    }
}