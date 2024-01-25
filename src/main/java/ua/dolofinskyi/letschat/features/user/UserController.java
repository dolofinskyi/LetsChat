package ua.dolofinskyi.letschat.features.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.dolofinskyi.letschat.security.SecurityContextService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;
    private final SecurityContextService contextService;

    @GetMapping("/list")
    public List<UserDto> list() {
        return userMapper.toDtos(userService.listAll());
    }

    @GetMapping("/search")
    public List<UserDto> search(@RequestParam String prefix) {
        return userMapper.toDtos(userService.findUsersByPrefix(prefix, contextService.getAuthentication().getName()));
    }
}
