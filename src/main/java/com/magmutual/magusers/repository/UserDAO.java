package com.magmutual.magusers.repository;

import com.magmutual.magusers.entity.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * This interface defines the minimum operations that a User repository should implement.
 */
@Repository
public interface UserDAO extends GenericDAO<User> {

    List<User> findByDateCreatedBetween(LocalDate startDate, LocalDate endDate);

    Optional<Integer> maxId();
}
