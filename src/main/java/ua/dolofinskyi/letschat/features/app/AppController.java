package ua.dolofinskyi.letschat.features.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.dolofinskyi.letschat.security.context.SecurityContextService;

@Controller
@RequiredArgsConstructor
public class AppController {
    private final SecurityContextService contextService;

    @GetMapping("/app")
    public ModelAndView app() {
         ModelAndView result = new ModelAndView("app");
         result.addObject("username", contextService.getUsername());
         return result;
    }
}
