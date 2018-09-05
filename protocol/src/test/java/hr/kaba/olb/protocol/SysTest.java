package hr.kaba.olb.protocol;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SysTest {
    @Test
    void auditTraceNumberShouldBeCorrectlyFormatted() {

        String target = Sys.auditTraceNumber();

        assertThat(target.length(), is(6));
        assertThat(target, not(containsString(" ")));

    }



}