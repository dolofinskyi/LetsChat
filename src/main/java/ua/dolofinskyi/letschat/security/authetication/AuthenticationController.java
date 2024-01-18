package ua.dolofinskyi.letschat.security.authetication;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.dolofinskyi.letschat.security.login.LoginDetails;
import ua.dolofinskyi.letschat.security.login.LoginService;
import ua.dolofinskyi.letschat.security.register.RegisterDetails;
import ua.dolofinskyi.letschat.security.register.RegisterService;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final RegisterService registerService;
    private final LoginService loginService;

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public AuthenticationResponse postLogin(HttpServletResponse response, @RequestBody LoginDetails details) {
        return loginService.login(response, details);
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public AuthenticationResponse postRegister(HttpServletResponse response, @RequestBody RegisterDetails details) {
        return registerService.register(response, details);
    }
}
