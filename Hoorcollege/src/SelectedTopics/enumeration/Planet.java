package SelectedTopics.enumeration;

public enum Planet {

    EARTH(6378.1, "The blue planet"),
    MARS(3397.2, "The red planet"),
    JUPITER(71492, "The giant planet");

    private final double radius; // in kilometres

    private final String description;

    private Planet(double radius, String description) {
        this.radius = radius;
        this.description = description;
    }

    public double getRadius() {
        return radius;
    }

    public String getDescription() {
        return description;
    }

}

