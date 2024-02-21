package Chapter3;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of bank accounts involving a bank code, a number,
 * a credit limit, a balance limit, a balance and a blocking
 * facility.
 *
 * @invar   The bank code that applies to all bank accounts
 *          must be a valid bank code.
 *        | isValidBankCode(getBankCode())
 * @invar   The bank code that applies to all bank accounts
 *          must be a valid bank code.
 *          | isValidBankCode(getBankCode())
 * @invar   Each bank account can have its number as its
 *          number.
 *          | canHaveAsNumber(getNumber())
 * @invar   Each bank account can have its credit limit
 *          as its credit limit.
 *          | canHaveAsCreditLimit(getCreditLimit())
 * @invar   The balance limit that applies to all bank accounts
 *          must be a valid balance limit.
 *          | isValidBalanceLimit(getBalanceLimit())
 * @invar   Each bank account can have its balance as its
 *          balance.
 *          | canHaveAsBalance(getBalance())
 *
 * @version 2.0
 * @author  Eric Steegmans
 */
class BankAccount {

    /**
     * Initialize this new bank account with given number, given
     * credit limit, given balance and given blocked state.

     * @param  number
     *         The number for this new bank account.
     * @param  creditLimit
     *         The credit limit for this new bank account.
     * @param  balance
     *         The balance for this new bank account.
     * @param  isBlocked
     *         The blocked state of this new bank account.
     * @post   The new number of this new bank account is equal
     *         to the given number.
     *       | new.getNumber() == number
     * @post   The new credit limit for this new bank account is
     *         equal to the given credit limit.
     *       | new.getCreditLimit() == creditLimit
     * @post   The new balance for this new bank account is equal
     *         to the given balance.
     *       | new.getBalance() == balance
     * @effect The blocked state for this new bank account is set
     *         to the given flag.
     *       | setBlocked(isBlocked)
     * @throws IllegalNumberException(number,this)
     *         This new bank account cannot have the given number
     *         as its number.
     *       | ! canHaveAsNumber(number)
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
    public BankAccount(int number, long creditLimit, long balance,
                       boolean isBlocked) throws IllegalNumberException,
            IllegalAmountException {
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
     * with given number, with credit limit 1000 and zero balance.
     *
     * @param  number
     *         The number for this new bank account.
     * @effect This new bank account is initialized with the
     *         given number as its number, 1000 as its credit
     *         limit, zero as its initial balance, and false as
     *         its initial blocked state.
     *       | this(number,-1000L,0L,false)
     */
    @Raw
    public BankAccount(int number) throws IllegalNumberException {
        this(number, -1000L, 0L, false);
    }

    /**
     * Terminate this bank account.
     *
     * @note    We need a terminator to recuperate account numbers
     *          that are no longer in use. The semantics of this tyoe
     *          of method is discussed in more detail in part II.
     */
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
     * Variable registering the number of this account.
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
    public long getCreditLimit() {
        return this.creditLimit;
    }

