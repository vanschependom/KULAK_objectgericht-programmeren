package Chapter3;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of exceptions signaling illegal amounts in
 * transactions for bank accounts.
 *   Each illegal amount exception involves the illegal amount
 *   and the bank account.
 *
 * @version 2.0
 * @author  Eric Steegmans
 */
public class IllegalAmountException extends RuntimeException {

    /**
     * Initialize this new illegal amount exception with given
     * amount and given bank account.
     *
     * @param  amount
     *         The amount for this new illegal amount exception.
     * @param  account
     *         The bank account for this new illegal amount
     *         exception.
     * @post   The amount of this new illegal amount exception
     *         is equal to the given amount.
     *       | new.getAmount() == amount
     * @post   The bank account of this new illegal amount
     *         exception is the same as the given bank account.
     *       | new.getAccount() == account
     * @effect This new illegal amount exception is further
     *         initialized as a new runtime exception involving
     *         no diagnostic message and no cause.
     *       | super()
     */
    public IllegalAmountException(long amount, BankAccount account) {
        this.amount = amount;
        this.account = account;
    }

    /**
     * Return the amount of this illegal amount exception.
     */
    @Basic
    @Raw
    @Immutable
    public long getAmount() {
        return amount;
    }

    /**
     * Variable registering the amount of this illegal amount
     * exception.
     */
    private final long amount;

    /**
     * Return the bank account of this illegal amount exception.
     */
    @Basic
    @Raw
    @Immutable
    public BankAccount getAccount() {
        return account;
    }

    /**
     * Variable referencing the bank account of this illegal
     * amount exception.
     */
    private final BankAccount account;

}
