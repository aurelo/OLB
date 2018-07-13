package hr.kaba.olb.codec;

import hr.kaba.olb.codec.constants.InitiatorType;
import hr.kaba.olb.codec.constants.MessageType;
import hr.kaba.olb.codec.message.Base24Header;
import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.codec.message.OLBMessage;
import hr.kaba.olb.codec.message.bitmap.Bitmap;
import hr.kaba.olb.codec.message.bitmap.BitmapField;
import hr.kaba.olb.codec.message.bitmap.PrimaryBitmapField;
import hr.kaba.olb.codec.message.bitmap.SecondaryBitmapField;

import hr.kaba.olb.codec.message.FormatRules;

import java.util.HashMap;
import java.util.Map;

public class OLBCodec {

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

        if ((encodedMessage == null) || encodedMessage.length() <= ISO.length() + BASE24_HEADER_LENGTH + MESSAGE_TYPE_LENGTH + PRIMARY_BITMAP_LENGTH) {
            throw new IllegalArgumentException(String.format("OLB message can not be parsed from %s", encodedMessage));
        }

        int indexOfFirstLetterAfterISO = encodedMessage.indexOf(ISO) + 3;

        Base24Header base24Header = Base24Header.parse(encodedMessage.substring(indexOfFirstLetterAfterISO, indexOfFirstLetterAfterISO + BASE24_HEADER_LENGTH));
        MessageType messageType = MessageType.from(encodedMessage.substring(indexOfFirstLetterAfterISO + BASE24_HEADER_LENGTH, indexOfFirstLetterAfterISO + BASE24_HEADER_LENGTH + MESSAGE_TYPE_LENGTH));
        Bitmap<PrimaryBitmapField> primaryBitmap = new Bitmap<>(encodedMessage.substring(indexOfFirstLetterAfterISO + BASE24_HEADER_LENGTH + MESSAGE_TYPE_LENGTH, indexOfFirstLetterAfterISO + BASE24_HEADER_LENGTH + MESSAGE_TYPE_LENGTH + PRIMARY_BITMAP_LENGTH)
                ,PrimaryBitmapField.class
                ,base24Header.getProductIndicator());


        String messageBody = encodedMessage.substring(encodedMessage.indexOf(ISO) + 3 + BASE24_HEADER_LENGTH + MESSAGE_TYPE_LENGTH + PRIMARY_BITMAP_LENGTH);

        hr.kaba.hiso.util.Pair<Map<BitmapField, String>, String> parsePrimaryBitmapResult = primaryBitmap.mapFieldValues(messageBody);

        Map<BitmapField, String> filledValues = new HashMap<>();
        filledValues.putAll(parsePrimaryBitmapResult.getFirst());

        // is there a secondary bitmap signifying fields from 65 onwards
        if (primaryBitmap.contains(PrimaryBitmapField.P1)) {
            Bitmap<SecondaryBitmapField> secondaryBitmap = new Bitmap<>(filledValues.get(PrimaryBitmapField.P1), SecondaryBitmapField.class, base24Header.getProductIndicator());

            hr.kaba.hiso.util.Pair<Map<BitmapField, String>, String> parseSecondaryBitmapResult = secondaryBitmap.mapFieldValues(parsePrimaryBitmapResult.getSecond());
            filledValues.putAll(parseSecondaryBitmapResult.getFirst());
        }

        return new OLBMessage(base24Header, messageType, filledValues);
    }



    private static Base24Header respondAsHost(Base24Header header) {
        return Base24Header.copyWithResponderCode(header, InitiatorType.HOST);
    }


    /**
     *
     * @param originalMessage - message being respond to
     * @param responseFields - response fields
     * @return OLB message encoded to string
     */
    public static HISOMessage respondTo(HISOMessage originalMessage, Map<BitmapField, String> responseFields) {
        Base24Header responseHeader = respondAsHost(originalMessage.getHeader());

        MessageType responseMessageType = MessageType.responseFor(originalMessage.getMessageType());

        Map<BitmapField, String> responseFieldsValues = new HashMap<>(originalMessage.getFields());
        responseFieldsValues.putAll(responseFields);

        // leave only fields required for appropriate message type / product
        responseFieldsValues = FormatRules.filterFields(originalMessage.getProductType(), responseMessageType, responseFieldsValues);

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

}
