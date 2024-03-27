package model;

import model.event.Event;
import model.time.ComparableDate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * A class which can be used to keep track of events with their corresponding timestamps.
 * @param <T>  The event type that should be kept in this agenda.
 * @param <U>  The date type used to store the timestamps of corresponding events.
 */
public class Agenda<T extends Event, U extends ComparableDate<U>> {
    // The events themselves.
    Set<DatedEvent> events;


    /**
     * Initialize a new agenda.
     */
    public Agenda() {
        this.events = new HashSet<>();
    }


    /**
     * Add an event to the agenda.
     * @param event  The event to be added.
     * @param date   The date at which the event occurs.
     */
    public void addEvent(T event, U date) {
        this.events.add(new DatedEvent(event, date));
    }


    /**
     * Remove an event from the agenda. If the given event does not
     * exist, no action will be taken and the function will return normally.
     * @param event  The event to be removed.
     */
    public void removeEvent(T event) {
        // Assumes the event is only present once
        for (var datedEvent : this.events) {
            if (datedEvent.getEvent() == event) {
                this.events.remove(datedEvent);
                return;
            }
        }
    }


    /**
     * Get the first upcoming event in the agenda based on the given starting date.
     * @param startDate  The date from which the first upcoming event is searched.
     * @return  The first event that occurs after the given starting date, or {@code null}
     *          if no such event exists.
     */
    public T getFirstUpcomingEvent(U startDate) {
        DatedEvent result = null;

        for (DatedEvent event : this.events) {
            if (startDate.comesBefore(event.getDate()) &&
                    (result == null || event.getDate().comesBefore(result.getDate()))) {
                result = event;
            }
        }

        if (result != null) {
            return result.getEvent();
        }
        return null;

        // Different way to do this with Java streams (for those interested)
        // Order of operations:
        //   1. Construct a stream of the DatedEvent objects in this agenda
        //   2. Filter out the DatedEvent objects that happen before the given starting date
        //   3. Reduce the stream of DatedEvent objects to a single DatedEvent, always taking
        //      the event that occurs first when reducing two DatedEvent objects
        //   4. Map the resulting DatedEvent object to the Event itself (of type T)
        //   5. OR, if no DatedEvent was found in the previous steps, provide null instead

//        return events.stream()
//                .filter(event -> !event.getDate().comesBefore(startDate))
//                .reduce((e1, e2) -> e1.getDate().comesBefore(e2.getDate()) ? e1 : e2)
//                .map(DatedEvent::getEvent)
//                .orElse(null);
    }


    /**
     * Get a list of all the upcoming events starting from the given date.
     * @param startDate  The date from which the upcoming events are searched.
     * @return  A list of all events that occur after the given starting date.
     */
    public List<T> getUpcomingEvents(U startDate) {
        List<T> result = new ArrayList<>();

        for (DatedEvent event : this.events) {
            if (startDate.comesBefore(event.getDate())) {
                result.add(event.getEvent());
            }
        }

        return result;

        // Alternative way to do this with Java streams again
//        return events.stream()
//                .filter(event -> event.getDate().comesEarlierThan(startDate))
//                .map(DatedEvent::getEvent)
//                .toList();
    }


    /**
     * Send a reminder for the first upcoming event after the given starting date.
     * @param startDate  The date from which the first upcoming event is searched.
     */
    public void sendNextReminder(U startDate) {
        Event event = this.getFirstUpcomingEvent(startDate);

        // Check to see if there is an upcoming event (if not, event equals null)
        if (event != null) {
            event.sendNotification();
        }
    }


    /**
     * Inner class used to conveniently store events with their corresponding timestamps
     * together in a single object.
     */
    private final class DatedEvent {
        // The event itself.
        private final T event;

        // The date at which the event takes place.
        private final U date;


        /**
         * Initialize a new DatedEvent object.
         * @param event  The event that should be kept in this DatedEvent object.
         * @param date   The date at which the given event occurs.
         */
        public DatedEvent(T event, U date) {
            this.event = event;
            this.date = date;
        }

        /**
         * Get the event in this DatedEvent object.
         */
        public T getEvent() {
            return this.event;
        }

        /**
         * Get the date in this DatedEvent object.
         */
        public U getDate() {
            return this.date;
        }
    }
}
