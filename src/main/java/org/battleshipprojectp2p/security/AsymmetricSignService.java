package org.battleshipprojectp2p.security;

import java.security.*;

class AsymmetricSignService {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private PublicKey validationKey;

    public AsymmetricSignService() {
        final var keyPair = generateKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    protected PublicKey getPublicKey() {
        return this.publicKey;
    }

    protected byte[] sign(String message) {
        final var data = message.getBytes();
        try {
            final var signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(this.privateKey);
            signature.update(data);
            return signature.sign();
        } catch (
                NoSuchAlgorithmException |
                InvalidKeyException |
                SignatureException e
        ) {
            throw new RuntimeException(e);
        }
    }

    protected boolean verifySignature(String message, String signature) {
        final var data = message.getBytes();
        final var signBytes = signature.getBytes();
        try {
            final var verifier = Signature.getInstance("SHA256withRSa");
            verifier.initVerify(validationKey);
            verifier.update(data);
            return verifier.verify(signBytes);
        } catch (
                NoSuchAlgorithmException |
                InvalidKeyException |
                SignatureException e
        ) {
            throw new RuntimeException(e);
        }
    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
