import be.kuleuven.cs.som.annotate.*;

/**
 * A class that represents an oil tank
 * @version 1.1
 * @author Vincent Van Schependom
 */
public class Oiltank {

    private float capacity;
    private float oil;

    /**
     * Een constructor voor de klasse Oiltank met 1 argument.
     * @param capacity
     * @effect Na het uitvoeren van de constructor wordt een olietank aangemaakt inhoud van 0 en wordt de capaciteit
     * ingesteld op de meegegeven waarde via setCapacity.
     */
    public Oiltank(float capacity) {
        this(capacity,0);
    }

    /**
     * Een constructor voor de klasse Oiltank met 2 argumenten.
     * @param capacity De capaciteit van de nieuwe Oiltank.
     * @param oil De inhoud van de nieuwe olietank.
     * @effect Er wordt een nieuwe olietank aangemaakt en de inhoud en de capaciteit worden ingesteld met respectievelijk
     * de methodes setOil en setCapacity.
     */
    public Oiltank(float capacity, float oil) {
        this.setOil(oil);
        this.setCapacity(capacity);
    }

    /**
     * Een constructor voor de klasse Oiltank zonder argumenten.
     * @effect Na het aanroepen van deze constructor wordt een olietank aangemaakt met capaciteit 5000 en inhoud 0
     * aan de hand van de constructor die zowel capaciteit als inhoud meekrijgt.
     */
    public Oiltank() {
        this(5000,0);
    }

    /**
     * De getter voor de capaciteit op te halen.
     * @return De capaciteit wordt teruggegeven.
     */
    @Basic
    public float getCapacity() {
        return capacity;
    }

    /**
     * De getter voor de hoeveelheid olie in de tank
     * @return Deze getter returnt de hoeveelheid olie.
     */
    @Basic
    public float getOil() {
        return oil;
    }

    /**
     * Een methode om de capaciteit in te stellen
     * @param capacity De capaciteit om in te stellen
     * @post Als de capaciteit positief is, wordt de capaciteit ingesteld op de meegegeven waarde, anders
     * wordt de capaciteit ingesteld op 0.
     */
    @Basic
    private void setCapacity(float capacity) {
        if (capacity >= 0) {
            this.capacity = capacity;
        } else {
            this.capacity = 0;
        }
    }

    /**
     * Setter voor de hoeveelheid olie.
     * @param oil De te setten hoeveelheid
     * @post Na het uitvoeren van de setter zal de hoeveelheid olie in de tank gelijk zijn aan oil, als deze
     * de capaciteit niet overschrijdt. (intertia: niks zeggen over de else -> ongewijzigd)
     */
    @Basic
    private void setOil(float oil) {
        if (oil <= getCapacity())
            this.oil = oil;
    }

    /**
     * Methode om een hoeveelheid olie toe te voegen.
     * @param amount De hoeveelheid die moet worden toegevoegd
     * @post Als de huidige hoeveelheid olie, verhoogd met de toe te voegen hoeveelheid
     * olie de capaciteit niet overschrijdt, wordt de hoeveelheid olie in de tank verhoogd.
     */
    public void addOil(float amount) {
        float current = getOil();
        if (current + amount <= capacity) {
            setOil(current + amount);
        }
    }

    /**
     * Methode om de tank volledig te vullen.
     * @post Na het uitvoeren van de methode zal de inhoud gelijk zijn aan de capaciteit.
     */
    public void fillTank() {
        setOil(getCapacity());
    }

    /**
     * Methode om de tank leeg te maken
     * @post Na het uitvoeren is de inhoud 0.
     */
    public void emptyTank() {
        setOil(0);
    }

    /**
     * Een methode om olie over te dragen naar een andere olietank.
     * @param other De olietank om naar over te dragen
     * @param amount De hoeveelheid die moet overgedragen worden
     * @post Als de nieuwe hoeveelheid de capaciteit van de andere tank niet overschrijdt, en bovendien de hoeveelheid
     * minder is of gelijk is aan de huidige hoeveelheid in de tank, wordt de hoeveelheid effectief overgedragen.
     */
    public void transfer(Oiltank other, float amount) {
        if (amount + other.getOil() <= other.getCapacity() && amount <= this.getCapacity()) {
            other.addOil(amount);
        }
    }

    /**
     * Een methode om te kijken of de tank voller is dan een andere tank, procentueel gezien.
     * @param other De andere olietank
     * @return Er wordt true gereturnd als de tank voller is, anders false.
     */
    public boolean isFullerThan(Oiltank other) {
        float ownPercentage = this.getOil() / this.getCapacity();
        float otherPercentage = other.getOil() / other.getCapacity();
        return ownPercentage > otherPercentage;
    }

    /**
     * Een methode om te kijken welke van 3 olietanken de grootste restcapaciteit heeft.
     * @param t1 De eerste olietank.
     * @param t2 De tweede olietank.
     * @param t3 De derde olietank.
     * @return Na het uitvoeren wordt de olietank met de grootste restcapaciteit gereturnd.
     */
    public static Oiltank fullest(Oiltank t1, Oiltank t2, Oiltank t3) {
        Oiltank[] tanks = { t1, t2, t3 };
        float biggestRest = Float.MIN_VALUE;
        Oiltank largest = null;
        for(Oiltank tank : tanks) {
            float rest = tank.getCapacity() - tank.getOil();
            if (rest > biggestRest) {
                biggestRest = rest;
                largest = tank;
            }
        }
        return largest;
    }

    /**
     * Een methode om het object mooi uit te printen.
     * @return Een string met een representatie van de capaciteit en de hoeveelheid olie.
     */
    @Override
    public String toString() {
        return "Olietank " +
                "met capaciteit" + capacity +
                "en hoeveelheid olie" + oil;
    }

    /**
     * A method for checking if an oiltank has the same capacity as another oil tank
     * @param other The oiltank to compare this one with.
     * @return Returns true if the capacities are the same, false otherwise.
     */
    public boolean hasSameCapacity(Oiltank other) {
        return this.getCapacity() == other.getCapacity();
    }

    /**
     * A method for checking if an oiltank has the same amount of oil as another oil tank
     * @param other The oiltank to compare this one with.
     * @return Returns true if the amounts of oil are the same, false otherwise.
     */
    public boolean hasSameOil(Oiltank other) {
        return this.getOil() == other.getOil();
    }

}
