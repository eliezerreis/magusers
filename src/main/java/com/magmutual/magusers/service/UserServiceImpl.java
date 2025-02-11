package com.magmutual.magusers.service;

import com.magmutual.magusers.dto.UserDTO;
import com.magmutual.magusers.entity.User;
import com.magmutual.magusers.exceptions.UserNotFoundException;
import com.magmutual.magusers.mapper.UserMapper;
import com.magmutual.magusers.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final UserMapper mapper;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, UserMapper mapper) {
        this.userDAO = userDAO;
        this.mapper = mapper;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        // Creating a manual Auto-Increment ID
        int id = userDAO.maxId().orElse(0) + 1;
        User user = mapper.toEntity(userDTO);
        user.setId(id);
        user.setDateCreated(LocalDate.now());

        userDAO.save(user);

        return mapper.toDTO(user);
    }

    @Override
    public UserDTO readUser(int id) {
        return mapper.toDTO(getUserById(id));
    }

    /**
     * Utility method the return a unique User object from the database;
     *
     * @param id The user primary key
     * @return The user object
     */
    private User getUserById(int id) {
        Optional<User> user = userDAO.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }

        return user.get();
    }

    @Override
    public void updateUser(UserDTO userDTO) {

        /*
         * This method is tricky. I have some code duplications here because is needed
         * the full list of user in order the overwrite the whole file to update one entry.
         */
        List<User> users = userDAO.findAll();

        Optional<User> user = users.stream()
            .filter(u -> u.getId() == userDTO.getId())
            .findFirst();

        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + userDTO.getId() + " not found");
        }

        /*
         * Updating the content of the user without changing the reference;
         */
        user.get().setFirstName(userDTO.getFirstName());
        user.get().setLastName(userDTO.getLastName());
        user.get().setEmail(userDTO.getEmail());
        user.get().setProfession(userDTO.getProfession());
        //user.get().setDateCreated(userDTO.getDateCreated()); This value should be change
        user.get().setCountry(userDTO.getCountry());
        user.get().setCity(userDTO.getCity());

        userDAO.saveOrUpdateAll(users);
    }

    @Override
    public void deleteUser(int id) {
        Optional<User> user = userDAO.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }

        userDAO.delete(user.get());
    }

    @Override
    public List<UserDTO> findByCreatedDateBetween(LocalDate startDate, LocalDate end) {
        return mapper.mapToList(userDAO.findByDateCreatedBetween(startDate, end));
    }
}
