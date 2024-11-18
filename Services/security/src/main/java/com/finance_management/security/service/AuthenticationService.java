package com.finance_management.security.service;

import com.finance_management.events.UserCreatedEvent;
import com.finance_management.security.repository.UserRepository;
import com.finance_management.security.entity.User;
import com.finance_management.security.events.UserProducer;
import com.finance_management.security.utils.AuthenticationRequest;
import com.finance_management.security.utils.AuthenticationResponse;
import com.finance_management.security.utils.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserProducer userProducer;


    public AuthenticationResponse register(AuthenticationRequest request){
        log.info("REGISTER : Authentication Request : {}", request);

        if(request.password() == null || request.username() == null){
            return AuthenticationResponse.dataMissing();
        }
        Optional<User> result = userRepository.findByUsername(request.username());

        if(result.isPresent()){
            return AuthenticationResponse.userAlreadyExist();
        }

        var user =  User.builder()
                .username(request.username())
                .role(Role.ROLE_USER)
                .password(passwordEncoder.encode(request.password()))
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .build();

        var savedUser = userRepository.save(user);
        if (savedUser == null) return AuthenticationResponse.serverError("Failed to save user :" + user);


        var userCreatedEvent = UserCreatedEvent.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .build();

        userProducer.sendMessage(userCreatedEvent);

        var jwt = jwtService.generateToken(request.username());
        return AuthenticationResponse.token(jwt);
    }

    public AuthenticationResponse login(AuthenticationRequest request){
        log.info("LOGIN : Authentication Request : {}", request);
        try {
            var authentication = new UsernamePasswordAuthenticationToken(request.username(), request.password());
            authenticationManager.authenticate(authentication);

            var jwt = jwtService.generateToken(request.username());
            return AuthenticationResponse.token(jwt);

        }
        catch (AuthenticationException error){
            log.error(error.getMessage());
            return AuthenticationResponse.invalidCredentials();
        }
    }

}
