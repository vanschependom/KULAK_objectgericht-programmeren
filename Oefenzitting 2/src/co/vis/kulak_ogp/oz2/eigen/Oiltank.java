package co.vis.kulak_ogp.oz2.eigen;

/**
 * A class of tanks for storing oil, involving a capacity and a contents.
 * The capacity expresses the maximum amount of oil that can be stored
 * in a tank; the contents expresses the actual amount of oil stored.
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
     * If the given capacity is negative, a capacity of 0 is
     * registered effectively.
     * If the given contents is negative, an initial contents of 0
     * is registered effectively.
     * If the given contents would exceed the registered capacity, an
     * initial contents equal to that capacity is registered.
     */
    public OilTank(int capacity, int contents) {
        if (capacity > 0) {
            setCapacity(capacity);
        }
        fill(contents);
    }

    /**
     * Initialize a new oil tank with given capacity and no contents.
     * If the given capacity is negative, a capacity of 0 is
     * registered effectively.
     */
    public OilTank(int capacity) {
        // In a constructor another constructor can be invoked using this
        // as the name of the constructor. A given implementation of
        // this constructor is considered to be better than
        // repeating the same logic in multiple places:
        // 	if (capacity > 0) setCapacity(capacity);
        this(capacity, 0);
    }

    /**
     * Initialize a new oil tank with a capacity of 5000 and no contents.
     */
    public OilTank() {
        this(5000);
    }


    /**
     * Add the given amount of oil to this tank.
     * If the given amount is not positive, the contents of this tank
     * remains unchanged. If the given amount exceeds the remaining
     * capacity, this tank will be filled up to its maximum capacity.
     */
    public void fill(int amount) {
        if (amount > 0) {
            if (getContents() + amount < getCapacity()) {
                setContents(getContents() + amount);
            } else {
                fill();
            }
        }
        // Alternative for the nested if-statement.
        // setContents(Math.min(amount+getContents(),getCapacity()));
    }

    /**
     * Fill this tank to its full capacity with oil.
     */
    public void fill() {
        setContents(getCapacity());
    }

    /**
     * Extract the given amount of oil from this tank.
     * If the given amount is not positive, the contents of this tank
     * remains unchanged. If this tank does not store the given amount
     * of oil, its entire contents is extracted.
     */
    public void extract(int amount) {
        if (amount > 0) {
            if (amount > getContents()) {
                extract();
            } else {
                setContents(getContents() - amount);
            }
        }
    }

    /**
     * Extract the full contents from this tank.
     */
    public void extract() {
        setContents(0);
    }

    /**
     * Transfer the entire contents of the other tank to this tank.
     * If the other tank is not effective or coincides with this tank,
     * no oil is transferred.
     * If the full contents of the other tank does not fit in this tank,
     * the latter tank is filled to its capacity. In that case, the
     * the remaining oil is still stored in the other tank.
     */
    public void transferFrom(OilTank other) {
        if ( (other != null) && (other != this) ) {
            if (other.getContents() <= this.getFree()) {
                this.fill(other.getContents());
                other.extract();
            } else {
                other.extract(this.getFree());
                this.fill();
            }
        }
    }

    /**
     * Return the capacity of this oil tank.
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Return the contents of this oil tank.
     */
    public int getContents() {
        return this.content;
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
     */
    public boolean isFullerThan(OilTank other) {
        return (other != null) &&
                (this.getPercentageFilled() > other.getPercentageFilled());
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
     */
    public OilTank getMostFree(OilTank other1, OilTank other2) {
        OilTank result = this;
        if ( (other1 != null) && (other1.getFree() > result.getFree()) ) {
            result = other1;
        }
        if ( (other2 != null) && (other2.getFree() > result.getFree()) ) {
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
     */
    public boolean isSame(OilTank other) {
        return (other != null) &&
                (this.getCapacity() == other.getCapacity()) &&
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
     */
    private void setCapacity(int amount) {
        this.capacity = amount;
    }

    /*
     * Set the contents of this oil tank to the given amount.
     */
    private void setContents(int amount) {
        this.content = amount;
    }

    /*
     * Set the free space in this oil tank to the given amount.
     */
    private void setFree(int amount) {
        setContents(getCapacity() - amount);
    }
}


