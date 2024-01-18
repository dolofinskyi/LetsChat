package ua.dolofinskyi.letschat.features.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping("/list")
    public List<UserDto> list() {
        return userMapper.toDtos(userService.listAll());
    }

    @GetMapping("/search")
    public List<String> search(@RequestParam String prefix) {
        return userService.findUsernamesByPrefix(prefix);
    }
}
