package finance_management.security.controller;

import finance_management.security.service.AuthenticationService;
import finance_management.security.events.UserProducer;
import finance_management.security.utils.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@NotNull @RequestBody AuthenticationRequest request){
        var response = authenticationService.register(request);
        if(!response.success()){
            return ResponseEntity.badRequest().body(response.data());
        }

        return  ResponseEntity.ok().body(response.data());

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
      var response = authenticationService.login(request);
      if(!response.success()){
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.data());
      }
      return ResponseEntity.ok(response.data());
    }

    @PostMapping("/isTokenValid")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(true);
    }

}
