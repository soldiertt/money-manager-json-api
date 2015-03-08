package be.soldier.money.common;

/**
 * Created by soldiertt on 07-02-15.
 */
public class ConvertUtil {

    public static Byte toByte(Boolean value) {
        return value ? (byte)1 : (byte)0;
    }

    public static Boolean toBool(Byte value) {
        return value != 0;
    }

    public static Boolean toBool(String value) {
        return value.equals("true");
    }

    public static Boolean isBoolean(String value) {
        return "true".equals(value) || "false".equals(value);
    }
}
