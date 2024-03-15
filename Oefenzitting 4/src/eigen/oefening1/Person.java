package eigen.oefening1;
import java.util.*;

/**
 * @invar   The property should be legal
 *          | hasProperProperty()
 */
public class Person {

    private Set<Property> properties = new HashSet<>();

    /**
     * @return  | if properties.isEmpty()
     *          |   then result == null
     *          | else
     *          |   result == this.properties
     */
    public Set<Property> getProperties() {
        if (properties.isEmpty())
            return null;
        else
            return properties;
    }

    /**
     * A method to add a property to the set of properties.
     * @param   property
     *          The property to add to the set of properties.
     *          | properties.add(property)
     * @throws  IllegalArgumentException
     *          | ! canHaveAsProperty()
     */
    public void addProperty(Property property) throws IllegalArgumentException {
        if ( !canHaveAsProperty(property) ) {
            throw new IllegalArgumentException("Illegal property");
        }
        this.properties.add(property);
        property.setOwner(this);
    }

    /**
     * Check if the class invariant is ok.
     * @return True / false
     */
    public boolean hasProperProperties() {
        for(Property p : properties) {
            if (!canHaveAsProperty(p)) {
                return false;
            }
        }
        return true;
    }

    public boolean canHaveAsProperty(Property property) {
        return(
                property != null &&
                (property.getOwner() == null || property.getOwner() == this)
        );
    }

    /**
     * A method to get the total value of all paintings and dogs combined.
     * @return  The total value of all paintings and dogs combined.
     *          | for each property in properties:
     *          |   if property instanceof Dog or property instanceof Painting:
     *          |       total += property.getValue()
     *          | return total
     */
    public float getTotalDogsPaintingsValue() {
        float total = 0;
        for (Property p : properties) {
            if( p instanceof Dog || p instanceof Painting )
                total += p.getValue();
        }
        return total;
    }

    /**
     * Get the minimum amount of food to feed all dogs for a certain amount of days.
     * @param   nbOfdays
     * @return  The minimum amount of food to feed all dogs for a certain amount of days.
     *          | for each property in properties:
     *          |   if property instanceof Dog:
     *          |       totalPerDay += ((Dog) property).getFoodPerDay()
     *          | return totalPerDay * nbOfdays
     * @throws  IllegalArgumentException
     *          | nbOfDays < 0
     */
    public float getMinFoodForDays(int nbOfdays) throws IllegalArgumentException {
        if (nbOfdays < 0)
            throw new IllegalArgumentException("nbOfDays must be positive");
        float totalPerDay = 0;
        for(Property p : properties) {
            if( p instanceof Dog ) {
                // cast to Dog
                totalPerDay += ((Dog) p).getFoodPerDay();
            }
        }
        return totalPerDay * nbOfdays;
    }

    /**
     * Check if a person has a painting of a certain painter.
     * @param   painter
     * @return  True if the person has a painting of <painter>
     *          | for some property in Property
     *          |   (property.getOwner() == this)
     */
    public boolean hasPaintingOf(Person painter) {
        for( Property p : properties ) {
            if (p instanceof Painting && ((Painting) p).getArtist() == painter)
                return true;
        }
        return false;
    }

    /**
     * @return Null if no properties
     * @return Null if no cars in possession
     * @return The car with the greatest cc
     */
    public Car getCarWithLargestCc() {
        Car greatestCar = null;
        float greatestCc = -1;
        for( Property p : properties ) {
            if ( p instanceof Car && ((Car) p).getCc() > greatestCc ) {
                greatestCc = ((Car) p).getCc();
                greatestCar = (Car) p;
            }
        }
        return greatestCar;
    }

}
