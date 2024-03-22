package eigen;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Main {
	public static void main(String[] args) {

		Lecture lecture1 = new Lecture(EventCategory.ACADEMIC, "Aula C703", 120,
				"Objectgericht Programmeren", "Selected Topics");
		String description = lecture1.getSummary();
		System.out.println(description);
		lecture1.sendNotification();

		BasicDate session5 = new BasicDate(2022, 3, 22,  9, 0, 0);
		System.out.println(session5.toString());
		BasicDate session4 = new BasicDate(2022, 3, 9,  16, 30, 0);
		// Should print true
		System.out.println(session4.comesBefore(session5));
		AdvancedDate advDate = new AdvancedDate(
				Date.from(Instant.from(DateTimeFormatter.ISO_INSTANT.parse(
						"2022-03-21T09:00:00.000Z")))
		);

		Calendar<Lecture, BasicDate> myAgenda = new Calendar<>();
		myAgenda.addEvent(new Lecture(EventCategory.ACADEMIC, "Aula C703", 120, "Test", "testtt"),
		new BasicDate(2022, 3, 0,0,0,0));
		myAgenda.addEvent(new Lecture(EventCategory.RECREATIVE, "A210", 110, "test", "test"),
		new BasicDate(2022, 4, 0,0,0,0));
		List<Lecture> lectures = myAgenda.getUpcomingEvents(
				new BasicDate(2022, 4, 1, 0, 0, 0));
		for (Lecture lecture : lectures) {
			System.out.println(lecture.getSummary());
		}
		//myAgenda.sendNextReminder(new BasicDate(2022, 4, 1, 0, 0, 0));
		//System.exit(0);

	}
}