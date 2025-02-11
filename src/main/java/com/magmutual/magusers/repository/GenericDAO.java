package com.magmutual.magusers.repository;

import java.util.List;
import java.util.Optional;

/**
 * This interface defines the basic operations for persisting data; It provides a definition that should be followed
 * for various persistence mechanisms. It could be for a CSV File or a Relational Database.
 *
 * @param <E> The entity type
 */
public interface GenericDAO<E> {

    Optional<E> findById(int id);

    List<E> findAll();

    void saveOrUpdateAll(List<E> entities);

    void save(E entity);

    void delete(E entity);
}
