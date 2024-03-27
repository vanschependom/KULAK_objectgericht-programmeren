package model;

import model.event.Event;
import model.event.EventCategory;
import model.event.Lecture;
import model.time.AdvancedDate;
import model.time.BasicDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n");
        Agenda<Lecture, BasicDate> lectureAgenda = new Agenda<>();
        Agenda<Event, AdvancedDate> advancedAgenda = new Agenda<>();


        lectureAgenda.addEvent(new Lecture(EventCategory.ACADEMIC, "C702", 120, "OGP", "Generics in Java"),
                new BasicDate(2022, 3, 21, 10, 0, 0));

        Lecture event = lectureAgenda.getFirstUpcomingEvent(
                new BasicDate(2020, 3, 21, 10, 0, 0));

        if (event != null) {
            event.sendNotification();
            System.out.println(event.getSummary());
            System.out.println();
        }

        advancedAgenda.addEvent(new Event(EventCategory.ACADEMIC, "Gent", 600) {
            @Override
            public String getSummary() {
                return "My anonymous event class.";
            }

            @Override
            public void sendNotification() {
                System.out.printf("This is a simple notification. Description: '%s'%n", this.getSummary());
            }
        }, new AdvancedDate("2022-03-21T12:12:09.840Z"));

        advancedAgenda.sendNextReminder(new AdvancedDate("2020-03-21T12:12:09.840Z"));


//        System.out.println("\nPress any key to continue....");
//        try {
//            // Ignore result on purpose
//            // noinspection ResultOfMethodCallIgnored
//            System.in.read();
//        } catch (IOException ignored) {}
        System.exit(0);
    }
}
