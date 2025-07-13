package skrobman.dev.springstart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skrobman.dev.springstart.dto.UserDto;
import skrobman.dev.springstart.exception.EmailAlreadyExist;
import skrobman.dev.springstart.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("user/register")
public class RegistrationController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Map<String, String>> register(@RequestBody UserDto userDto){
        try {
            userService.registerUser(userDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of(
                            "message", "Registration Successful. Please, check your email to verify your email."
                    ));
        }catch (EmailAlreadyExist e){
            throw e;
        }

    }
}
