package hr.kaba.olb.protocol.nmm;

import hr.kaba.olb.codec.OLBCodec;
import hr.kaba.olb.codec.constants.Formatters;
import hr.kaba.olb.codec.constants.InitiatorType;
import hr.kaba.olb.codec.constants.ResponseCode;
import hr.kaba.olb.codec.message.HISOMessage;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


class ResponseTest {
    @Test
    void echoResponseShouldBeProperlyFormatted() {

        String echoRequestString = "DISO00600001008008220000000000000040000000000000008281534510000013011";
        HISOMessage echoRequest = OLBCodec.decode(echoRequestString);

        Response hostResponder = new Response(InitiatorType.HOST);

        String transmissionDateTime = LocalDateTime.of(2017, 11, 3, 6, 55, 37).format(Formatters.TRANSMISSION_DATE_TIME_FORMATTER);
        HISOMessage hostResponse = hostResponder.respond(echoRequest, ResponseCode.NMM_APPROVED, transmissionDateTime);

        assertThat(OLBCodec.encode(hostResponse), is("ISO006000015081082200000020000000400000000000000110306553700000100301"));

    }

}