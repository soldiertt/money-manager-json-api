package be.soldier.money.web.util;

/**
 * Created by soldiertt on 31-01-15.
 */
public class NumberUtils {

    public static boolean isNumericInt(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Integer toInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
