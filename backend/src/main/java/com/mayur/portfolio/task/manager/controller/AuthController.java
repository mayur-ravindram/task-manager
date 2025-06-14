package com.mayur.portfolio.task.manager.controller;

import com.mayur.portfolio.task.manager.model.JwtResponse;
import com.mayur.portfolio.task.manager.model.LoginRequest;
import com.mayur.portfolio.task.manager.model.LoginResponse;
import com.mayur.portfolio.task.manager.service.AuthService;
import com.mayur.portfolio.task.manager.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    // constructor injection for authSevice and jwtService
    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    /**
     * Endpoint for user login.
     * Validates the user's credentials and returns a JWT token if successful.
     *
     * @param loginRequest contains username and password
     * @return JWT token if login is successful, or an error message if not
     */

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        // Validate credentials against DB (pseudo-code)
        boolean valid = authService.validateUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (!valid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtResponse(null) {{
                setErrorMessage("Invalid username or password");
            }});
        }
        String token = jwtService.generateToken(loginRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }


    /**
     * Endpoint for user registration.
     * Creates a new user account and returns a success message.
     *
     * @param loginRequest contains username and password
     * @return success message if registration is successful, or an error message if not
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginRequest loginRequest) {
        // Register user in DB (pseudo-code)
        boolean registered = authService.registerUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (!registered) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed");
        }
        return ResponseEntity.ok("User registered successfully");
    }

}
