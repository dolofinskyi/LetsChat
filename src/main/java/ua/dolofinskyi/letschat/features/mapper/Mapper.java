package ua.dolofinskyi.letschat.features.mapper;

import java.util.List;

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
