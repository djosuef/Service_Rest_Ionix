package com.ionix.demo.service;

import org.springframework.stereotype.Service;

import com.ionix.demo.config.AppProperties;

import lombok.RequiredArgsConstructor;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CifradoDesService {

    private final AppProperties appProperties;

    public Optional<?> cifrarDES(String texto) {
        try {
            String clave = appProperties.getCifrado().getKey();

            DESKeySpec keySpec = new DESKeySpec(clave.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8));
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);

            Optional<String> cifrado = Optional.of(encryptedText);
            //System.out.println("Cifrado: " + cifrado);
            return cifrado;
        } catch (Exception e) {
            // Handle exception
        }
        return Optional.empty();
    }
}
