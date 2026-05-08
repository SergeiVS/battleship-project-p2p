package org.battleshipprojectp2p.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class IvService {
    private final SecureRandom sRandom;
    private final List<String> ivList;

    protected IvService() {
        this.sRandom = getSecureRandom();
        this.ivList = new ArrayList<>();
    }

    protected String getNewIv() {
        final var bytes = new byte[16];
        synchronized (this.sRandom) {
            this.sRandom.nextBytes(bytes);
        }
        final var iv = Arrays.toString(bytes);

        if (ivList.contains(iv)) {
            getNewIv();
        }
        ivList.add(iv);
        return iv;
    }


    private SecureRandom getSecureRandom() {
        try {
            final var random = new SecureRandom();
            SecureRandom.getInstance("AES/GCM/NoPadding");
            return random;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
