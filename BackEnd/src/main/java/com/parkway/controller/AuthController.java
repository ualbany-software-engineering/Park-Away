package com.parkway.controller;

import com.parkway.dto.User;
import com.parkway.model.JwtResponse;
import com.parkway.model.LoginRequest;
import com.parkway.security.jwt.JwtTokenUtil;
import com.parkway.security.service.UserDetailsImpl;
import com.parkway.service.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtUtils;
    private UserService userService;


    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtUtils, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateAccessToken(authentication);


        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

//        if (userDetails.getStatus().equalsIgnoreCase("Inactive")) {
//            throw new UserInactiveException("User is Inactive");
//        }
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());


        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUserId(),
                userDetails.getUsername(),
                userDetails.getUsername(),
                userDetails.getLastName(),
                roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUSer(@Valid @RequestBody User user, HttpServletRequest request) {
        return new ResponseEntity(userService.saveUser(user, getSiteURL(request)), HttpStatus.OK);
    }

    @PostMapping("/register/root")
    public ResponseEntity<?> createRootAdminUser(HttpServletRequest request) {
        return new ResponseEntity(userService.creatRootUser(getSiteURL(request)), HttpStatus.OK);
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "Successfully Verified. Please login to continue.";
        } else {
            return "Verification Failed.Please contact customer support.";
        }
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
