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
    private final UserService userService;
    private final UserMapper userMapper;
    private final SecurityContextService contextService;

    @GetMapping("/chats")
    public List<UserDto> users() {
        User user = userService.findByUsername(contextService.getUsername());
        return userMapper.usernamesToDtos(user.getChats());
    }

    @GetMapping("/list")
    public List<UserDto> list() {
        return userMapper.toDtos(userService.listAll());
    }

    @GetMapping("/search")
    public List<UserDto> search(@RequestParam String prefix) {
        return userMapper.toDtos(userService.findUsersByPrefix(prefix, contextService.getUsername()));
    }
}
