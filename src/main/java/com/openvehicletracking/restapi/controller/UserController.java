package com.openvehicletracking.restapi.controller;

import com.openvehicletracking.restapi.model.dto.LoginRequestDTO;
import com.openvehicletracking.restapi.model.dto.LoginResponseDTO;
import com.openvehicletracking.restapi.repository.UserRepository;
import com.openvehicletracking.restapi.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Autowired
    public UserController(TokenProvider tokenProvider, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/access-token", consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponseDTO accessToken(@RequestBody LoginRequestDTO loginRequestDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        return new LoginResponseDTO(authentication, token);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/checkpoint")
    public ResponseEntity<Void> checkpoint() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.GET)
    public com.openvehicletracking.restapi.model.user.User user() {
         User principle = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         return userRepository.findByEmail(principle.getUsername());
    }

}
