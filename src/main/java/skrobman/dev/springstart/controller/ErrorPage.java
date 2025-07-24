package skrobman.dev.springstart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorPage {
    @GetMapping("/error")
    public String error() {
        return "Sorry, error occurred!";
    }
}
