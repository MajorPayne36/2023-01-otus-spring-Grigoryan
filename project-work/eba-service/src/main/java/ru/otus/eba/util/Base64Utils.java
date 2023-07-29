package ru.otus.eba.util;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.bouncycastle.util.encoders.Base64;

import java.security.Key;

/**
 * Класс-утилита для работы с base64.
 */
@Log4j2
@UtilityClass
public class Base64Utils {

    /**
     * base64 расшифровка текста.
     *
     * @param str строка которую надо расшифровать.
     * @return массив байтов
     */
    public static byte[] base64(String str) {
        log.debug("Start method base64. str = ({})", str);
        var result = Base64.decode(str);
        log.debug("End method base64. result = ({})", result);
        return result;
    }

    /**
     * Преобразование ключей в строку символов.
     *
     * @param key Ключ который надо преобразовать в текст.
     * @return строка символов.
     */
    public static String base64(Key key) {
        log.debug("Start method base64.");
        var result = Base64.toBase64String(key.getEncoded());
        log.debug("End method base64. result = ({})", result);
        return result;
    }

    /**
     * Преобразование ключей в строку символов.
     *
     * @param key Ключ который надо преобразовать в текст.
     * @return строка символов.
     */
    public static String base64(byte[] byteArray) {
        log.debug("Start method base64.");
        var result = Base64.toBase64String(byteArray);
        log.debug("End method base64. result = ({})", result);
        return result;
    }

}
