package br.com.tokiomarine.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GeralServico {

    public static String gerarHash(String senha) {
        if (senha == null) return "";
        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] messageDigest = algorithm.digest(senha.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        senha = hexString.toString();
        return senha;
    }

    public static Boolean validaConfirmaSenha(String senha, String confirmaSenha) {
        if (senha == null || confirmaSenha == null) return false;
        return senha.equals(confirmaSenha) && !senha.equals("");
    }

}
