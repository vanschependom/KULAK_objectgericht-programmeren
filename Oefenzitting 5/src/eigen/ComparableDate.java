package eigen;

public sealed interface ComparableDate<T> permits BasicDate, AdvancedDate {
	public boolean comesBefore(T other);
}
