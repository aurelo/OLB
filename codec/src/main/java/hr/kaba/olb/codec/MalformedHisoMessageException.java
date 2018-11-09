package hr.kaba.olb.codec;

/**
 * Exception thrown when received message does not conform to specs
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   09.11.2018
 */
class MalformedHisoMessageException extends IllegalArgumentException {

    MalformedHisoMessageException(String message) {
        super(message);
    }
}
