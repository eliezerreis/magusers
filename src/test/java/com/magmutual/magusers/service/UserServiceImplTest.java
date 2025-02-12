package com.magmutual.magusers.service;


import com.magmutual.magusers.dto.UserDTO;
import com.magmutual.magusers.entity.User;
import com.magmutual.magusers.exceptions.UserNotFoundException;
import com.magmutual.magusers.mapper.UserMapper;
import com.magmutual.magusers.repository.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserDAO userDAO;
    @Mock
    private UserMapper mapper;
    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO userDTO;
    private User user;
    private List<User> users = new ArrayList<>();
    private List<UserDTO> dtos = new ArrayList<>();

    @BeforeEach
    public void setup() {
        userDTO = UserDTO.builder()
            .id(1)
            .firstName("Jane")
            .lastName("Doe")
            .email("jane@domain.com")
            .profession("Doctor")
            .dateCreated(LocalDate.now())
            .country("USA")
            .city("Miami")
            .build();

        user = User.builder()
            .id(1)
            .firstName("Jane")
            .lastName("Doe")
            .email("jane@domain.com")
            .profession("Doctor")
            .dateCreated(LocalDate.now())
            .country("USA")
            .city("Miami")
            .build();

        users.add(user);
        for (int i = 2; i <= 50; i++)
            users.add(buildUser(i));


        dtos.add(userDTO);
        for (int i = 2; i <= 50; i++)
            dtos.add(buildDTO(i));
    }

    private User buildUser(int id) {
        return User.builder()
            .id(id)
            .firstName("John")
            .lastName("Doe")
            .email("john@domain.com")
            .profession("Developer")
            .dateCreated(LocalDate.now())
            .country("USA")
            .city("Miami")
            .build();
    }

    private UserDTO buildDTO(int id) {
        return UserDTO.builder()
            .id(id)
            .firstName("John")
            .lastName("Doe")
            .email("john@domain.com")
            .profession("Developer")
            .dateCreated(LocalDate.now())
            .country("USA")
            .city("Miami")
            .build();
    }


    @Test
    public void testCreateUser() {
        when(userDAO.maxId()).thenReturn(Optional.empty()); // Testing when maxId is null, it means no data in the dataset
        when(mapper.toEntity(any(UserDTO.class))).thenReturn(user);
        when(mapper.toDTO(any(User.class))).thenReturn(userDTO);

        Integer nextId = 1; //On an empty dataset, the first id is one
        UserDTO result = userService.createUser(userDTO);

        assertNotNull(result);
        assertEquals(nextId, result.getId());

        // Verify if these methods is being called at least once
        verify(userDAO).maxId();
        verify(userDAO).save(any(User.class));
    }

    @Test
    public void testReadUser() {
        when(userDAO.findById(anyInt())).thenReturn(Optional.of(user));
        when(mapper.toDTO(any(User.class))).thenReturn(userDTO);

        UserDTO result = userService.readUser(1);

        assertEquals(userDTO, result);

        verify(userDAO).findById(anyInt());
        verify(mapper).toDTO(user);
    }

    @Test
    public void testReadUserNotFound() {
        when(userDAO.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.readUser(anyInt()));
    }

    @Test
    public void testUpdateUser() {
        when(userDAO.findAll()).thenReturn(users);

        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getProfession(), userDTO.getProfession());
        //assertEquals(user.getDateCreated(), userDTO.getDateCreated());
        assertEquals(user.getCountry(), userDTO.getCountry());
        assertEquals(user.getCity(), userDTO.getCity());

        userService.updateUser(userDTO);

        verify(userDAO).findAll();
        verify(userDAO).saveOrUpdateAll(users);
    }

    @Test
    public void testUpdateUserNotFound() {
        when(userDAO.findAll()).thenReturn(new ArrayList<>());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userDTO));
    }

    @Test
    public void testDeleteUser() {
        when(userDAO.findById(anyInt())).thenReturn(Optional.of(user));

        userService.deleteUser(1);

        verify(userDAO).findById(anyInt());
        verify(userDAO).delete(user);
    }

    @Test
    public void testDeleteUserNotFound() {
        when(userDAO.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(anyInt()));
    }

    @Test
    public void testFindByCreatedDateBetween() {
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();

        when(userDAO.findByDateCreatedBetween(isNotNull(), isNotNull())).thenReturn(users);
        when(mapper.mapToList(users)).thenReturn(dtos);

        List<UserDTO> results = userService.findByCreatedDateBetween(startDate, endDate);

        assertNotNull(results);
        assertEquals(dtos.size(), results.size());

        verify(userDAO).findByDateCreatedBetween(isNotNull(), isNotNull());
        verify(mapper).mapToList(users);
    }

    @Test
    public void testFindByCreatedDateAfter() {
        when(userDAO.findByProfession(anyString())).thenReturn(users);
        when(mapper.mapToList(users)).thenReturn(dtos);

        List<UserDTO> results = userService.findByProfession(anyString());

        assertNotNull(results);
        assertEquals(dtos.size(), results.size());

        verify(userDAO).findByProfession(anyString());
        verify(mapper).mapToList(users);
    }

}

