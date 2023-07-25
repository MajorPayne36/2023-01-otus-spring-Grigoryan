package ru.otus.eba.util;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.codec.binary.Base64;
import ru.otus.eba.exception.CryptoException;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * Класс-утилита для работы с RSA.
 */
@Log4j2
@UtilityClass
public class CryptoUtil {

    private static final String ALGORITHM = "RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING";

    /**
     * Расшифровывает переданный текс с использованием открытого ключа.
     *
     * @param encryptedText шифрованный текст в base64.
     * @param publicKeyStr  открытый ключ в base64.
     * @return расшифрованный текст.
     */
    public static String decryptByPublicKey(String encryptedText, String publicKeyStr) {
        try {
            log.debug("Start method decryptByPublicKey: encrypted_text = ({}), public_Key_str = ({})", encryptedText, publicKeyStr);

            PublicKey key = toPublicKey(publicKeyStr);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decryptedBytes = cipher.doFinal(base64(encryptedText));

            var result = new String(decryptedBytes);
            log.debug("End method decryptByPublicKey. result = ({})", result);
            return result;
        } catch (Exception ex) {
            log.error("Error while decrypt text = ({}) with publ;ic key = ({})", encryptedText, publicKeyStr);
            throw new CryptoException(ex);
        }
    }

    /**
     * Преобразует текстовую строку в открытый ключ.
     *
     * @param publicKey открытый ключ в виде строки base64
     * @return обьект открытого ключа.
     */
    public static PublicKey toPublicKey(String publicKey) {
        log.debug("Start method toPublicKey. public_key = ({})", publicKey);
        try {
            var decoded = base64(publicKey);
            var kf = KeyFactory.getInstance(ALGORITHM);
            var result = kf.generatePublic(new X509EncodedKeySpec(decoded));
            log.debug("End method toPublicKey. result = ({})", result);
            return result;
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            log.error("Error while getting publicKey from ({})", publicKey);
            throw new CryptoException(e);
        }
    }

    /**
     * base64 расшифровка текста.
     *
     * @param str строка которую надо расшифровать.
     * @return массив байтов
     */
    public static byte[] base64(String str) {
        log.debug("Start method base64. str = ({})", str);
        var result = Base64.decodeBase64(str);
        log.debug("End method base64. result = ({})", result);
        return result;
    }
}