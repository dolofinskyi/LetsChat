package ua.dolofinskyi.letschat.features.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<E, D> {
    E toEntity(D dto);
    D toDto(E entity);
    default List<E> toEntities(List<D> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }
    default List<D> toDtos(List<E> entities) {
        return entities.stream().map(this::toDto).toList();
    }
}
