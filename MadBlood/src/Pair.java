/**
 * Pair is a simple class that stores two elements of two types.
 * @param <K> the first type to store
 * @param <V> the second type to store
 */
public class Pair<K, V> {
    /**
     * Constructs a Pair with the two supplied values.
     * @param first the first value to store in this Pair instance.
     * @param second the second value to store in this Pair instance.
     */
    public Pair(K first, V second){
        this.first = first;
        this.second = second;
    }


    /**
     * The first element of the Pair
     */
    public K first;

    /**
     * The second element of the Pair
     */
    public V second;
}
