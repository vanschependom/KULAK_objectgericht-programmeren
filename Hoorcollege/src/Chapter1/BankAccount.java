package Chapter1;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of bank accounts involving a bank code, a number,
 * a credit limit, a balance limit, a balance and a blocking
 * facility.
 *
 * @version 2.0
 * @author  Eric Steegmans
 */
class BankAccount {

    /**
     * Initialize this new bank account with given number, given
     * balance and given blocked state.
     *
     * @param  number
     *         The number for this new bank account.
     * @param  balance
     *         The balance for this new bank account.
     * @param  isBlocked
     *         The blocked state for this new bank account.
     * @post   If the given number is not negative, the initial
     *         number of this new bank account is equal to the
     *         given number. Otherwise, its initial number is
     *         equal to 0.
     * @post   If the given balance is not below the credit limit
     *         and not above the balance limit, the initial
     *         balance of this new bank account is equal to the
     *         given balance. Otherwise, its initial balance is
     *         equal to 0.
     * @post   The initial blocked state of this new bank account
     *         is equal to the given flag.
     */
    public BankAccount(int number, long balance, boolean isBlocked) {
        if (number < 0)
            number = 0;
        this.number = number;
        setBalance(balance);
        setBlocked(isBlocked);
    }

    /**
     * Initialize this new bank account as an unblocked account
     * with given number and given balance.
     *
     * @param  number
     *         The number for this new bank account.
     * @param  balance
     *         The balance for this new bank account.
     * @effect This new bank account is initialized with the
     *         given number as its number, the given balance as
     *         its balance, and false as its blocked state.
     */
    public BankAccount(int number, long balance) {
        this(number, balance, false);
    }

    /**
     * Initialize this new bank account as an unblocked account
     * with given number and zero balance.
     *
     * @param  number
     *         The number for this new bank account.
     * @effect This new bank account is initialized with the
     *         given number as its number and zero as its initial
     *         balance.
     */
    public BankAccount(int number) {
        this(number, 0);
    }

    /**
     * Return the number of this bank account.
     *   The number of a bank account serves to distinguish that
     *   account from all other bank accounts.
     */
    @Basic
    @Immutable
    public int getNumber() {
        return this.number;
    }

    /**
     * Variable registering the number of this bank account.
     */
    private final int number;

    /**
     * Return the bank code that applies to all bank accounts.
     *   The bank code identifies the bank to which all bank
     *   accounts belong.
     */
    @Basic
    @Immutable
    public static int getBankCode() {
        return bankCode;
    }

    /**
     * Variable registering the bank code that applies to all
     * bank accounts.
     */
    private static final int bankCode = 123;

    /**
     * Return the balance of this bank account.
     *   The balance of a bank account expresses the amount of
     *   money available on that account.
     */
    @Basic
    public long getBalance() {
        return this.balance;
    }

    /**
     * Check whether this bank account has a higher balance than
     * the given amount of money.
     *
     * @param  amount
     *         The amount of money to compare with.
     * @return True if and only if the balance of this bank
     *         account is greater than the given amount.
     */
    public boolean hasHigherBalanceThan(long amount) {
        return getBalance() > amount;
    }

    /**
     * Check whether this bank account has a higher balance than
     * the other bank account.
     *
     * @param  other
     *         The bank account to compare with.
     * @return True if and only if the other bank account is
     *         effective, and if this bank account has a higher
     *         balance than the balance of the other bank
     *         account.
     */
    public boolean hasHigherBalanceThan(BankAccount other) {
        return ((other != null) && (this.hasHigherBalanceThan(other
                .getBalance())));
    }

    /**
     * Deposit the given amount of money to this bank account.
     *
     * @param  amount
     *         The amount of money to be deposited.
     * @post   If the given amount of money is positive, and if
     *         the old balance of this bank account is not above
     *         the balance limit decremented with the given
     *         amount of money, the new balance of this bank
     *         account is equal to the old balance of this bank
     *         account incremented with the given amount of
     *         money.
     */
    public void deposit(long amount) {
        if ((amount > 0) && (getBalance() <= getBalanceLimit() - amount))
            setBalance(getBalance() + amount);
    }

