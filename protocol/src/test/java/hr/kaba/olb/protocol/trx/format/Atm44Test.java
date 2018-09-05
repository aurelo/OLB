package hr.kaba.olb.protocol.trx.format;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Atm44Test {

    @Test
    void shouldCorrectlyFormatPositiveBalances() {

        Optional<Integer> ledgerBalance = Optional.of(1509715);
        Optional<Integer> availableBalance = Optional.of(1609715);

        Atm44 atm44 = Atm44.of(ledgerBalance, availableBalance);

        assertThat(atm44.format(), is("254000001509715000001609715"));

    }

    @Test
    void shouldCorrectlyFormatNegativeBalances() {
        Optional<Integer> ledgerBalance = Optional.of(-103515);
        Optional<Integer> availableBalance = Optional.of(-3515);

        Atm44 atm44 = Atm44.of(ledgerBalance, availableBalance);

        assertThat(atm44.format(), is("254-00000103515-00000003515"));

    }

    @Test
    void shouldReturnNullForEmptyBalances() {
        Optional<Integer> ledgerBalance = Optional.ofNullable(null);
        Optional<Integer> availableBalance = Optional.ofNullable(null);

        Atm44 atm44 = Atm44.of(ledgerBalance, availableBalance);

        assertThat(atm44.format(), is(nullValue()));
    }

}