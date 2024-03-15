package eigen.oefening1;

abstract public class Property {

    private float value;
    private Person owner;

    public Property(float value) {
        setValue(value);
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

}
