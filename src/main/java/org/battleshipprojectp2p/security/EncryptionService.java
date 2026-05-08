package org.battleshipprojectp2p.security;

import org.battleshipprojectp2p.error.EncryptionException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionService {
    private final SymmetricKeyService symmetricKeyService;
    private final IvService ivService;
    private final OpponentIvService opponentIvService;
    private final SequenceService sequenceService;

    private final AsymmetricSignService signService;

    private final String passPhrase;

    public EncryptionService(String passPhrase) {
        this.passPhrase = passPhrase;
        this.symmetricKeyService = new SymmetricKeyService();
        this.ivService = new IvService();
        this.opponentIvService = new OpponentIvService();
        this.sequenceService = new SequenceService();
        this.signService = new AsymmetricSignService();
    }

    public String encryptMessage(String message) {
        try {
            final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, symmetricKeyService.getSymmetricKey(), new IvParameterSpec(ivService.getNewIv().getBytes()));
            final var encMessage = cipher.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(encMessage);
        } catch (
                NoSuchPaddingException |
                NoSuchAlgorithmException |
                InvalidAlgorithmParameterException |
                InvalidKeyException |
                IllegalBlockSizeException |
                BadPaddingException e
        ) {
            throw new RuntimeException("Encryption error: " + e);
        }
    }

    public String decryptMessage(String encryptedMessage, String iv) {
        opponentIvService.addNewIv(iv);
        try {
            final var messageBytes = Base64.getDecoder().decode(encryptedMessage);
            final var ivSpec = new IvParameterSpec(opponentIvService.getCurrentIv().getBytes());
            final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, symmetricKeyService.getSymmetricKey(), ivSpec);
            final var decMessage = cipher.doFinal(messageBytes);
            return new String(decMessage);
        } catch (
                NoSuchAlgorithmException |
                NoSuchPaddingException |
                InvalidAlgorithmParameterException |
                InvalidKeyException |
                IllegalBlockSizeException |
                BadPaddingException e
        ) {
            throw new RuntimeException(e);
        }
    }

    public void validatePassPhrase(String passPhrase) {
        if (!this.passPhrase.equals(passPhrase)) {
            throw new EncryptionException("PassPhrase is not valid");
        }
    }
}
