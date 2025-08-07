package skrobman.dev.springstart.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        String errorMessage = "Invalid email or password";
        String errorCode = "INVALID_CREDENTIALS";

        if (
                exception.getCause() instanceof DisabledException
                        || exception instanceof DisabledException
        ) {
            errorMessage = "Your account is not activated. Please activate your account via the link sent to your email.";
            errorCode = "ACCOUNT_NOT_ACTIVATED";
        } else if (exception instanceof LockedException) {
            errorMessage = "Your account is locked.";
            errorCode = "ACCOUNT_LOCKED";
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "Invalid email or password.";
            errorCode = "INVALID_CREDENTIALS";
        }

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", Instant.now().toString());
        errorResponse.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        errorResponse.put("error", "Authentication Failed");
        errorResponse.put("message", errorMessage);
        errorResponse.put("errorCode", errorCode);

        String jsonResponse = new ObjectMapper().writeValueAsString(errorResponse);
        response.getWriter().write(jsonResponse);
    }
}
