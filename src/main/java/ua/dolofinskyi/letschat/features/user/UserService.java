package ua.dolofinskyi.letschat.features.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.letschat.features.crud.CrudService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements CrudService<User, String>, UserDetailsService {
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
        List<User> list = new ArrayList<>();
        Iterator<User> iterator = userRepository.findAll().iterator();
        iterator.forEachRemaining(list::add);
        return list;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username);
    }

    public User createUser(String username, String password, String secret) {
        return User.builder()
                .username(username)
                .password(password)
                .secret(secret)
                .status(UserStatus.ONLINE)
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .build();
    }

    public List<String> findUsernamesByPrefix(String prefix) {
        return listAll()
                .stream()
                .map(User::getUsername)
                .filter(user -> user.startsWith(prefix))
                .toList();
    }

    public Optional<User> findOptionalByUsername(String username) throws UsernameNotFoundException {
        return listAll()
                .stream()
                .filter(user -> Objects.equals(user.getUsername(), username))
                .findFirst();
    }

    public User findByUsername(String username) throws UsernameNotFoundException {
        return findOptionalByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public boolean isUserExist(String username) {
        return findOptionalByUsername(username).isPresent();
    }
}
