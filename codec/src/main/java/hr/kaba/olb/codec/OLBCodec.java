package hr.kaba.olb.codec;

import hr.kaba.olb.codec.constants.InitiatorType;
import hr.kaba.olb.codec.constants.MessageType;
import hr.kaba.olb.codec.constants.ResponseCode;
import hr.kaba.olb.codec.message.Base24Header;
import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.codec.message.OLBMessage;
import hr.kaba.olb.codec.message.bitmap.Bitmap;
import hr.kaba.olb.codec.message.bitmap.BitmapField;
import hr.kaba.olb.codec.message.bitmap.PrimaryBitmapField;
import hr.kaba.olb.codec.message.bitmap.SecondaryBitmapField;

import hr.kaba.olb.codec.message.FormatRules;

import hr.kaba.hiso.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OLBCodec {

    private final static Logger logger = LoggerFactory.getLogger(OLBCodec.class);

    private final static String ISO = "ISO";
    private final static int BASE24_HEADER_LENGTH = 9;
    private final static int MESSAGE_TYPE_LENGTH = 4;
    private final static int PRIMARY_BITMAP_LENGTH = 16;

    /**
     *
     * @param encodedMessage
     * @return OLB message (POJO) parsed from encoded message
     */
    public static HISOMessage decode(String encodedMessage) {

        Objects.nonNull(encodedMessage);

        if (encodedMessageToSmall(encodedMessage)) {
            throw new IllegalArgumentException(String.format("OLB message can not be parsed from %s", encodedMessage));
        }

        EncodedMessageParser parser = new EncodedMessageParser(encodedMessage);

        Base24Header base24Header = Base24Header.parse(parser.base24Header());
        MessageType messageType = MessageType.from(parser.messageType());
        Bitmap<PrimaryBitmapField> primaryBitmap = new Bitmap<>(parser.primaryBitmap(), PrimaryBitmapField.class, base24Header.getProductIndicator());

        String messageBody = parser.messageBody();

        Pair<Map<BitmapField, String>, String> parsePrimaryBitmapResult = primaryBitmap.mapFieldValues(messageBody);

        Map<BitmapField, String> filledValues = new HashMap<>();
        filledValues.putAll(parsePrimaryBitmapResult.getFirst());

        // is there a secondary bitmap signifying fields from 65 onwards
        if (primaryBitmap.contains(PrimaryBitmapField.P1)) {
            Bitmap<SecondaryBitmapField> secondaryBitmap = new Bitmap<>(filledValues.get(PrimaryBitmapField.P1), SecondaryBitmapField.class, base24Header.getProductIndicator());

            Pair<Map<BitmapField, String>, String> parseSecondaryBitmapResult = secondaryBitmap.mapFieldValues(parsePrimaryBitmapResult.getSecond());
            filledValues.putAll(parseSecondaryBitmapResult.getFirst());
        }

        return new OLBMessage(base24Header, messageType, filledValues);
    }



    private static Base24Header respondAs(Base24Header header, InitiatorType responder) {
        return Base24Header.copyWithResponderCode(header, responder);
    }


    /**
     *
     * @param originalMessage - message being respond to
     * @param responder
     * @param responseFields - response fields
     * @return OLB message encoded to string
     */
    public static HISOMessage respondTo(HISOMessage originalMessage, InitiatorType responder, Map<BitmapField, String> responseFields) {

        logger.debug("coding response for: {} for responder: {} with values: {}", originalMessage.getMessageType(), responder.getCode(), responseFields);

        Base24Header responseHeader = respondAs(originalMessage.getHeader(), responder);

        MessageType responseMessageType = MessageType.responseFor(originalMessage.getMessageType());

        Map<BitmapField, String> responseFieldsValues = new HashMap<>(originalMessage.getFields());

        responseFieldsValues.putAll(responseFields);

        logger.debug("Product: {} response type: {} with aggregated response fields with original fields: {}", originalMessage.getProductType(), responseMessageType, responseFieldsValues);

        // leave only fields required for appropriate message type / product
        responseFieldsValues = FormatRules.filterFields(originalMessage.getProductType(), responseMessageType, responseFieldsValues);

        logger.debug("filtered response fileds: {}", responseFieldsValues);

        return new OLBMessage(responseHeader, responseMessageType, responseFieldsValues);
    }


    /**
     *
     * @param message
     * @return OLB message encoded to string
     */

    public static String encode(HISOMessage message) {
        return String.format("%s%s%s%s%s", ISO, message.getHeader(), message.getMessageType().getCode(), message.getPrimaryBitmap(), message.dataEncoded());
    }


    private static boolean encodedMessageToSmall(String encodedMessage){
        return encodedMessage.length() <= ISO.length() + BASE24_HEADER_LENGTH + MESSAGE_TYPE_LENGTH + PRIMARY_BITMAP_LENGTH;
    }

    private static class EncodedMessageParser {
        private final String message;
        private final int indexOfFirstLetterAfterISO;

        public EncodedMessageParser(String message) {
            this.message = message;
            indexOfFirstLetterAfterISO = message.indexOf(ISO) + 3;
        }


        String base24Header() {
            return message.substring(indexOfFirstLetterAfterISO, indexOfFirstLetterAfterISO + BASE24_HEADER_LENGTH);
        }

        String messageType() {
            return message.substring(indexOfFirstLetterAfterISO + BASE24_HEADER_LENGTH, indexOfFirstLetterAfterISO + BASE24_HEADER_LENGTH + MESSAGE_TYPE_LENGTH);
        }

        String primaryBitmap() {
            return message.substring(indexOfFirstLetterAfterISO + BASE24_HEADER_LENGTH + MESSAGE_TYPE_LENGTH, indexOfFirstLetterAfterISO + BASE24_HEADER_LENGTH + MESSAGE_TYPE_LENGTH + PRIMARY_BITMAP_LENGTH);
        }

        String messageBody() {
            return message.substring(message.indexOf(ISO) + 3 + BASE24_HEADER_LENGTH + MESSAGE_TYPE_LENGTH + PRIMARY_BITMAP_LENGTH);
        }
    }


}
