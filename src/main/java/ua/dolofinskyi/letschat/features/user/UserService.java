package ua.dolofinskyi.letschat.features.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.letschat.features.crud.CrudService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements CrudService<User, String> {
    private final UserRepository userRepository;

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public User get(String id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public User update(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public void delete(User entity) {
        userRepository.delete(entity);
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    public User createUser(String username, String password, String secret) {
        return add(
                User.builder()
                    .username(username)
                    .password(password)
                    .secret(secret)
                    .status(UserStatus.ONLINE)
                    .enabled(true)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .build()
        );
    }

    public List<User> findUsersByPrefix(String prefix, String username) {
        return listAll()
                .stream()
                .filter(user -> (user.getUsername().startsWith(prefix) && !user.getUsername().equals(username)))
                .toList();
    }

    public User findBySessionId(String sessionId) {
        return listAll()
                .stream()
                .filter(user -> user.getSessionId().equals(sessionId))
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
    }

    private Optional<User> findOptionalByUsername(String username) {
        return listAll()
                .stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    public User findByUsername(String username) {
        return findOptionalByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    public boolean isUserExist(String username) {
        return findOptionalByUsername(username).isPresent();
    }
}
