package manolovisoromero.person_processor.storage;

import manolovisoromero.person_processor.model.Entity;

import java.util.Collection;
import java.util.Optional;

public interface PersistAdapter<T extends Entity>{

    void connect();

    void closeConnection();

    T save(T entity);

    Collection<T> returnAll();

    Optional<T> getById(Long id);

    void deleteById(Long id);

    void clear();
}
