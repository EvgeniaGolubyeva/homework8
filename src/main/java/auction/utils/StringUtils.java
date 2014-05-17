package auction.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * @author Evgenia
 */
public class StringUtils {
    public static String currencyFormat(BigDecimal value) {
        return NumberFormat.getCurrencyInstance().format(value);
    }
}
