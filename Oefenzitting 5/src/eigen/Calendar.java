package eigen;

import java.util.*;

public class Calendar<C extends Event, D extends ComparableDate> {

	List<DatedEvent> events = new ArrayList<>();

	public Calendar() {

	}

	public void addEvent(C event, D time) {
		events.add(new DatedEvent(event, time));
	}

	public List<C> getUpcomingEvents(D now) {
		List<C> toReturn = new ArrayList<>();
		for (DatedEvent event : events) {
			if (event.getTime().comesBefore(now)) {
				toReturn.add(event.getEvent());
			};
		}
		return toReturn;
	}

	public C getFirstUpcomingEvent(D now) {
		C first = null;
		D firstTime = null;
		for(DatedEvent event : events) {
			if ( (first == null || event.getTime().comesBefore(firstTime))
					&& event.getTime().comesBefore(now)) {
				first = event.getEvent();
				firstTime = event.getTime();
			}
		}
		return first;
	}

	// inner class = non-static nested class
	private class DatedEvent {

		private C event = null;
		private D time;

		public DatedEvent(C event, D time) {
			setEvent(event);
			setTime(time);
		}

		private void setEvent(C event) {
			this.event = event;
		}
		private void setTime(D time) {
			this.time = time;
		}
		private C getEvent() {
			return event;
		}
		public D getTime() {
			return time;
		}

	}

}
