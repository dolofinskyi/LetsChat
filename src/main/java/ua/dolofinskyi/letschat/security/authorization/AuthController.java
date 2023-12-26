package ua.dolofinskyi.letschat.security.authorization;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.dolofinskyi.letschat.security.action.register.RegisterDetails;
import ua.dolofinskyi.letschat.security.action.register.RegisterService;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegisterService registerService;

    @GetMapping("/login")
    public ModelAndView getLogin() {
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    @ResponseBody
    public void postLogin() {

    }

    @GetMapping("/register")
    public ModelAndView getRegister() {
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    @ResponseBody
    public AuthResponse postRegister(HttpServletRequest request, @RequestBody RegisterDetails details) {
        return registerService.action(request, details);
    }
}
