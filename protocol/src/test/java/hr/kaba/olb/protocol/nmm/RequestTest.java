package hr.kaba.olb.protocol.nmm;

import hr.kaba.olb.codec.OLBCodec;
import hr.kaba.olb.codec.constants.Formatters;
import hr.kaba.olb.codec.constants.InitiatorType;
import hr.kaba.olb.codec.constants.NetworkManagementInformationCode;
import hr.kaba.olb.codec.message.HISOMessage;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class RequestTest {


    @Test
    void logonIsProperlyFormatted() {

        Request logonRequest = new Request(NetworkManagementInformationCode.LOGON, InitiatorType.HOST);

        String transmissionDateTime = LocalDateTime.of(2018, 8, 8, 15, 30, 45).format(Formatters.TRANSMISSION_DATE_TIME_FORMATTER);
        String systemAuditTraceNumber = "000042";


        HISOMessage logonMessage = logonRequest.create(transmissionDateTime, systemAuditTraceNumber);

        assertThat(OLBCodec.encode(logonMessage), is("ISO0060000500800822000000000000004000000000000000808153045000042001"));
        assertThat(OLBCodec.encode(logonMessage), is(OLBCodec.encode(Request.logon(transmissionDateTime, systemAuditTraceNumber))));

    }

}