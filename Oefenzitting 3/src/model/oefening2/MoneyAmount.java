package model.oefening2;

import java.math.BigInteger;

/**
 * A class of money amounts expressing an amount of money
 * in cents of a given currency.
 *  Objects of this class are actually containers (mutable
 *  objects) in which an amount of money can be registered.
 *  
 * @invar   Each money amount must be able to have its amount
 *          in cents as its amount in cents.
 *          | model.oefening2.MoneyAmount.canHaveAsAmountInCents(getAmount())
 * @invar   Each money amount must be able to have its currency
 *          as its currency.
 *          | model.oefening2.MoneyAmount.canHaveAsCurrency(getCurrency())
 */
public class MoneyAmount {

    /**
     * Variable referencing the amount involved in this money amount.
     * 
     * @invar   The referenced big integer is an acceptable amount for
     *          this amount of money.
     *          | model.oefening2.MoneyAmount.canHaveAsAmountInCents(amount)
     */
    private BigInteger amountInCents;

    /**
     * Variable referencing the currency of this money amount.
     * 
     * @invar   The referenced currency is an acceptable currency for
     *          this amount of money.
     *          | model.oefening2.MoneyAmount.canHaveAsCurrency(currency)
     */
    private Currency currency;
    
    
    
    /**
     * Initialize this new money amount with given amount in cents
     * and given currency.
     * 
     * @param   amountInCents
     *          The amount in cents for this new money amount.
     * @param   currency
     *          The currency for this new money amount.
     * @post    The amount in cents for this new money amount is
     *          set to the given amount in cents.
     *          | new.getAmountInCents() == amountInCents
     * @post    The currency for this new money amount is set
     *          to the given currency.
     *          | new.getCurrency() == currency
     * @throws  IllegalArgumentException
     *          This new money amount can not have the given amount
     *          in cents as its amount in cents.
     *          | ! model.oefening2.MoneyAmount.canHaveAsAmountInCents(amountInCents)
     * @throws  IllegalArgumentException
     *          This new money amount can not have the given currency
     *          as its currency.
     *          | ! model.oefening2.MoneyAmount.canHaveAsCurrency(currency)
     */
    public MoneyAmount(BigInteger amountInCents, Currency currency) throws IllegalArgumentException {
        if (! MoneyAmount.canHaveAsAmountInCents(amountInCents)) {
            throw new IllegalArgumentException("Non-effective amount!");
        }
        if (! MoneyAmount.canHaveAsCurrency(currency)) {
            throw new IllegalArgumentException("Non-effective currency!");
        }
        this.setAmountInCents(amountInCents);
        this.setCurrency(currency);
    }
    
    /**
     * Initialize this new money amount with to 0 Eurocents.
     * 
     * @post    The amount in cents for this new money amount is
     *          set to 0.
     *          | new.getAmountInCents().intValue() == 0
     * @post    The currency for this new money amount is set
     *          to the currency Euro.
     *          | new.getCurrency() == model.oefening2.Currency.getEuro()
     */
    public MoneyAmount() {
        this(BigInteger.valueOf(0), Currency.getEuro());
    }


    /**
     * Return the amount of money involved in this money amount [raw].
     *  Amounts are expressed in cents.
     */
    public BigInteger getAmountInCents() {
        return this.amountInCents;
    }
    

    /**
     * Set the amount of cents for this money amount to the given amount
     * of cents.
     * 
     * @param   amountInCents  The amount in cents to be registered.
     * @post    The amount of cents for this money amount is set to the
     *          given amount of cents.
     *          | new.getAmountInCents() == amountInCents
     */
    private void setAmountInCents(BigInteger amountInCents) {
        this.amountInCents = amountInCents;
    }


    /**
     * Return the currency of this money amount [raw].
     */
    public Currency getCurrency() {
        return this.currency;
    }



    /**
     * Set the currency for this money amount to the given currency.
     * 
     * @param   currency
     *          The currency to be registered.
     * @post    The currency for this money amount is set to the
     *          given currency.
     *          | new.getCurrency() == currency
     */
    private void setCurrency(Currency currency) {
        this.currency = currency;
    }



