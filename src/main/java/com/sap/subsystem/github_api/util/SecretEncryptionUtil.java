package com.sap.subsystem.github_api.util;

import com.goterl.lazysodium.LazySodium;
import com.goterl.lazysodium.LazySodiumJava;
import com.goterl.lazysodium.SodiumJava;
import com.goterl.lazysodium.interfaces.Box;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class SecretEncryptionUtil {

    private SecretEncryptionUtil(){}

    private static final LazySodium lazySodium = new LazySodiumJava(new SodiumJava());

    public static String encryptSecret(String base64PublicKey, String secretValue) {
        // Decode the Base64 public key from GitHub API
        byte[] publicKeyBytes = Base64.getDecoder().decode(base64PublicKey);

        // Convert the secret into bytes
        byte[] secretBytes = secretValue.getBytes(StandardCharsets.UTF_8);

        // Allocate buffer for encryption
        byte[] encryptedBytes = new byte[secretBytes.length + Box.SEALBYTES];

        // Encrypt the secret using LibSodium's cryptoBoxSeal
        boolean success = lazySodium.cryptoBoxSeal(encryptedBytes, secretBytes, secretBytes.length, publicKeyBytes);

        if (!success) {
            throw new RuntimeException("❌ Secret encryption failed!");
        }

        // Return Base64-encoded encrypted secret (GitHub requires Base64 format)
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}
