package eigen;
import java.util.Date;

public record AdvancedDate(Date date) implements ComparableDate<AdvancedDate> {

	@Override
	public boolean comesBefore(AdvancedDate other) {
		return date().before(other.date());
	}

}
