package ua.dolofinskyi.letschat.security.authetication;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ua.dolofinskyi.letschat.features.user.UserFoundException;
import ua.dolofinskyi.letschat.features.user.UserNotFoundException;
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
    public RedirectView postLogin(HttpServletResponse response, @RequestBody LoginDetails details)
            throws UserNotFoundException {
        loginService.login(response, details);
        return new RedirectView("/app");
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public RedirectView postRegister(HttpServletResponse response, @RequestBody RegisterDetails details)
            throws UserFoundException {
        registerService.register(response, details);
        return new RedirectView("/app");
    }
}
