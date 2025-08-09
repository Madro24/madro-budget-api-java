package madro.finances.budget.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody LoginRequest loginRequest,
            HttpServletRequest request,
            HttpServletResponse response) {
        
        Map<String, Object> responseBody = new HashMap<>();
        
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );

            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Create or get existing session
            HttpSession session = request.getSession(true);
            
            // Store the security context in the session
            session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
            );

            // Set session cookie properties
            response.setHeader("Set-Cookie", 
                "JSESSIONID=" + session.getId() + 
                "; Path=/; HttpOnly; SameSite=Lax; Max-Age=3600");

            responseBody.put("success", true);
            responseBody.put("message", "Authentication successful");
            responseBody.put("sessionId", session.getId());
            responseBody.put("username", authentication.getName());
            responseBody.put("authorities", authentication.getAuthorities());

            return ResponseEntity.ok(responseBody);

        } catch (BadCredentialsException e) {
            responseBody.put("success", false);
            responseBody.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        } catch (Exception e) {
            responseBody.put("success", false);
            responseBody.put("message", "Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(
            HttpServletRequest request,
            HttpServletResponse response) {
        
        Map<String, Object> responseBody = new HashMap<>();
        
        try {
            // Get the current session
            HttpSession session = request.getSession(false);
            
            if (session != null) {
                // Invalidate the session
                session.invalidate();
            }

            // Clear the security context
            SecurityContextHolder.clearContext();

            // Clear the session cookie
            response.setHeader("Set-Cookie", 
                "JSESSIONID=; Path=/; HttpOnly; SameSite=Lax; Max-Age=0");

            responseBody.put("success", true);
            responseBody.put("message", "Logout successful");

            return ResponseEntity.ok(responseBody);

        } catch (Exception e) {
            responseBody.put("success", false);
            responseBody.put("message", "Logout failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getAuthenticationStatus(HttpServletRequest request) {
        Map<String, Object> responseBody = new HashMap<>();
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HttpSession session = request.getSession(false);
        
        if (authentication != null && authentication.isAuthenticated() && 
            !"anonymousUser".equals(authentication.getName())) {
            
            responseBody.put("authenticated", true);
            responseBody.put("username", authentication.getName());
            responseBody.put("authorities", authentication.getAuthorities());
            responseBody.put("sessionId", session != null ? session.getId() : null);
            responseBody.put("sessionValid", session != null && !session.isNew());
            
        } else {
            responseBody.put("authenticated", false);
            responseBody.put("message", "User not authenticated");
        }

        return ResponseEntity.ok(responseBody);
    }

    // Inner class for login request
    public static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest() {}

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
