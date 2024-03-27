package model.event;

import model.util.SystemNotification;


public class Lecture extends Event {
    // The course of which the lecture belongs to.
    private final String course;

    // The topic of the lecture.
    private final String topic;


    /**
     * Initialize a new lecture event.
     * @param category  The category of the lecture (in this case it should always be academic).
     * @param location  The location of the lecture.
     * @param duration  The duration of the lecture, expressed in number of minutes.
     * @param course    The course the lecture belongs to.
     * @param topic     The topic of the lecture.
     */
    public Lecture(EventCategory category, String location, int duration, String course, String topic) {
        super(category, location, duration);
        this.course = course;
        this.topic = topic;
    }


    /**
     * Shortened constructor, see {@link #Lecture(EventCategory, String, int, String, String)}
     * (the above constructor can be shortened since the category is always the same for this event).
     */
    public Lecture(String location, int duration, String course, String topic) {
        this(EventCategory.ACADEMIC, location, duration, course, topic);
    }


    /**
     * Initialize a lecture, see {@link #Lecture(String, int, String, String)}.
     * Additionally, add the given note to this lecture.
     * @param note  The note that should be added to this lecture.
     */
    public Lecture(String location, int duration, String topic, String course, String note) {
        super(EventCategory.ACADEMIC, location, duration, note);
        this.course = course;
        this.topic = topic;
    }


    /**
     * Get the course that this lecture belongs to.
     */
    public String getCourse() {
        return this.course;
    }


    /**
     * Get the topic of the lecture.
     */
    public String getTopic() {
        return this.topic;
    }

    @Override
    public String getSummary() {
        return String.format("Lecture %s of the [%s] course with topic '%s'.",
                super.getSummary(), this.getCourse(), this.getTopic());
    }

    @Override
    public void sendNotification() {
        // For lecture events we send a system notification
        SystemNotification.sendNotification("Next lecture starts in a few minutes.",
                String.format("[%s] %s", this.getCourse(), this.getTopic()));
    }
}
