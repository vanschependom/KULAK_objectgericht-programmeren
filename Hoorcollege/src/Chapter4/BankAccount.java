package Chapter4;

import be.kuleuven.cs.som.annotate.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A class of bank accounts involving a bank code, a number,
 * a credit limit, a balance and a blocking facility.
 *
 * @invar   The bank code that applies to all bank accounts
 *          must be a valid bank code.
 *        | isValidBankCode(getBankCode())
 * @invar   Each bank account can have its number as its
 *          number.
 *        | canHaveAsNumber(getNumber())
 * @invar   Each bank account can have its credit limit as its
 *          credit limit.
 *        | canHaveAsCreditLimit(getCreditLimit())
 * @invar   Each bank account can have its balance as its
 *          balance.
 *        | canHaveAsBalance(getBalance())
 *
 * @version 2.0
 * @author  Eric Steegmans
 */
class BankAccount {

    /**
     * Initialize this new bank account with given number, given
     * credit limit, given balance and given blocked state.
     *
     * @param  number
     *         The number for this new bank account.
     * @param  creditLimit
     *         The credit limit for this new bank account.
     * @param  balance
     *         The balance for this new bank account.
     * @param  isBlocked
     *         The initial value for the blocked state of this
     *         new bank account.
     * @post   The new number of this new bank account is equal
     *         to the given number.
     *       | new.getNumber() == number
     * @post   The new credit limit of this new bank account is
     *         equal to the given credit limit.
     *       | new.getCreditLimit().equals(creditLimit)
     * @post   The new balance of this new bank account is equal
     *         to the given balance.
     *       | new.getBalance().equals(balance)
     * @post   The new blocked state of this new bank account is
     *         equal to the given flag.
     *       | new.isBlocked() == isBlocked
     * @throws IllegalAmountException(balance,this)
     *         The given balance is not a valid balance for any
     *         bank account, or it does not match with the given
     *         credit limit.
     *       |  ( (! isPossibleBalance(balance))
     *       | || (! matchesBalanceCreditLimit
     *       |          (balance,creditLimit)) )
     * @throws IllegalAmountException(creditLimit,this)
     *         The given credit limit is not a valid credit limit
     *         for any bank account, or it does not match with
     *         the given balance.
     *       |  ( (! isPossibleCreditLimit(creditLimit))
     *       | || (! matchesBalanceCreditLimit
     *       |          (balance,creditLimit)) )
     */
    @Raw
    public BankAccount(int number, MoneyAmount creditLimit,
                       MoneyAmount balance, boolean isBlocked)
            throws IllegalNumberException, IllegalAmountException {
        if (!canHaveAsNumber(number))
            throw new IllegalNumberException(number, this);
        this.number = number;
        if (!isPossibleCreditLimit(creditLimit))
            throw new IllegalAmountException(creditLimit, this);
        this.creditLimit = creditLimit;
        setBalance(balance);
        setBlocked(isBlocked);
        allAccountNumbers.add(number);
    }

    /**
     * Initialize this new bank account to an unblocked account
     * with given number, with a credit limit of -1000,00 EUR and
     * with a balance of 0,00 EUR.
     *
     * @param  number
     *         The number for this new bank account.
     * @effect This new bank account is initialized with the
     *         given number as its number, with -1000 EUR as its
     *         credit limit, with 0 EUR as its balance, and with
     *         false as its blocked state.
     *       | this(number,
     *       |      new MoneyAmount(new BigDecimal(-1000)),
     *       |      MoneyAmount.EUR_0,false)
     */
    @Raw
    public BankAccount(int number) throws IllegalNumberException {
        this(number, new MoneyAmount(new BigDecimal(-1000)), MoneyAmount.EUR_0,
                false);
    }

    public void terminate() {
        allAccountNumbers.remove(this.getNumber());
    }

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
     * Check whether the given bank code is a valid bank code for
     * all bank accounts.
     *
     * @param  bankCode
     *         The bank code to check.
     * @return True if and only if the given bank code is strict
     *         positive.
     *       | result == (bankCode > 0)
     */
    public static boolean isValidBankCode(int bankCode) {
        return bankCode > 0;
    }

