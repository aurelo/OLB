package hr.kaba.olb.responders.ora.service.proxy.resp;

import hr.kaba.olb.codec.constants.MessageType;
import hr.kaba.olb.protocol.detect.RequestType;
import hr.kaba.olb.responders.ora.service.proxy.ConnectionResponder;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProceduresMappingTest {

    @Test
    void shouldHaveMappingForAllExceptUnknownAndReject() {
        Arrays.stream(RequestType.values())
              .filter(r -> r != RequestType.UNKNOWN && r != RequestType.REJECT)
              .forEach(requestType -> assertThat(String.format("No prcedure handler for %s", requestType.name())
                                               , Procedures.handlerFor(requestType), is(not(Procedures.PRC_MBU_UNKNOWN))));
    }

}