package ua.denitdao.servlet.shop.util;

public final class PasswordManager {

    public static String hashPassword(String password) {
        return password;
    }

    public static boolean verifyPassword(String passwordToVerify, String passwordHash) {
        return passwordHash.equals(passwordToVerify);
    }

}