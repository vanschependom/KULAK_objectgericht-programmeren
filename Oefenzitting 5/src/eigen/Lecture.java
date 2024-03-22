package eigen;

public class Lecture extends Event {

	private String course = null;
	private String topic = null;

	public Lecture(EventCategory cat, String location, int duration, String course, String topic) {
		super(cat, location, duration);
		setCourse(course);
		setTopic(topic);
	}

	@Override
	public String getSummary() {
		String string = super.getSummary();
		string += " The course is '" + getCourse() + "' "
					+  "and the topic is '" + getTopic() + "'.";
		return string;
	}

	public void sendNotification() {
		String title = "Time to leave for " + getCourse() + "!!";
		String desc = "Lecture '" + getTopic() + "' of course '" + getCourse() + "' is starting soon in " + getLocation() + ".";
		SystemNotification.sendNotification(title, desc);
	}

	public String getCourse() {
		return course;
	}

	private void setCourse(String course) {
		this.course = course;
	}

	public String getTopic() {
		return topic;
	}

	private void setTopic(String topic) {
		this.topic = topic;
	}

}
