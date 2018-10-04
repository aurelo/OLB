package hr.kaba.olb.protocol.detect;

import hr.kaba.olb.codec.OLBCodec;
import hr.kaba.olb.codec.message.HISOMessage;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class RequestDetectorPreauthTest {

    @Test
    void shouldRecognizePosAuthorizationRequest() {
        String posPreauthRequestString = "-ISO0260000500100B238808128A080180000000010000004000030000000001650091005503901097007503709100910151119100100898215189222400885952=2006000000000341V0100898        VIPNET DOO            ZAGREB          HR1910162007PRO1+00000000192400PRO1031000000001124000080000038207000000000341000000000000000000000000";
        HISOMessage posPreauthRequest = OLBCodec.decode(posPreauthRequestString);

        assertThat(RequestDetector.determineType(posPreauthRequest), is(RequestType.POS_AUTHORIZATION_REQUEST));
    }

    @Test
    void shouldRecognizePosAuthorizationAdvice() {
        String posAuthorizationAdviceString = "8ISO0260000730120B23880812AA08018000000001000000400003000000002000009021331300099851525220902090206042760375189222400840452=17022010000000000000747700      00KZ200052        Konzum P-2000         ZAGREB       HRVHR191016EUROEURO-12000000192400PRO1031000000001124000080000038200002760132522000000000000000000000000";
        HISOMessage posAuthorizationAdvice = OLBCodec.decode(posAuthorizationAdviceString);

        assertThat(RequestDetector.determineType(posAuthorizationAdvice), is(RequestType.POS_AUTHORIZATION_ADVICE));

    }

}
