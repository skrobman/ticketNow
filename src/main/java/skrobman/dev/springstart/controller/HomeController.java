package skrobman.dev.springstart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Welcome to TicketNow!";
    }

    @GetMapping("/loggedIn")
    public String loggedIn() {
        return "You are logged in!";
    }
}
