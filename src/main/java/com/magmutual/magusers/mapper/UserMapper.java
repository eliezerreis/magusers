package com.magmutual.magusers.mapper;

import com.magmutual.magusers.dto.UserDTO;
import com.magmutual.magusers.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setProfession(user.getProfession());
        userDTO.setDateCreated(user.getDateCreated());
        userDTO.setCountry(user.getCountry());
        userDTO.setCity(user.getCity());

        return userDTO;
    }

    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        return new User(
            userDTO.getId(),
            userDTO.getFirstName(),
            userDTO.getLastName(),
            userDTO.getEmail(),
            userDTO.getProfession(),
            userDTO.getDateCreated(),
            userDTO.getCountry(),
            userDTO.getCity());
    }

    public List<UserDTO> mapToList(List<User> users) {
        if (users == null || users.isEmpty()) {
            return new ArrayList<>();
        }

        return users.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

}
