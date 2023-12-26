package ua.dolofinskyi.letschat.security.authorization;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.dolofinskyi.letschat.security.action.login.LoginDetails;
import ua.dolofinskyi.letschat.security.action.login.LoginService;
import ua.dolofinskyi.letschat.security.action.register.RegisterDetails;
import ua.dolofinskyi.letschat.security.action.register.RegisterService;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegisterService registerService;
    private final LoginService loginService;

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(HttpServletRequest request, HttpServletResponse response,
                          @RequestBody LoginDetails details) {
        loginService.action(request, response, details);
        return "redirect:app";
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(HttpServletRequest request, HttpServletResponse response,
                             @RequestBody RegisterDetails details) {
        registerService.action(request, response, details);
        return "redirect:app";
    }
}
