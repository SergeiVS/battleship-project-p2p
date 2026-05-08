package org.battleshipprojectp2p.security;

import org.battleshipprojectp2p.error.EncryptionException;

import java.util.ArrayList;
import java.util.List;

class OpponentIvService {
    private final List<String> usedIvs;

    public OpponentIvService() {
        usedIvs = new ArrayList<>();
    }

    public void addNewIv(String iv) {
        if (usedIvs.contains(iv)) {
            throw new EncryptionException("IV is not unique, connection will be interrupted");
        }
        usedIvs.add(iv);
    }

    public String getCurrentIv() {
        if (usedIvs.isEmpty()) {
            throw new EncryptionException("No opponents iv saved yet");
        }
        return usedIvs.getLast();
    }
}