    /**
     * Variable registering the bank code that applies to all
     * bank accounts.
     */
    private static final int bankCode = 123;

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
     * Check whether this bank account can have the given number
     * as its number.
     *
     * @param  number
     *         The number to check.
     * @return True if and only if the given number is strict
     *         positive, and if no other bank account has the
     *         given number as its number.
     *       | result ==
     *       |    ( (number > 0)
     *       |   && (for each account in BankAccount:
     *       |        ( (account == this)
     *       |       || (account.getNumber() != number) ) ) )
     */
    @Raw
    public boolean canHaveAsNumber(int number) {
        return (number > 0)
                && ((this.getNumber() == number) || (!allAccountNumbers
                .contains(number)));
    }

    /**
     * Variable registering the number of this bank account.
     */
    private final int number;

    /**
     * Variable referencing a set collecting all account numbers
     * that have been used so far.
     *    More information on sets and other data structures is given
     *    in the second part of the book.
     */
    private static final Set<Integer> allAccountNumbers = new HashSet<Integer>();

    /**
     * Return the credit limit for this bank account.
     *   The credit limit expresses the lowest possible value for
     *   the balance of a bank account.
     */
    @Basic
    @Raw
    public MoneyAmount getCreditLimit() {
        return this.creditLimit;
    }

    /**
     * Check whether the given credit is a possible credit limit
     * for any bank account.
     *
     * @param  creditLimit
     *         The credit limit to check.
     * @return True if and only if the given credit limit is
     *         effective and not positive.
     *       | result ==
     *       |   ( (creditLimit != null)
     *       |  && (creditLimit.signum() <= 0) )
     */
    public static boolean isPossibleCreditLimit(MoneyAmount creditLimit) {
        return ((creditLimit != null) && (creditLimit.signum() <= 0));
    }

    /**
     * Check whether this bank account can have the given credit
     * limit as its credit limit.
     *
     * @param  creditLimit
     *         The credit limit to check.
     * @return True if and only if the given credit limit is a
     *         possible credit limit for any bank account, and if
     *         the given credit limit matches with the balance of
     *         this bank account.
     *       | result ==
     *       |   ( isPossibleCreditLimit(creditLimit)
     *       |  && matchesBalanceCreditLimit
     *       |         (getBalance(),creditLimit) )
     */
    @Raw
    public boolean canHaveAsCreditLimit(MoneyAmount creditLimit) {
        return (isPossibleCreditLimit(creditLimit) && matchesBalanceCreditLimit(
                getBalance(), creditLimit));
    }

    /**
     * Set the credit limit for this bank account to the given
     * credit limit.
     *
     * @param  creditLimit
     *         The new credit limit for this bank account.
     * @post   The credit limit for this bank account is equal to
     *         the given credit limit.
     *       | new.getCreditLimit().equals(creditLimit)
     * @throws IllegalAmountException(creditLimit,this)
     *         This bank account cannot have the given credit
     *         limit as its credit limit.
     *       | ! canHaveAsCreditLimit(creditLimit)
     */
    @Raw
    public void setCreditLimit(MoneyAmount creditLimit)
            throws IllegalAmountException {
        if (!canHaveAsCreditLimit(creditLimit))
            throw new IllegalAmountException(creditLimit, this);
        this.creditLimit = creditLimit;
    }

    /**
     * Variable referencing the credit limit for this bank
     * account.
     */
    private MoneyAmount creditLimit = MoneyAmount.EUR_0;

    /**
     * Return the balance of this bank account.
     *   The balance of a bank account expresses the amount of
     *   money available on that account.
     */
    @Basic
    @Raw
    public MoneyAmount getBalance() {
        return this.balance;
    }

    /**
     * Check whether the balance is a possible balance for any
     * bank account.
     *
     * @param  balance
     *         The balance to check.
     * @return True if and only if the given balance is
     *         effective.
     *       | result == (balance != null)
     */
    public static boolean isPossibleBalance(MoneyAmount balance) {
        return (balance != null);
    }

    /**
     * Check whether this bank account can have the given
     * balance as its balance.
     *
     * @param  balance
     *         The balance to check.
     * @return True if and only if the given balance is a
     *         possible balance for any bank account, and if the
     *         given balance matches with the credit limit for
     *         this bank account.
     *       | result ==
     *       |   ( isPossibleBalance(balance)
     *       |  && matchesBalanceCreditLimit
     *       |         (balance,getCreditLimit())) )
     */
    @Raw
    public boolean canHaveAsBalance(MoneyAmount balance) {
        return (isPossibleBalance(balance) && matchesBalanceCreditLimit(
                balance, getCreditLimit()));
    }

