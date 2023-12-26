package ua.dolofinskyi.letschat.features.service;

import java.util.List;

public interface CrudService <T, ID> {
    T add(T entity);
    T get(ID id);
    T update(T entity);
    void delete(T entity);
    List<T> listAll();
}
