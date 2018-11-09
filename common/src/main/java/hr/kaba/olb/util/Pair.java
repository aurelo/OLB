package hr.kaba.olb.util;

/**
 * Utility class acting as 2 member tuple
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   15.07.2018
 */
public class Pair<F, S> {

    private final F first;
    private final S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }
}
