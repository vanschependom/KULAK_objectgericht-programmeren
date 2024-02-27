
package kulak_ogp_oz2.eigen;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of tanks for storing oil, involving a capacity and a contents.
 * The capacity expresses the maximum amount of oil that can be stored
 * in a tank; the contents expresses the actual amount of oil stored.
 *
 * @author      Vincent Van Schependom
 * @version     2.0
 * @invar       The capacity of the tank must at all times be legal
 *              | isValidCapacity(getCapacity())
 * @invar       The content of the tank must at all times be legal
 *              | canHaveAsContent(getContents())
 */
class OilTank {

    /*
     * The capacity of this oil tank expressed.
     */
    private int capacity;
    /*
     * The amount of oil currently stored in this oil tank.
     */
    private int content;

    /**
     * Initialize a new oil tank with given capacity and contents.
     * @param   capacity
     * @param   contents
     * @pre     The capacity must be legal
     *          | isValidCapacity(capacity)
     * @pre     The contents must be legal
     *          | canHaveAsContents(contents)
     * @post    New oil tank with given capacity and contents.
     */
    public OilTank(int capacity, int contents) {
        setCapacity(capacity);
        fill(contents);
    }

    /**
     * Initialize a new oil tank with given capacity and no contents.
     * If the given capacity is negative, a capacity of 0 is
     * registered effectively.
     * @param   capacity
     * @pre     The capacity must be legal
     *          | isValidCapacity(capacity)
     * @effect  | this(capacity,0)
     */
    public OilTank(int capacity) {
        this(capacity, 0);
    }

    /**
     * Initialize a new oil tank with a capacity of 5000 and no contents.
     * @effect   A new oil tank is created with a capacity of 5000 and no contents.
     *           | this(5000)
     */
    public OilTank() {
        this(5000);
    }

    /**
     * Add the given amount of oil to this tank.
     *
     * @param   amount
     * @pre     The amount must be strictly positive
     *          | amount > 0
     * @pre     The current amount, incremented with the amount to be
     *          added, must not cause overflow
     *          | this.amount < Integer.MAX_VALUE - amount
     * @pre     The current amount, incremented with the amount to be
     *          added, must be a legal content
     *          | canHaveAsContent(this.amount + amount)
     */
    public void fill(int amount) {
        setContents(getContents()+amount);
    }

    /**
     * Fill this tank to its full capacity with oil.
     * @post    The tank is fully filled
     *          | new.getContents() = this.getCapacity()
     */
    public void fill() {
        setContents(getCapacity());
    }

    /**
     * Extract the given amount of oil from this tank.
     * @param   amount
     * @pre     The amount must be positive
     *          | amount > 0
     * @pre     The amount must be less than or equal to the contents
     *          | amount <= this.getContents()
     * @post    The amount is subtracted from the contents
     */
    public void extract(int amount) {
        setContents(getContents() - amount);
    }

    /**
     * Extract the full contents from this tank.
     * @post    The content is set to 0.
     *          | new.getContents() == 0
     */
    public void extract() {
        setContents(0);
    }

    /**
     * Transfer the entire contents of the other tank to this tank.
     * @pre     The other account must be effective
     *          | other != null
     * @pre     The other account must be different from this bank account
     *          | other != this
     * @pre     The full contents of the other tank must fit in this tank
     *          | other.getContents() <= this.getFree()
     * @post    The contents are incremented with the contents of the other tank
     *          | new.getContents() = this.getContents() + other.getContents()
     * @post    The contents of the other tank are set to 0.
     *          | other.getContents() = 0
     */
    public void transferFrom(OilTank other) {
        this.fill(other.getContents());
        other.extract();
    }

    /**
     * Return the capacity of this oil tank.
     */
    @Basic
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * A checker for the capacity
     * @param   capacity
     * @return  True if and only if the capacity is valid
     *          | result == capacity >= 0
     */
    public boolean isValidCapacity(int capacity) {
        return capacity >= 0;
    }

    /**
     * Getter for the contents
     * @returns The content.
     */
    public int getContents() {
        return this.content;
    }

    /**
     * A checker for the contents.
     * @param   content
     *          The content to be checked
     * @return  Returns whether a content is legal.
     *          | result == (content >= 0)
     *              && (content <= getCapacity())
     */
    public boolean canHaveAsContents(int content) {
        return (content >= 0) && (content <= getCapacity());
    }

    /**
     * Return the free amount of space in this oil tank.
     * The difference between the capacity of this oil tank
     * and its contents.
     */
    public int getFree() {
        return getCapacity() - getContents();
    }

    /**
     * Check whether this tank is relatively more filled than the other tank.
     * True if the other tank is effective and if the relatively filled volume
     * of this tank exceeds that of the other tank; false otherwise.
     * @param   other
     * @pre     | other != null
     */
    public boolean isFullerThan(OilTank other) {
        return this.getPercentageFilled() > other.getPercentageFilled();
    }

    /**
     * Return the relative amount (the percentage) of the capacity of this tank
     * filled with oil.
     * For a tank with a capacity of 0, 100% is returned.
     */
    public double getPercentageFilled() {
        if (getCapacity() == 0) {
            return 100.0;
        } else {
            return (100.0 * getContents()) / getCapacity();
        }
    }

    /**
     * Return the tank with the largest unused capacity out of this tank and
     * the two other tanks.
     * @pre     | other != null
     */
    public OilTank getMostFree(OilTank other1, OilTank other2) {
        OilTank result = this;
        if (other1.getFree() > result.getFree()) {
            result = other1;
        } else {
            result = other2;
        }
        return result;
    }

    /**
     * Return a textual representation of this oil tank.
     * The resulting string has the form "Oil Tank with Capacity xxx and
     * Contents yyy".
     */
    public String toString() {
        return "Oil Tank with Capacity " + getCapacity() +
                " and Contents " + getContents();
    }

    /**
     * Check whether this tank is identical to the other tank.
     * True if the capacity and the contents of this tank are equal to
     * the capacity, respectively the contents of the other tank.
     * @pre     | other != null
     */
    public boolean isSame(OilTank other) {
        return (this.getCapacity() == other.getCapacity()) &&
                (this.getContents() == other.getContents());
    }

    /**
     * Return a new tank as an exact copy of this oil tank.
     */
    public OilTank getCopy() {
        return new OilTank(getCapacity(), getContents());
    }

    /*
     * Set the capacity of this oil tank to the given amount.
     * @pre     | amount > 0
     */
    private void setCapacity(int amount) {
        this.capacity = amount;
    }

    /*
     * Set the contents of this oil tank to the given amount.
     * @pre     | amount > 0
     */
    private void setContents(int amount) {
        this.content = amount;
    }

    /*
     * Set the free space in this oil tank to the given amount.
     * @pre     | amount > 0
     */
    private void setFree(int amount) {
        setContents(getCapacity() - amount);
    }
}


