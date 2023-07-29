package ru.otus.eba.util;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import ru.otus.eba.exception.CryptoException;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Класс-утилита для работы с открытым и закрытым ключами.
 */
@Log4j2
@UtilityClass
public class RsaKeyUtils {
    public static final String ALGORITHM = "RSA";

    /**
     * Создания пары ключей. Используемый алгоритм {@link RsaKeyUtils#ALGORITHM}
     * @return
     * @throws NoSuchAlgorithmException
     */
    public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    /**
     * Преобразует текстовую строку в открытый ключ.
     *
     * @param publicKeyStr открытый ключ в виде строки base64
     * @return обьект открытого ключа.
     */
    public static PublicKey toPublicKey(String publicKeyStr) {
        log.debug("Start method toPublicKey. public_key = ({})", publicKeyStr);
        try {
            var decoded = Base64Utils.base64(publicKeyStr);
            var kf = KeyFactory.getInstance(ALGORITHM);
            var result = kf.generatePublic(new X509EncodedKeySpec(decoded));
            log.debug("End method toPublicKey. result = ({})", result);
            return result;
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            log.error("Error while getting publicKey from ({})", publicKeyStr);
            throw new CryptoException(e);
        }
    }

    /**
     * Преобразует текстовую строку в закрытый ключ.
     *
     * @param privateKeyStr закрытый ключ в виде строки base64
     * @return обьект закрытого ключа.
     */
    public static PrivateKey toPrivateKey(String privateKeyStr) {
        log.debug("Start method toPrivateKey. public_key = ({})", privateKeyStr);
        try {
            var decoded = Base64Utils.base64(privateKeyStr);
            var kf = KeyFactory.getInstance(ALGORITHM);
            var result = kf.generatePrivate(new PKCS8EncodedKeySpec(decoded));
            log.debug("End method toPrivateKey. result = ({})", result);
            return result;
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            log.error("Error while getting privateKey from ({})", privateKeyStr);
            throw new CryptoException(e);
        }
    }

}
