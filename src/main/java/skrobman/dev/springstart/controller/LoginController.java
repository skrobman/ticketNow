package skrobman.dev.springstart.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginPage(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if (session != null) {
            String errorMsg = (String) session.getAttribute("loginErrorMsg");
            if (errorMsg != null) {
                model.addAttribute("loginError", errorMsg);
                session.removeAttribute("loginErrorMsg");
            }
        }

        return "login";
    }
}
