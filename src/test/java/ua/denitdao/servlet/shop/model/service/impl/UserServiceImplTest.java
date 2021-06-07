package ua.denitdao.servlet.shop.model.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.denitdao.servlet.shop.model.dao.DaoFactory;
import ua.denitdao.servlet.shop.model.dao.UserDao;
import ua.denitdao.servlet.shop.model.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDao userDao;
    @Mock
    private DaoFactory daoFactory;
    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        when(daoFactory.createUserDao()).thenReturn(userDao);
    }

    @Test
    void When_createUser_Expect_True() {
        User testUser = User.builder().login("login").build();
        when(userDao.create(testUser)).thenReturn(true);

        boolean result = userService.createUser(testUser);

        assertTrue(result);
        verify(daoFactory).createUserDao();
        verify(userDao).create(testUser);
        verify(userDao).close();
    }

    @Test
    void When_createUserFail_Expect_False() {
        User testUser = User.builder().login("login").build();
        when(userDao.create(testUser)).thenReturn(false);

        boolean result = userService.createUser(testUser);

        assertFalse(result);
        verify(daoFactory).createUserDao();
        verify(userDao).create(testUser);
        verify(userDao).close();
    }

    @Test
    void When_createUserFailClosing_Expect_False() {
        User testUser = User.builder().login("login").build();
        when(userDao.create(testUser)).thenReturn(true);
        doThrow(new RuntimeException()).when(userDao).close();

        boolean result = userService.createUser(testUser);

        assertFalse(result);
        verify(daoFactory).createUserDao();
        verify(userDao).create(testUser);
        verify(userDao).close();
    }

    @Test
    void When_getUserById_Expect_User() {
        Long userId = 1L;
        Optional<User> testUser = Optional.of(User.builder().id(userId).login("login").build());
        when(userDao.findById(userId)).thenReturn(testUser);

        Optional<User> result = userService.getUserById(userId);

        assertEquals(testUser, result);
        verify(daoFactory).createUserDao();
        verify(userDao).findById(userId);
        verify(userDao).close();
    }

    @Test
    void When_getUserById_Expect_Empty() {
        Long userId = 1L;
        when(userDao.findById(userId)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(userId);

        assertEquals(Optional.empty(), result);
        verify(daoFactory).createUserDao();
        verify(userDao).findById(userId);
        verify(userDao).close();
    }

    @Test
    void When_getUserByLogin_Expect_User() {
        String login = "login";
        Optional<User> testUser = Optional.of(User.builder().login(login).build());
        when(userDao.findUserByLogin(login)).thenReturn(testUser);

        Optional<User> result = userService.getUserByLogin(login);

        assertEquals(testUser, result);
        verify(daoFactory).createUserDao();
        verify(userDao).findUserByLogin(login);
        verify(userDao).close();
    }

    @Test
    void When_getUserByLogin_Expect_Empty() {
        String login = "login";
        when(userDao.findUserByLogin(login)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserByLogin(login);

        assertEquals(Optional.empty(), result);
        verify(daoFactory).createUserDao();
        verify(userDao).findUserByLogin(login);
        verify(userDao).close();
    }

    @Test
    void When_getAllUsers_Expect_List() {
        List<User> testUsers = new ArrayList<>();
        testUsers.add(User.builder().build());
        testUsers.add(User.builder().build());
        when(userDao.findAll()).thenReturn(testUsers);

        List<User> result = userService.getAllUsers();

        assertEquals(testUsers, result);
        verify(daoFactory).createUserDao();
        verify(userDao).findAll();
        verify(userDao).close();
    }

    @Test
    void When_getAllUsers_Expect_EmptyList() {
        when(userDao.findAll()).thenReturn(Collections.emptyList());

        List<User> result = userService.getAllUsers();

        assertEquals(Collections.emptyList(), result);
        verify(daoFactory).createUserDao();
        verify(userDao).findAll();
        verify(userDao).close();
    }

    @Test
    void When_changeUserBlock_Expect_Block() {
        Long userId = 1L;
        when(userDao.block(userId)).thenReturn(true);

        boolean result = userService.changeUserBlock(userId, true);

        assertTrue(result);
        verify(daoFactory).createUserDao();
        verify(userDao).block(userId);
        verify(userDao).close();
    }

    @Test
    void When_changeUserBlock_Expect_Unblock() {
        Long userId = 1L;
        when(userDao.unblock(userId)).thenReturn(true);

        boolean result = userService.changeUserBlock(userId, false);

        assertTrue(result);
        verify(daoFactory).createUserDao();
        verify(userDao).unblock(userId);
        verify(userDao).close();
    }
}