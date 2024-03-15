package eigen.oefening1;

public class Dog extends Property {

    private float foodPerDay;
    private String name;

    public Dog(float value, String name, float foodPerDay) {
        super(value);
    }

    public float getFoodPerDay() {
        return foodPerDay;
    }

    public void setFoodPerDay(float foodPerDay) {
        this.foodPerDay = foodPerDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
