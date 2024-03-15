package eigen.oefening1;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    @Test
    public void addPropertyLegal() {

        Person person = new Person();
        Dog dog = new Dog(69,"Daggoe",420);
        Painting painting = new Painting(10000,"Sterrennacht");

        assertNull(person.getProperties());
        assertEquals(person.getTotalDogsPaintingsValue(), 0);

        assertNull(dog.getOwner());

        person.addProperty(dog);

        assertEquals(dog.getOwner(), person);
        assertEquals(person.getTotalDogsPaintingsValue(),69);

        person.addProperty(painting);

        assertEquals(person.getTotalDogsPaintingsValue(), 69+10000);

    }

    @Test
    public void addPropertyAlreadyOwned() {

        Person person1 = new Person();
        Person person2 = new Person();
        Dog dog = new Dog(60, "Hondje", 1500);
        person1.addProperty(dog);

        assertFalse(person2.canHaveAsProperty(dog));

        assertThrows(IllegalArgumentException.class, () -> {
            person2.addProperty(dog);
        });

    }

    @Test
    public void checkPainter() {

        Person vanGogh = new Person();
        Person person = new Person();
        Property sterrennacht = new Painting(10000,"Sterrennacht",vanGogh);

        assertNull(sterrennacht.getOwner());
        person.addProperty(sterrennacht);
        assertEquals(sterrennacht.getOwner(), person);
        assertTrue(person.hasPaintingOf(vanGogh));

    }

}
