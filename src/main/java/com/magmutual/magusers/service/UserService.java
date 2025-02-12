package com.magmutual.magusers.service;

import com.magmutual.magusers.dto.UserDTO;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO readUser(int id);

    void updateUser(UserDTO userDTO);

    void deleteUser(int id);

    List<UserDTO> findByCreatedDateBetween(LocalDate startDate, LocalDate end);

    List<UserDTO> findByProfession(String profession);

}
