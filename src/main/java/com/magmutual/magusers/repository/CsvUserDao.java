package com.magmutual.magusers.repository;

import com.magmutual.magusers.entity.User;
import com.magmutual.magusers.exceptions.DatabaseFormatException;
import com.magmutual.magusers.exceptions.DatabaseIOException;
import com.magmutual.magusers.file.CsvFileManager;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CsvUserDao implements UserDAO {

    CsvFileManager entityManager;

    @Autowired
    public CsvUserDao(CsvFileManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> findById(int id) {
        List<User> users = findAll();

        return users.stream()
            .filter(user -> user.getId() == id)
            .findFirst();
    }

    @Override
    public List<User> findAll() {
        try {
            return entityManager.readAll();
        } catch (IOException e) {
            throw new DatabaseIOException(e.getMessage(), e);
        }
    }

    @Override
    public void saveOrUpdateAll(List<User> users) {
        try {
            entityManager.writeAll(users);
        } catch (IOException e) {
            throw new DatabaseIOException(e.getMessage(), e);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new DatabaseFormatException(e.getMessage(), e);
        }
    }

    @Override
    public void save(User entity) {
        List<User> users = findAll();
        users.add(entity);

        saveOrUpdateAll(users);
    }


    @Override
    public void delete(User user) {
        List<User> users = findAll();
        users.remove(user);

        saveOrUpdateAll(users);
    }


    @Override
    public List<User> findByDateCreatedBetween(LocalDate startDate, LocalDate endDate) {
        List<User> users = findAll();

        return users.stream()
            .filter(user -> !user.getDateCreated().isBefore(startDate) && !user.getDateCreated().isAfter(endDate))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Integer> maxId() {
        List<User> users = findAll();

        Optional<User> maxUserId = users.stream()
            .max(Comparator.comparingInt(User::getId));

        // The map method return 0 if the Optional isEmpty
        return maxUserId.map(User::getId);
    }
}
