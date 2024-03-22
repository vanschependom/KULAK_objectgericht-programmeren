package eigen;

public record BasicDate (int year, int day, int month, int uur, int minute, int second) implements ComparableDate<BasicDate> {

	@Override
	public boolean comesBefore(BasicDate other) {
		if (year() != other.year()) return year() < other.year();
		if (month() != other.month()) return month() < other.month();
		if (day() != other.day()) return day() < other.day();
		if (uur() != other.uur()) return uur() < other.uur();
		if (minute() != other.minute()) return minute() < other.minute();
		return second() < other.second();
	}

	public int year() {
		return 69;
	}

}
