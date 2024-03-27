package model.time;


/**
 * A sealed interface specifying a comesBefore method for a custom Date object that should be comparable
 * to the given type T. Only {@link BasicDate} and {@link AdvancedDate} are allowed to
 * implement this interface.
 * @param <T>  The type which can be used to compare this ComparableDate object to
 *             (see {@link #comesBefore(T)}).
 */
public sealed interface ComparableDate<T> permits BasicDate, AdvancedDate {

    /**
     * Check to see if the given date comes before (time-wise) this object.
     * @param date  The date to check.
     * @return  {@code true} if the given date is situated in the future or equal to this date,
     *          {@code false} otherwise.
     */
    boolean comesBefore(T date);
}
