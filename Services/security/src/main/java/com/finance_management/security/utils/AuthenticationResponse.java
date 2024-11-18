package finance_management.security.utils;

import org.springframework.security.core.AuthenticationException;

import java.util.HashMap;
import java.util.Map;

public record AuthenticationResponse(Boolean success, Map<String, String> data) {

    private static Map<String, String> createResponse(String key, String data) {
        Map<String, String> message = new HashMap<>();
        message.put(key, data);
        return message;
    }

    public static AuthenticationResponse dataMissing() {
        return new AuthenticationResponse(false, createResponse("error", "Data is missing"));
    }

    public static AuthenticationResponse userAlreadyExist() {
        return new AuthenticationResponse(false, createResponse("error", "User is already registered"));
    }

    public static AuthenticationResponse invalidCredentials() {
        return new AuthenticationResponse(false, createResponse("error", "Invalid credentials"));
    }

    public static AuthenticationResponse token(String jwt) {
        return new AuthenticationResponse(true, createResponse("token", jwt));
    }

    public static AuthenticationResponse serverError(String customMessage) {
        return new AuthenticationResponse(false, createResponse("error", customMessage));
    }

    public static AuthenticationResponse serverError(AuthenticationException error) {
        return new AuthenticationResponse(false, createResponse("error", error.getMessage()));
    }
}