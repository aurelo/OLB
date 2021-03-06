package hr.kaba.olb.codec.message.bitmap;

import hr.kaba.olb.codec.constants.ProductIndicator;
import hr.kaba.olb.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 64 bit representation of fields being present in message
 * HISO message can have 2*64 fields (primary and secondary fields)
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   09.11.2018
 */
public class Bitmap<E extends Enum<E> & BitmapField> {

    private static final Logger logger = LoggerFactory.getLogger(Bitmap.class);

    private final String hexRepresentation;
    private final String binaryRepresentation;

    private List<E> enumValues;

    private List<E> presentFields;

    //searches 1 in binary string which signifies existence of field
    private static final Pattern pattern = Pattern.compile("[1]");

    static final int LENGTH = 64;

    public static Bitmap of(Map<BitmapField, String> data, Class<BitmapField> bitmapFieldClass) {
        //List<BitmapField> sorted = fields.keySet().stream().sorted(Comparator.comparingInt(BitmapField::getOrderingPosition)).collect(Collectors.toList());
        return null;
    }


    private boolean isValidBitmap(String bitmap) {
        return ((bitmap != null) && (bitmap.length() == 16));
    }

    private List<E> getEnumValues(Class<E> clazz) {

        final Set<E> enumSet = EnumSet.allOf(clazz);
        return new ArrayList<>(enumSet);
    }


    private List<E> findPresentFields(String binaryRepresentation, ProductIndicator forProduct) {

        List<Integer> positions = new ArrayList<>();

        Matcher matcher = pattern.matcher(binaryRepresentation);
        while (matcher.find()) {
            positions.add(matcher.start() + 1);
        }

        return this.enumValues.stream().filter(e -> Arrays.asList(e.getProductIndicators()).contains(forProduct))
                              .filter(e -> positions.contains(e.getPosition()))
                              .collect(Collectors.toList());
    }

    public Bitmap(String bitmap, Class<E> forEnum, ProductIndicator forProduct) throws IllegalArgumentException {
        if (!isValidBitmap(bitmap)) {
            throw new IllegalArgumentException(String.format("Invalid primary bitmap %s", bitmap));
        }

        this.hexRepresentation = bitmap;
        ProductIndicator productIndicator = forProduct;

        this.enumValues = new ArrayList<>(EnumSet.allOf(forEnum));


        // binary representation of BigInteger cuts leading zeros
        this.binaryRepresentation = hexToBinary(bitmap);
        this.presentFields = findPresentFields(this.binaryRepresentation, forProduct);
    }


    public String getBinaryRepresentation() {
        return this.binaryRepresentation;
    }

    public String getHexRepresentation() {
        return this.hexRepresentation;
    }

    public boolean contains(E field) {
        return presentFields.contains(field);
    }

    public List<E> getPresentFields() {
        return presentFields;
    }

    /**
     * @param hexMessage message to be parsed
     * @return Pair => key = map of filed with parsed value
     * => value = un parsed remainder of original string
     */
    public Pair<Map<BitmapField, String>, String> mapFieldValues(String hexMessage) {

        //logger.debug("parsing hex message: {} with length: {}", hexMessage, hexMessage.length());

        Map<BitmapField, String> pairedFields = new HashMap<>();

        int startingIndex = 0;
        int length;
        int lengthOfSizeCodingForField;

        for (BitmapField f : getPresentFields()) {

            if (hexMessage.length() > startingIndex) {

                if (f.getFieldSize() == BitmapField.FieldSize.VARIABILE) {
                    // find length of variable message parts
                    // max length of up to 9 can be coded with one sign, 10 or more with 2 signs, and over 100 with 3 signs
                    lengthOfSizeCodingForField = Integer.valueOf(f.getMaxLength()).toString().length();

                    length = Integer.valueOf(hexMessage.substring(startingIndex, startingIndex + lengthOfSizeCodingForField));
                    startingIndex += lengthOfSizeCodingForField; // move start of field for read information for length of field

                } else {
                    length = f.getMaxLength();
                }

                pairedFields.put(f, hexMessage.substring(startingIndex, startingIndex + length).trim());
                startingIndex = startingIndex + length;
            }

        }

        String remainingMessage = hexMessage.length() > startingIndex ? hexMessage.substring(startingIndex) : "";

        return new Pair<>(pairedFields, remainingMessage);
    }


    public static String encode(Map<BitmapField, String> fields) {

        List<BitmapField> sorted = fields.keySet()
                                         .stream()
                                         .sorted(Comparator.comparingInt(BitmapField::getOrderingPosition))
                                         .collect(Collectors.toList());

        return sorted.stream().map(e -> e.encoded(fields.get(e))).collect(Collectors.joining());

    }

    /**
     *
     * @param fields map of bitmap fields and it's values
     * @return binary encoding of presence of fields in map
     */
    private static String binaryBitmapFromFields(Map<BitmapField, String> fields) {

        String[] binaryBitmap = new String[LENGTH];

        IntStream.range(0, LENGTH).forEach(i -> {
            binaryBitmap[i] = fields.keySet().stream().anyMatch(f -> f.getPosition() == (i + 1)) ? "1" : "0";// field position starts from 1
        });


        return Arrays.stream(binaryBitmap).filter(Objects::nonNull).collect(Collectors.joining());

    }

    /**
     *
     * @param fields map of bitmap fields and corresponding values
     * @return hex encoding of presence of fields in message
     */
    private static String hexBitmapFromFields(Map<BitmapField, String> fields) {
        return binaryToHex(binaryBitmapFromFields(fields));
    }

    public static String bitmapFromFields(Map<BitmapField, String> fields, Predicate<Map.Entry<BitmapField, String>> filterFields) {

        String binaryBitmap = binaryBitmapFromFields(
                fields
                        .entrySet()
                        .stream()
                        .filter(filterFields)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        );

        return binaryToHex(binaryBitmap);
    }


    static String hexToBinary(String hexRepresentation) {
        return String.format("%64s", new BigInteger(hexRepresentation, 16).toString(2)).replace(" ", "0");
    }

    static String binaryToHex(String binaryRepresentation) {
        return String.format("%16s", new BigInteger(binaryRepresentation, 2).toString(16).toUpperCase()).replace(" ", "0");
    }


}
