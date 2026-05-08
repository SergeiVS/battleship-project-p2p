package org.battleshipprojectp2p.security;

import org.battleshipprojectp2p.error.EncryptionException;

import javax.crypto.KeyAgreement;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

class SymmetricKeyService {
    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    private SecretKeySpec symmetricKey;

    protected SymmetricKeyService() {
        final var keyPair = getKeypair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    protected byte[] getPublicKey() {
        return this.publicKey.getEncoded();
    }

    protected void setSymmetricKey(PublicKey remotePublicKey) {
        if (symmetricKey == null) {
            try {
                KeyAgreement agreement = KeyAgreement.getInstance("DiffieHellman");
                agreement.init(this.privateKey);
                agreement.doPhase(remotePublicKey, true);
                final var generatedSecret = agreement.generateSecret();
                final var digest = MessageDigest.getInstance("SHA-256");
                final var secretBytes = digest.digest(generatedSecret);
                this.symmetricKey = new SecretKeySpec(secretBytes, "AES");
            } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new EncryptionException("Symmetric key already exist, connection will be broken");
        }
    }

    protected SecretKeySpec getSymmetricKey() {
        if (symmetricKey != null) {
            return symmetricKey;
        } else {
            throw new EncryptionException("No symmetric key found");
        }
    }

    private KeyPair getKeypair() {
        try {
            final var random = new SecureRandom();
            random.generateSeed(8);
            final var keyPairGen = KeyPairGenerator.getInstance("DiffieHellman");
            keyPairGen.initialize(3072, new SecureRandom());
            return keyPairGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