    /**
     * Check whether the given credit is a possible credit limit
     * for any bank account.
     *
     * @param  creditLimit
     *         The credit limit to check.
     * @return True if and only if the given credit limit is not
     *         positive.
     *       | result == (creditLimit <= 0)
     */
    public static boolean isPossibleCreditLimit(long creditLimit) {
        return creditLimit <= 0;
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
    public boolean canHaveAsCreditLimit(long creditLimit) {
        return (isPossibleCreditLimit(creditLimit) && matchesBalanceCreditLimit(
                getBalance(), creditLimit));
    }

    /**
     * Set the credit limit for this bank account to the given
     * credit limit

     * @param  creditLimit
     *         The new credit limit for this bank account.
     * @post   The new credit limit for this bank account is
     *         equal to the given credit limit.
     *       | new.getCreditLimit() == creditLimit
     * @throws IllegalAmountException(creditLimit,this)
     *         This bank account cannot have the given credit
     *         limit as its credit limit.
     *       | ! canHaveAsCreditLimit(creditLimit)
     */
    @Raw
    public void setCreditLimit(long creditLimit) throws IllegalAmountException {
        if (!canHaveAsCreditLimit(creditLimit))
            throw new IllegalAmountException(creditLimit, this);
        this.creditLimit = creditLimit;
    }

    /**
     * Variable registering the credit limit for this bank
     * account.
     */
    private long creditLimit;

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
     * Check whether the given balance limit is a valid balance
     * limit for all bank accounts.
     *
     * @param   balanceLimit
     *          The balance limit to check.
     * @return  True if and only if the given balance limit is
     *          strict positive.
     *          | result == (balanceLimit > 0)
     */
    public static boolean isValidBalanceLimit(long balanceLimit) {
        return balanceLimit > 0;
    }

    /**
     * Variable registering the balance limit that applies to all
     * bank accounts.
     */
    private static final long balanceLimit = Long.MAX_VALUE;

    /**
     * Return the balance of this bank account.
     *   The balance of a bank account expresses the amount of
     *   money available on that account.
     */
    @Basic
    @Raw
    public long getBalance() {
        return this.balance;
    }

    /**
     * Check whether the given balance is a possible balance for
     * any bank account.
     *
     * @param  balance
     *         The balance to check.
     * @return True if and only if the given balance is not above
     *         the balance limit that applies to all bank
     *         accounts.
     *       | result == (balance <= getBalanceLimit())
     */
    public static boolean isPossibleBalance(long balance) {
        return balance <= getBalanceLimit();
    }

    /**
     * Check whether this bank account can have the given
     * balance as its balance.
     *
     * @param  balance
     *         The balance to check.
     * @return True if and only if the given balance is a
     *         possible balance for any bank account, and if the
     *         given balance matches with the credit limit of
     *         this bank account.
     *       | result ==
     *       |   ( isPossibleBalance(balance)
     *       |  && matchesBalanceCreditLimit
     *       |         (balance,getCreditLimit())) )
     */
    @Raw
    public boolean canHaveAsBalance(long balance) {
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
     * @return True if and only if the given balance exceeds the
     *         given credit limit.
     *       | result == (balance > creditLimit)
     */
    public static boolean matchesBalanceCreditLimit(long balance,
                                                    long creditLimit) {
        return balance > creditLimit;
    }

    /**
     * Return a boolean reflecting whether this bank account has
     * a higher balance than the given amount of money.
     *
     * @param  amount
     *         The amount of money to compare with.
     * @return True if and only if the balance of this bank
     *         account is greater than the given amount.
     *       | result == (this.getBalance() > amount)
     */
    public boolean hasHigherBalanceThan(long amount) {
        return getBalance() > amount;
    }

    /**
     * Return a boolean reflecting whether this bank account has
     * a higher balance than the other bank account.
     *
     * @param  other
     *         The bank account to compare with.
     * @return True if and only if this bank account has a higher
     *         higher balance than the balance of the other bank
     *         account.
     *       | result ==
     *       |   this.hasHigherBalanceThan(other.getBalance())
     * @throws IllegalArgumentException
     *         The other bank account is not effective.
     *       | other == null
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
     * @return True if and only if the given amount is positive,
     *         if this bank account can have its current balance
     *         incremented with the given amount as its balance,
     *         and if that sum does not cause an overflow.
     *       | result ==
     *       |   (amount > 0) &&
     *       |   canHaveAsBalance(getBalance() + amount) &&
     *       |   (getBalance() <= Long.MAX_VALUE - amount)
     */
    public boolean canAcceptForDeposit(long amount) {
        return (amount > 0) && canHaveAsBalance(getBalance() + amount)
                && (getBalance() <= Long.MAX_VALUE - amount);
    }

    /**
     * Deposit the given amount of money to this bank account.
     *
     * @param  amount
     *         The amount of money to be deposited.
     * @post   The new balance of this bank account is equal to
     *         the old balance of this bank account incremented
     *         with the given amount of money.
     *       | new.getBalance() == this.getBalance() + amount
     * @throws IllegalAmountException(amount,this)
     *         This bank account cannot accept the given amount
     *         for deposit.
     *       | ! canAcceptForDeposit(amount)
     */
    public void deposit(long amount) throws IllegalAmountException {
        if (!canAcceptForDeposit(amount))
            throw new IllegalAmountException(amount, this);
        setBalance(getBalance() + amount);
    }

    /**
     * Return a boolean reflecting whether this bank account can
     * accept the given amount for withdraw.
     *
     * @param  amount
     *         The amount to be checked.
     * @return True if and only if the given amount is positive,
     *         if this bank account is not blocked, if this bank
     *         account can have its current balance decremented
     *         with the given amount as its balance, and if that
     *         subtraction does not cause an overflow.
     *       | result ==
     *       |   (amount > 0) &&
     *       |   (! isBlocked()) &&
     *       |   canHaveAsBalance(getBalance() - amount) &&
     *       |   (getBalance() >= Long.MIN_VALUE + amount)
     */
    public boolean canAcceptForWithdraw(long amount) {
        return (amount > 0) && (!isBlocked())
                && canHaveAsBalance(getBalance() - amount)
                && (getBalance() >= Long.MIN_VALUE + amount);
    }

    /**
     * Withdraw the given amount of money from this bank account.
     *
     * @param  amount
     *         The amount of money to be withdrawn.
     * @post   The new balance of this bank account is equal to
     *         the old balance of this bank account decremented
     *         with the given amount of money.
     *       | new.getBalance() == this.getBalance() Рamount
     * @throws IllegalAmountException(-amount,this)
     *         This bank account cannot accept the given amount
     *         for withdraw.
     *       | ! canAcceptForWithdraw(amount)
     */
    public void withdraw(long amount) throws IllegalAmountException {
        if (!canAcceptForWithdraw(amount))
            throw new IllegalAmountException(-amount, this);
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
    public void transferTo(long amount, BankAccount destination)
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

    // ALTERNATIVE IMPLEMENTATION
    //    public void transferTo(long amount, BankAccount destination)
    //            throws IllegalArgumentException, IllegalAmountException {
    //        try {
    //            if ((destination == null) || (destination == this))
    //                throw new IllegalArgumentException("Illegal destination!");
    //            withdraw(amount);
    //            destination.deposit(amount);
    //        }
    //        catch (IllegalAmountException exc) {
    //            if (exc.getStackTrace()[0].getMethodName().equals("deposit"))
    //                deposit(amount);
    //            throw exc;
    //        }
    //        catch (Error exc) {
    //            if (exc.getStackTrace()[0].getMethodName().equals("deposit"))
    //                deposit(amount);
    //            throw exc;
    //        }
    //    }

    // LESS COMPLETE IMPLEMENTATION
    //    public void transferTo(long amount, BankAccount destination)
    //          throws IllegalArgumentException, IllegalAmountException {
    //      try {
    //        if ( (destination == null) || (destination == this) )
    //          throw new IllegalArgumentException("Illegal destination!");
    //        withdraw(amount);
    //        destination.deposit(amount);
    //      }
    //      catch (IllegalAmountException exc) {
    //        if (exc.getAccount() == destination)
    //          deposit(amount);
    //        throw exc;
    //      }
    //    }

    /**
     * Set the balance for this bank account to the given
     * balance.
     *
     * @param  balance
     *         The new balance for this bank account.
     * @post   The new balance for this bank account is equal to
     *         the given balance.
     *       | new.getBalance() == balance
     * @throws IllegalAmountException(balance,this)
     *         This bank account cannot have the given balance
     *         as its balance.
     *       | ! canHaveAsBalance(balance)
     */
    @Raw
    @Model
    private void setBalance(long balance) throws IllegalAmountException {
        if (!canHaveAsBalance(balance))
            throw new IllegalAmountException(balance, this);
        this.balance = balance;
    }

    /**
     * Variable registering the balance of this bank account.
     */
    private long balance = 0;

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
     * @param   flag
     *          The blocked state to be registered.
     * @post    The new blocked state of this bank account is set
     *          according to the given flag.
     */
    @Raw
    public void setBlocked(boolean flag) {
        this.isBlocked = flag;
    }

    /**
     * Block this bank account.
     *
     * @effect  The blocked state of this bank account is set to
     *          true.
     */
    public void block() {
        setBlocked(true);
    }

    /**
     * Unblock this bank account.
     *
     * @effect  The blocked state of this bank account is set to
     *          false.
     */
    public void unblock() {
        setBlocked(false);
    }

    /**
     * Variable registering the blocked state of this account.
     */
    private boolean isBlocked = false;

}
