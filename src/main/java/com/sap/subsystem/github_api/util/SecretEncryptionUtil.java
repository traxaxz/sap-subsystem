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

    public static String encryptSecret(final String base64PublicKey, final String secretValue) {
        final byte[] publicKeyBytes = Base64.getDecoder().decode(base64PublicKey);
        final byte[] secretBytes = secretValue.getBytes(StandardCharsets.UTF_8);
        final byte[] encryptedBytes = new byte[secretBytes.length + Box.SEALBYTES];
        final boolean success = lazySodium.cryptoBoxSeal(encryptedBytes, secretBytes, secretBytes.length, publicKeyBytes);

        if (!success) {
            throw new RuntimeException("Secret encryption failed!");
        }

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}
