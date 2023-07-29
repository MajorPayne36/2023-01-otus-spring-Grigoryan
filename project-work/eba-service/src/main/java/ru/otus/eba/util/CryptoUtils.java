package ru.otus.eba.util;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import ru.otus.eba.exception.CryptoException;

import javax.crypto.Cipher;
import java.security.Key;

/**
 * Класс-утилита для работы с шифрованием/расшифрованием текста. Используемый алгоритм {@link CryptoUtils#ALGORITHM}.
 */
@Log4j2
@UtilityClass
public class CryptoUtils {

    public static final String ALGORITHM = "RSA/ECB/PKCS1Padding";

    /**
     * Расшифровывает переданный текс с использованием открытого ключа.
     *
     * @param encryptedText шифрованный текст в base64.
     * @param key           ключ для дешифровки.
     * @return расшифрованный текст.
     */
    public static String decrypt(String encryptedText, Key key) {
        try {
            log.debug("Start method decrypt: encrypted_text = ({}), key = ({})", encryptedText, Base64Utils.base64(key));

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decryptedBytes = cipher.doFinal(Base64Utils.base64(encryptedText));

            var result = new String(decryptedBytes);
            log.debug("End method decrypt. result = ({})", result);
            return result;
        } catch (Exception ex) {
            log.error("Error while decrypt text = ({}) with key = ({})", encryptedText, Base64Utils.base64(key));
            throw new CryptoException(ex);
        }
    }


    /**
     * Расшифровывает переданный текс с использованием открытого ключа.
     *
     * @param decryptedText шифрованный текст в base64.
     * @param key           ключ для дешифровки.
     * @return расшифрованный текст.
     */
    public static String encrypt(String decryptedText, Key key) {
        try {
            log.debug("Start method encrypt: decrypted_text = ({}), key = ({})", decryptedText, Base64Utils.base64(key));

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] decryptedBytes = cipher.doFinal(decryptedText.getBytes());

            var result = Base64Utils.base64(decryptedBytes);
            log.debug("End method encrypt. result = ({})", result);
            return result;
        } catch (Exception ex) {
            log.error("Error while encrypt text = ({}) with key = ({})", decryptedText, Base64Utils.base64(key));
            throw new CryptoException(ex);
        }
    }

}