    /**
     * Check whether the given balance matches with the given
     * credit limit.
     *
     * @param  balance
     *         The balance to check.
     * @param  creditLimit
     *         The credit limit to check.
     * @return True if and only if the given balance and the
     *         given credit limit are both effective, if they
     *         both use the same currency and if the given
     *         balance exceeds the given credit limit.
     *        | result ==
     *        |   ( (balance != null) && (creditLimit != null)
     *        |  && (balance.getCurrency() ==
     *        |         creditLimit.getCurrency())
     *        |  && (balance.compareTo(creditLimit) > 0) )
     */
    public static boolean matchesBalanceCreditLimit(MoneyAmount balance,
                                                    MoneyAmount creditLimit) {
        return ((balance != null) && (creditLimit != null)
                && (balance.getCurrency() == creditLimit.getCurrency()) && (balance
                .compareTo(creditLimit) > 0));
    }

    /**
     * Return a boolean reflecting whether this bank account has
     * a higher balance than the given amount of money.
     *
     * @param  amount
     *         The amount of money to compare with.
     * @return True if and only if the balance of this bank
     *         account is greater than the given amount.
     *       | result == (getBalance().compareTo(amount) > 0)
     * @throws IllegalArgumentException
     *         The given amount is not effective or its currency
     *         differs from the currency of this bank account.
     *       |  ( (amount == null)
     *       | || (amount.getCurrency() !=
     *       |        this.getBalance().getCurrency()) )
     */
    public boolean hasHigherBalanceThan(MoneyAmount amount)
            throws IllegalArgumentException {
        if (amount == null)
            throw new IllegalArgumentException("Non effective amount!");
        if (amount.getCurrency() != this.getBalance().getCurrency())
            throw new IllegalArgumentException("Different currencies");
        return getBalance().compareTo(amount) > 0;
    }

    /**
     * Return a boolean reflecting whether this bank account has
     * a higher balance than the other bank account.
     *
     * @param  other
     *         The bank account to compare with.
     * @return True if and only if this bank account has a higher
     *         balance than the balance of the other bank
     *         account.
     *       | result ==
     *       |   this.hasHigherBalanceThan(other.getBalance())
     * @throws IllegalArgumentException
     *         The other bank account is not effective or its
     *         currency differs from the currency of this bank
     *         account.
     *       |  ( (other == null)
     *       | || (other.getBalance().getCurrency() !=
     *       |        this.getBalance().getCurrency()) )
     */
    public boolean hasHigherBalanceThan(BankAccount other)
            throws IllegalArgumentException {
        if (other == null)
            throw new IllegalArgumentException("Non effective bank account!");
        return this.hasHigherBalanceThan(other.getBalance());
    }

    /**
     * Return a boolean reflecting whether this bank account can
     * accept the given amount for deposit.
     *
     * @param  amount
     *         The amount to be checked.
     * @return True if and only if the given amount is effective
     *         and positive.
     *       | result ==
     *       |   ( (amount != null)
     *       |  && (amount.signum() > 0) )
     */
    public boolean canAcceptForDeposit(MoneyAmount amount) {
        return ((amount != null) && (amount.signum() > 0));
    }

    /**
     * Deposit the given amount of money to this bank account.
     *
     * @param  amount
     *         The amount of money to be deposited.
     * @post   The new balance of this bank account is equal to
     *         the old balance of this bank account incremented
     *         with the given amount of money.
     *       | new.getBalance().equals(
     *       |     this.getBalance().add(amount))
     * @throws IllegalAmountException(amount,this)
     *         This bank account cannot accept the given amount
     *         for deposit.
     *       | ! canAcceptForDeposit(amount)
     */
    public void deposit(MoneyAmount amount) throws IllegalAmountException {
        if (!canAcceptForDeposit(amount))
            throw new IllegalAmountException(amount, this);
        setBalance(getBalance().add(amount));
    }

