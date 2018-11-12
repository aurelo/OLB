package hr.kaba.olb.protocol.trx.format;

import java.util.Optional;


/**
 * Value class representing additional response data field (field 44)
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
public class Atm44 {

    private final Optional<Integer> ledgerBalance;
    private final Optional<Integer> availableBalance;


    public final static String EMPTY_FIELD = null;

    private Atm44(Optional<Integer> ledgerBalance, Optional<Integer> availableBalance) {
        this.ledgerBalance = ledgerBalance;
        this.availableBalance = availableBalance;
    }

    public boolean shouldBeAdded() {
        return (ledgerBalance.isPresent() || availableBalance.isPresent());
    }

    public String format() {

        if (!ledgerBalance.isPresent() && !availableBalance.isPresent()) {
            return EMPTY_FIELD;
        }

        String usageIndicator = determineUsageIndicator(ledgerBalance, availableBalance);

        return String.format("25%1s%012d%012d", usageIndicator, ledgerBalance.orElse(0), availableBalance.orElse(0));
    }


    public static Atm44 of(Optional<Integer> ledgerBalance, Optional<Integer> availableBalance) {
        return new Atm44(ledgerBalance, availableBalance);
    }

    private static String determineUsageIndicator(Optional<Integer> ledgerBalance, Optional<Integer> availableBalance) {

        String usageIndicator = "1";

        if (ledgerBalance.isPresent() && availableBalance.isPresent()) {
            usageIndicator = "4";
        } else if (ledgerBalance.isPresent() && !availableBalance.isPresent()) {
            usageIndicator = "1";
        } else if (!ledgerBalance.isPresent() && availableBalance.isPresent()) {
            usageIndicator = "2";
        }

        return usageIndicator;
    }
}
