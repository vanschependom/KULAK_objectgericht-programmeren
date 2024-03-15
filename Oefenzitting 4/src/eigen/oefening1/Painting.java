package eigen.oefening1;

public class Painting extends Property {

    private String title;
    private Person artist;

    public Painting(float value, String title, Person artist) {
        super(value);
        setTitle(title);
        setArtist(artist);
    }

    public Painting(float value, String title) {
        this(value, title, null);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Person getArtist() {
        return artist;
    }

    public void setArtist(Person artist) {
        this.artist = artist;
    }

}
