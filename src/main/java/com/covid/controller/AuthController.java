package com.covid.controller;

import com.covid.jwt.JwtTokenUtil;
import com.covid.model.*;
import com.covid.repository.RoleRepository;
import com.covid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String createAuthenticationToken(@RequestBody LoginRequest loginRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        return jwtTokenUtil.generateToken(loginRequest.getUsername());
    }

    @PostMapping("/signup")
    public User register(@RequestBody SignupRequest signupRequest) {
        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(), signupRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role roles = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new IllegalStateException("role not found"));
        user.setRoles(Collections.singleton(roles));
        User newUser = userRepository.save(user);

        return newUser;
    }

}
