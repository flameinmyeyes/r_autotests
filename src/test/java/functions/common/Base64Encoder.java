package functions.common;

import java.io.File;
import java.util.Base64;

public class Base64Encoder {

    /**
     * Закодировать в base64
     */
    public static String encodeStringToBase64(String value) {
        String encodedValue = Base64.getEncoder().encodeToString(value.getBytes());
        return encodedValue;
    }

    /**
     * Раскодировать из base64
     */
    public static String decodeFromBase64(String value) {
        byte[] decodedBytes = Base64.getDecoder().decode(value);
        String decodedValue = new String(decodedBytes);
        return decodedValue;
    }

    public static String encodeBytesToBase64(byte[] source) {
        String encodedValue = Base64.getEncoder().encodeToString(source);
        return encodedValue;
    }
}
