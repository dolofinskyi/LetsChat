package ua.dolofinskyi.letschat.features.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    default List<User> listAll() {
        List<User> list = new ArrayList<>();
        Iterator<User> iterator = findAll().iterator();
        iterator.forEachRemaining(list::add);
        return list;
    }

    default Optional<User> findByUsername(String username) {
        return listAll()
                .stream()
                .filter(user -> Objects.equals(user.getUsername(), username))
                .findFirst();
    }
}
