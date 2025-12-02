package org.example.security;

import java.security.MessageDigest;

public class PasswordUtil {

    public static String hash(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(senha.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) sb.append(String.format("%02x", b));

            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar hash", e);
        }
    }

    public static boolean matches(String senhaDigitada, String senhaHash) {
        return hash(senhaDigitada).equals(senhaHash);
    }
}
