package ua.denitdao.servlet.shop.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Util class used for password operations
 */
public final class PasswordManager {

    private PasswordManager() {
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verifyPassword(String passwordToVerify, String passwordHash) {
        return BCrypt.checkpw(passwordToVerify, passwordHash);
    }
}
