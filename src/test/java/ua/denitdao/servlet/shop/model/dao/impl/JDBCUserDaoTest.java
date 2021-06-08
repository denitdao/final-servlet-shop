package ua.denitdao.servlet.shop.model.dao.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.denitdao.servlet.shop.model.dao.UserDao;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.entity.enums.Roles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JDBCUserDaoTest extends DBTest {

    private UserDao userDao;

    private final Long defUserId = 1L;
    private final String defUserLogin = "test";
    private final String defUserPassword = "password";

    @BeforeEach
    void setUp() {
        userDao = new JDBCUserDao(getConnection());
    }

    @Test
    void When_create_Expect_UserAdded() {
        User user = User.builder()
                .firstName("fname")
                .secondName("sname")
                .login("login")
                .role(Roles.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .password("password").build();

        assertTrue(userDao.create(user));
        assertNotNull(user.getId());
        assertEquals(user, userDao.findById(user.getId()).get());
    }

    @Test
    void When_createSameLogin_Expect_RuntimeException() {
        User user = User.builder()
                .firstName("fname").secondName("sname")
                .login(defUserLogin).password("password")
                .role(Roles.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();

        assertThrows(RuntimeException.class, () -> userDao.create(user));
    }

    @Test
    void When_findById_Expect_TestUser() {
        User user = userDao.findById(defUserId).get();

        assertEquals(defUserId, user.getId());
        assertEquals(defUserLogin, user.getLogin());
        assertEquals(defUserPassword, user.getPassword());
    }

    @Test
    void When_findByIdNone_Expect_Empty() {
        assertEquals(Optional.empty(), userDao.findById(-1L));
    }

    @Test
    void When_findUserByLogin_Expect_TestUser() {
        User user = userDao.findUserByLogin(defUserLogin).get();

        assertEquals(defUserId, user.getId());
        assertEquals(defUserLogin, user.getLogin());
        assertEquals(defUserPassword, user.getPassword());
    }

    @Test
    void When_findUserByLoginNone_Expect_Empty() {
        assertEquals(Optional.empty(), userDao.findUserByLogin("rndm"));
    }

    @Test
    void When_findAll_Expect_ExactAmount() {
        User user1 = User.builder()
                .firstName("fname1").secondName("sname1")
                .login("login1").password("password2")
                .role(Roles.USER).createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
        User user2 = User.builder()
                .firstName("fname2").secondName("sname2")
                .login("login2").password("password2")
                .role(Roles.USER).createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
        assertTrue(userDao.create(user1));
        assertTrue(userDao.create(user2));

        List<User> result = userDao.findAll();

        assertEquals(3, result.size());
    }

    @Test
    void When_block_Expect_Changed() {
        assertTrue(userDao.block(defUserId));
        assertTrue(userDao.findById(defUserId).get().isBlocked());
    }

    @Test
    void When_unblock_Expect_Changed() {
        When_block_Expect_Changed();
        assertTrue(userDao.unblock(defUserId));
        assertFalse(userDao.findById(defUserId).get().isBlocked());
    }

    @AfterEach
    void tearDown() {
        userDao.close();
    }
}