    /**
     * Withdraw the given amount of money from this bank account.
     *
     * @param  amount
     *         The amount of money to be withdrawn.
     * @post   If the given amount of money is positive, and if
     *         this bank account is not blocked, and if the old
     *         balance of this bank account is not below the
     *         credit limit incremented with the given amount of
     *         money, the new balance of this bank account is
     *         equal to the old balance of this bank account
     *         decremented with the given amount of money.
     */
    public void withdraw(long amount) {
        if ((amount > 0) && (!isBlocked())
                && (getBalance() >= getCreditLimit() + amount))
            setBalance(getBalance() - amount);
    }

    /**
     * Transfer the given amount of money from this bank account
     * to the given destination account.
     *
     * @param  amount
     *         The amount of money to be transferred.
     * @param  destination
     *         The bank account to transfer the money to.
     * @effect If the given destination account is effective and
     *         not the same as this bank account, and if this
     *         bank account is not blocked, and if the old
     *         balance of this bank account is not below the
     *         credit limit incremented with the given amount of
     *         money, and if the old balance of the given
     *         destination account is not above the balance
     *         limit decremented with the given amount of money,
     *         the given amount of money is withdrawn from this
     *         bank account, and deposited to the given
     *         destination account.
     */
    public void transferTo(long amount, BankAccount destination) {
        if ((amount > 0) && (destination != null) && (!this.isBlocked())
                && (this.getBalance() >= getCreditLimit() + amount)
                && (destination.getBalance() <= getBalanceLimit() - amount)) {
            this.withdraw(amount);
            destination.deposit(amount);
        }
    }

    /**
     * Set the balance of this bank account to the given balance.
     *
     * @param  balance
     *         The new balance for this bank account.
     * @post   If the given balance is not below the credit limit
     *         and not above the balance limit, the new balance
     *         of this bank account is equal to the given
     *         balance.
     */
    private void setBalance(long balance) {
        if ((balance >= getCreditLimit()) && (balance <= getBalanceLimit()))
            this.balance = balance;
    }

    /**
     * Variable registering the balance of this bank account.
     */
    private long balance = 0;

    /**
     * Return the credit limit that applies to all bank accounts.
     *   The credit limit expresses the lowest possible value for
     *   the balance of a bank account.
     */
    @Basic
    public static long getCreditLimit() {
        return creditLimit;
    }

    /**
     * Set the credit limit that applies to all bank accounts to
     * the given credit limit.
     *
     * @param  creditLimit
     *         The new credit limit for all bank accounts.
     * @post   If the given credit limit is not above the credit
     *         limit that currently applies to all bank accounts,
     *         the new credit limit that applies to all bank
     *         accounts is equal to the given credit limit.
     */
    public static void setCreditLimit(long creditLimit) {
        if (creditLimit < getCreditLimit())
            BankAccount.creditLimit = creditLimit;
    }

    /**
     * Variable registering the credit limit that applies to all
     * bank accounts.
     */
    private static long creditLimit = 0;

    /**
     * Return the balance limit that applies to all bank
     * accounts.
     *   The balance limit expresses the highest possible value
     *   for the balance of a bank account.
     */
    @Basic
    @Immutable
    public static long getBalanceLimit() {
        return balanceLimit;
    }

    /**
     * Variable registering the balance limit that applies to all
     * bank accounts.
     */
    private static final long balanceLimit = Long.MAX_VALUE;

    /**
     * Check whether this bank account is blocked.
     *   Some methods have no effect when invoked against blocked
     *   accounts.
     */
    @Basic
    public boolean isBlocked() {
        return this.isBlocked;
    }

    /**
     * Set the blocked state of this bank account according to
     * the given flag.
     *
     * @param  flag
     *         The new blocked state for this bank account.
     * @post   The new blocked state of this bank account is
     *         equal to the given flag.
     */
    public void setBlocked(boolean flag) {
        this.isBlocked = flag;
    }

    /**
     * Block this bank account.
     *
     * @effect The blocked state of this bank account is set to
     *         true.
     */
    public void block() {
        setBlocked(true);
    }

    /**
     * Unblock this bank account.
     *
     * @effect The blocked state of this bank account is set to
     *         false.
     */
    public void unblock() {
        setBlocked(false);
    }

    /**
     * Variable registering the blocked state of this bank
     * account.
     */
    private boolean isBlocked = false;

}