    /**
     * Return a boolean reflecting whether this bank account can
     * accept the given amount for withdraw.
     *
     * @param  amount
     *         The amount to be checked.
     * @return True if and only if the given amount is effective
     *         and positive, if this bank account is not blocked,
     *         and if this bank account can have its current
     *         balance decremented with the given amount as its
     *         balance.
     *       | result ==
     *       |   ( (amount != null)
     *       |  && (amount.signum() > 0)
     *       |  && (! isBlocked())
     *       |  && canHaveAsBalance
     *       |         (getBalance().subtract(amount)) )
     */
    public boolean canAcceptForWithdraw(MoneyAmount amount) {
        return ((amount != null) && (amount.signum() > 0) && (!isBlocked()) && canHaveAsBalance(getBalance()
                .subtract(amount)));
    }

    /**
     * Withdraw the given amount of money from this bank account.
     *
     * @param  amount
     *         The amount of money to be withdrawn.
     * @post   The new balance of this bank account is equal to
     *         the old balance of this bank account decremented
     *         with the given amount of money.
     *       | new.getBalance().equals(
     *       |     this.getBalance().subtract(amount))
     * @throws IllegalAmountException(amount.negate(),this)
     *         This bank account cannot accept the given amount
     *         for withdraw.
     *       | ! canAcceptForWithdraw(amount)
     */
    public void withdraw(MoneyAmount amount) throws IllegalAmountException {
        if (!canAcceptForWithdraw(amount))
            throw new IllegalAmountException(amount != null ? amount.negate()
                    : null, this);
        setBalance(getBalance().subtract(amount));
    }

    /**
     * Transfer the given amount of money from this bank account
     * to the given destination account.
     *
     * @param  amount
     *         The amount of money to be transferred.
     * @param  destination
     *         The bank account to transfer the money to.
     * @effect The given amount of money is withdrawn from this
     *         bank account, and deposited to the given
     *         destination account.
     *       |  ( this.withdraw(amount)
     *       | && destination.deposit(amount) )
     * @throws IllegalArgumentException
     *         The given destination account is not effective or
     *         it is the same as this bank account.
     *       | (destination == null) || (destination == this)
     */
    public void transferTo(MoneyAmount amount, BankAccount destination)
            throws IllegalArgumentException, IllegalAmountException {
        boolean partialTransfer = false;
        try {
            if ((destination == null) || (destination == this))
                throw new IllegalArgumentException("Illegal destination!");
            withdraw(amount);
            partialTransfer = true;
            destination.deposit(amount);
            partialTransfer = false;
        } finally {
            if (partialTransfer)
                deposit(amount);
        }
    }

    /**
     * Set the balance for this bank account to the given
     * balance.
     *
     * @param  balance
     *         The new balance for this bank account.
     * @post   The new balance for this bank account is the same
     *         as the given balance.
     *       | new.getBalance() == balance
     * @throws IllegalAmountException(balance,this)
     *         This bank account cannot have the given balance
     *         as its balance.
     *       | ! canHaveAsBalance(balance)
     */
    @Raw
    @Model
    private void setBalance(MoneyAmount balance) throws IllegalAmountException {
        if (!canHaveAsBalance(balance))
            throw new IllegalAmountException(balance, this);
        this.balance = balance;
    }

    /**
     * Variable referencing the balance of this bank account.
     */
    private MoneyAmount balance;

    /**
     * Return a boolean reflecting whether this bank account is
     * blocked.
     *   Some methods will have no effect when invoked against
     *   blocked accounts.
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
     *         The blocked state to be registered.
     * @post   The new blocked state of this bank account is set
     *         according to the given flag.
     */
    @Raw
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
     * Variable registering the blocked state of this account.
     */
    private boolean isBlocked = false;

    /**
     * Return a textual representation of this bank account.
     *
     * @return The resulting string mentions "Bank Account" as
     *         the name of the class of this bank account.
     *       | result.contains("Bank Account")
     * @return The resulting string contains the number of this
     *         bank account.
     *       | result.contains(String.valueOf(getNumber()))
     */
    @Override
    public String toString() {
        return "Bank Account\n" + "  number: " + getNumber() + "\n";
    }

    /**
     * Return a clone of this bank account.
     *
     * @throws CloneNotSupportedException
     *         Always.
     *         | true
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Bank acounts cannot be cloned!");
    }

}