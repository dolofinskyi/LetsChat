package ua.dolofinskyi.letschat.features.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.letschat.features.service.CrudService;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements CrudService<User, String>, UserDetailsService {
    private final UserRepository repository;

    @Override
    public User add(User user) {
        return repository.save(user);
    }

    @Override
    public User get(String id) {
        return repository
                .findById(id)
                .orElseThrow(NullPointerException::new);
    }

    @Override
    public User update(User entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(User entity) {
        repository.delete(entity);
    }

    @Override
    public List<User> listAll() {
        return repository.listAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
    public User createUser(String username, String password, String secret) {
        return User.builder()
                .username(username)
                .password(password)
                .secret(secret)
                .chats(Collections.emptySet())
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .build();
    }

    public boolean isUserExist(String username) {
        return repository.findByUsername(username).isPresent();
    }
}
