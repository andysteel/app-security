package com.gmail.andersoninfonet.appsecurityclient.controller;

import javax.servlet.http.HttpServletRequest;

import com.gmail.andersoninfonet.appsecurityclient.dto.MessageResponse;
import com.gmail.andersoninfonet.appsecurityclient.dto.UserRequest;
import com.gmail.andersoninfonet.appsecurityclient.dto.UserResponse;
import com.gmail.andersoninfonet.appsecurityclient.event.RegistrationCompleteEvent;
import com.gmail.andersoninfonet.appsecurityclient.service.UserService;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    public RegistrationController(UserService userService, ApplicationEventPublisher publisher) {
        this.userService = userService;
        this.publisher = publisher;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userDTO, final HttpServletRequest request) {
        var user = userService.registerUser(userDTO);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return ResponseEntity.ok(new UserResponse(user));
    }

    @GetMapping("/verifyRegistration")
    public ResponseEntity<MessageResponse> verifyUser(@RequestParam("token") String token) {
        var isValid = userService.validateVerificationToken(token);

        if(isValid) {
            return ResponseEntity.ok(new MessageResponse("User successfully enabled !"));
        }
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Token expired, user not enabled !"));
    }

    @GetMapping("/resendVerifyToken")
    public ResponseEntity<MessageResponse> resendVerifyToken(@RequestParam("email") String  email, final HttpServletRequest request) {
        var wrapperUser = new Object(){boolean isPresent = false;};
        Runnable isNotPresent = () -> {};
        userService.findUserByEmail(email).ifPresentOrElse(u -> {
            publisher.publishEvent(new RegistrationCompleteEvent(u, applicationUrl(request)));
            wrapperUser.isPresent = true;
        } , isNotPresent);

        if(wrapperUser.isPresent) {
            return ResponseEntity.ok(new MessageResponse("Token resent !"));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("User not found, please signup first !"));
    }

    private String applicationUrl(HttpServletRequest request) {
        return request.getScheme()
            .concat("://")
            .concat(request.getServerName())
            .concat(":")
            .concat(String.valueOf(request.getServerPort()))
            .concat(request.getContextPath());
    }
}