    /**
     * Change the value of this money amount to the given money
     * amount.
     * 
     * @param   target  The money amount to be registered. 
     * @post    The amount in cents for this money amount is set to the
     *          amount of cents of the given money amount.
     *          | new.getAmountInCents() == target.getAmountInCents()
     * @post    The currency for this money amount is set to the
     *          currency of the given money amount.
     *          | new.getCurrency() == target.getCurrency()
     * @throws  IllegalArgumentException
     *          The given money amount is not effective.
     *          | target == null
     */
    public void changeValue(MoneyAmount target) throws IllegalArgumentException {
        try {
            this.setAmountInCents(target.getAmountInCents());
            this.setCurrency(target.getCurrency());
        } catch (NullPointerException exc) {
            // The target money amount is not effective.
            throw new IllegalArgumentException("Non-effective money amount!");
        }
    }
    
    
    
    /**
     * Return a new money amount expressed in the given currency and
     * with the same value as this money amount.
     */
    public MoneyAmount toCurrency(Currency currency) {
        if (this.getCurrency() == currency) {
            return getCopy();
        }
        BigInteger conversion = BigInteger.valueOf(this.getCurrency().toCurrency(currency));
        BigInteger amount = getAmountInCents().multiply(conversion);
        amount = amount.divide(BigInteger.valueOf(1000000));
        return new MoneyAmount(amount, currency);
    }

    /**
     * Check whether this money amount is negative.
     * 
     * @return  True if the amount in cents of this money amount
     *          is negative; false otherwise.
     *          | result == (getAmountInCents().signum() == -1)
     */
    public boolean isNegative() {
        return this.getAmountInCents().signum() == -1;
    }
    
    /**
     * Return a new money amount reflecting the sum of this money
     * amount and the given money amount.
     *  The resulting money amount is expressed in the same currency
     *  as this money amount.
     */
    public MoneyAmount add(MoneyAmount other) {
        if (other == null) {
            return this.getCopy();
        }
        if (this.getCurrency() != other.getCurrency()) {
            other = other.toCurrency(this.getCurrency());
        }
        return new MoneyAmount(this.getAmountInCents().add(other.getAmountInCents()), 
                this.getCurrency());
    }

    /**
     * Return a new object that is identical to this money amount.
     * 
     * @return  The resulting money amount is a new effective money amount,
     *          with same amount in cents and the same currency as this
     *          money amount.
     *          | (result != null) && (result != this) &&
     *          | (result.getAmountInCents() == this.getAmountInCents()) &&
     *          | (result.getCurrency() == this.getCurrency())
     */
    public MoneyAmount getCopy() {
        // In a later version, this method is best replaced by a redefinition
        // of the predefined method 'clone', inherited from the root class
        // Object. That redefinition requires knowledge about inheritance
        // from classes and interfaces, that has not been covered at this
        // point.
        return new MoneyAmount(this.getAmountInCents(), this.getCurrency());
    }

    /**
     * Check whether this money amount is equal to the
     * given money amount.
     * 
     * @param   other  The money amount to compare with.
     * @return  True of the given money amount is effective, if
     *          the amount in cents of this money amount is equal
     *          to the amount of cents of the other money amount,
     *          and if the currency of this money amount is the
     *          same as the currency of the other money amount;
     *          false otherwise.
     *          | result ==
     *          |   (other != null) &&
     *          |   (getAmountInCents().equals(other.getAmountInCents())) &&
     *          |   (getCurrency() == other.getCurrency())
     */
    public boolean isEqualTo(MoneyAmount other) {
        try {
            return (this.getAmountInCents().equals(other.getAmountInCents()))
                    && (this.getCurrency() == other.getCurrency());
        } catch (ClassCastException exc) {
            // The other money amount is not effective.
            return false;
        }
    }

    /**
     * Return a textual representation of this money amount
     */
    public String toString() {
        String result = "[" + this.amountInCents;
        if (currency == Currency.getEuro())
            result += " Euro";
        else
            result += " USD";
        return result + "]";
    }


    /**
     * Check whether this money amount can have the given amount
     * as its amount.
     * 
     * @param   amount  The amount to be checked.
     * @return  True if the given amount is effective; false otherwise.
     *          | result == (amount != null)
     */
    public static boolean canHaveAsAmountInCents(BigInteger amount) {
        return amount != null;
    }
    
    /**
     * Check whether this money amount can have the currency
     * as its currency.
     * 
     * @param   currency  The currency to be checked.
     * @return  True if the given currency is effective; false otherwise.
     *          | result == (currency != null)
     */
    public static boolean canHaveAsCurrency(Currency currency) {
        return currency != null;
    }

}