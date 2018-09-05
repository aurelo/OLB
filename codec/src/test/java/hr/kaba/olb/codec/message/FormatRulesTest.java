package hr.kaba.olb.codec.message;

import hr.kaba.olb.codec.constants.MessageType;
import hr.kaba.olb.codec.constants.ProductIndicator;
import hr.kaba.olb.codec.message.bitmap.PrimaryBitmapField;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormatRulesTest {

    @Test
    void optionalFieldsAreTakenIntoConsideration() {

        assertTrue(FormatRules.containsField(ProductIndicator.ATM, MessageType.TRX_RESP, PrimaryBitmapField.P44A));
    }

}