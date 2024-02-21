package Chapter2;

import java.util.*;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of bank accounts involving a bank code, a number,
 * a credit limit, a balance limit, a balance, a blocking
 * facility and a series of tokens.
 *
 * @invar   The bank code that applies to all bank accounts
 *          must be a valid bank code.
 *        | isValidBankCode(getBankCode())
 * @invar   Each bank account can have its number as its
 *          number.
 *        | canHaveAsNumber(getNumber())
 * @invar   The credit limit that applies to all bank accounts
 *          must be a valid credit limit.
 *        | isValidCreditLimit(getCreditLimit())
 * @invar   The balance limit that applies to all bank accounts
 *          must be a valid balance limit.
 *        | isValidBalanceLimit(getBalanceLimit())
 * @invar   The balance of each bank account must be a valid
 *          balance for any bank account.
 *        | isValidBalance(getBalance())
 * @invar   The number of tokens of each bank account must be a
 *          valid number of tokens for any bank account.
 *        | isValidNbTokens(getNbTokens())
 * @invar   Each bank account can have each of its tokens at
 *          its index as a token at that index.
 *        | for each I in 1..getNbTokens():
 *        |   canHaveAsTokenAt(getTokenAt(I),I)
 *
 * @version 2.0
 * @author  Eric Steegmans
 */
class BankAccount {

    /**
     * Initialize this new bank account with given number, given
     * balance, given blocked state and given series of tokens.
     *
     * @param  number
     *         The number for this new bank account.
     * @param  balance
     *         The initial balance for this new bank account.
     * @param  isBlocked
     *         The initial value for the blocked state of this
     *         new bank account.
     * @param  tokens
     *         The series of tokens for this new bank account.
     * @pre    This new bank account can have the given number as
     *         its number.
     *       | canHaveAsNumber(number)
     * @pre    The given initial balance must be a valid balance
     *         for any bank account.
     *       | isValidBalance(balance)
     * @pre    The given series of tokens must be effective.
     *       | tokens != null
     * @pre    Each of the given tokens must be a valid token for
     *         any bank account.
     *       | for each token in tokens:
     *       |   isValidToken(token)
     * @pre    The given series of tokens does not contain
     *         identical tokens.
     *       | for each I in 1..tokens.length-1:
     *       |   (for each J in 0..I-1:
     *       |       (! tokens[I].equals(tokens[J]) ) )
     * @post   The new number of this new bank account is equal
     *         to the given number.
     *       | new.getNumber() == number
     * @post   The new balance of this new bank account is equal
     *         to the given initial balance.
     *       | new.getBalance() == balance
     * @post   The new blocked state of this new bank account is
     *         equal to the given flag.
     *       | new.isBlocked() == isBlocked
     * @post   The new series of tokens ascribed to this new
     *         bank account is equal to the given series of
     *         tokens.
     *       | Arrays.equals(tokens,theAccount.getTokens())
     */
    @Raw
    public BankAccount(int number, long balance, boolean isBlocked,
                       String... tokens) {
        assert canHaveAsNumber(number);
        assert (tokens != null);
        for (int i = 0; i < tokens.length; i++) {
            assert isValidToken(tokens[i]);
            for (int j = 0; j < i; j++)
                assert !tokens[i].equals(tokens[j]);
        }
        this.number = number;
        setBalance(balance);
        setBlocked(isBlocked);
        this.tokens = tokens.clone();
        allAccountNumbers.add(number);
    }

    /**
     * Initialize this new bank account to an unblocked account
     * with given number and given balance.
     *
     * @param  number
     *         The number for this new bank account.
     * @param  balance
     *         The initial balance for this new bank account.
     * @effect This new bank account is initialized with the
     *         given number as its number, the given balance as
     *         its initial balance, false as its initial blocked
     *         state and no effective tokens as its tokens.
     *       | this(number,balance,false)
     */
    @Raw
    public BankAccount(int number, long balance) {
        this(number, balance, false);
    }

    /**
     * Initialize this new bank account to an unblocked account
     * with given number and zero balance.
     *
     * @param   number
     *          The number for this new bank account.
     * @effect  This new bank account is initialized in the same
     *          way a new bank account would be initialized with
     *          the middle constructor involving the given number
     *          as its number and zero as its initial balance.
     */
    @Raw
    public BankAccount(int number) {
        this(number, 0);
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
    @Raw
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
        return (number > 0) && ( (this.getNumber() == number) || (! allAccountNumbers.contains(number)));
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
     * Return the credit limit that applies to all bank accounts.
     *   The credit limit expresses the lowest possible value for
     *   the balance of a bank account.
     */
    @Basic
    public static long getCreditLimit() {
        return creditLimit;
    }

    /**
     * Check whether the given credit limit is a valid credit
     * limit for all bank accounts.
     *
     * @param  creditLimit
     *         The credit limit to check.
     * @return True if and only if the given credit limit is not
     *         positive.
     *       | result == (creditLimit <= 0)
     */
    public static boolean isValidCreditLimit(long creditLimit) {
        return creditLimit <= 0;
    }

    /**
     * Set the credit limit that applies to all bank accounts to
     * the given limit.
     *
     * @param   creditLimit
     *          The new credit limit for all bank accounts.
     * @pre     The given credit limit may not exceed the credit
     *          limit that currently applies to all bank accounts.
     *          | creditLimit <= getCreditLimit()
     * @post    The new credit limit that applies to all bank
     *          accounts is equal to the given credit limit.
     *          | new.getCreditLimit() == limit
     */
    public static void setCreditLimit(long creditLimit) {
        assert (creditLimit <= getCreditLimit()) : "Precondition: Acceptable credit limit";
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
     * Check whether the given balance limit is a valid balance
     * limit for all bank accounts.
     *
     * @param  balanceLimit
     *         The balance limit to check.
     * @return True if and only if the given balance limit is
     *         strict positive.
     *       | result == (balanceLimit > 0)
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
     * Check whether the given balance is a valid balance for any
     * bank account.
     *
     * @param  balance
     *         The balance to check.
     * @return True if and only if the given balance is not below
     *         the credit limit and not above the balance limit.
     *        | result ==
     *        |    ( (balance >= getCreditLimit())
     *        |   && (balance <= getBalanceLimit()) )
     */
    public static boolean isValidBalance(long balance) {
        return ((balance >= getCreditLimit()) && (balance <= getBalanceLimit()));
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
     * @pre    The other bank account is effective.
     *       | other != null
     * @return True if and only if this bank account has a higher
     *         balance than the balance of the other bank
     *         account.
     *       | result ==
     *       |   this.hasHigherBalanceThan(other.getBalance())
     */
    public boolean hasHigherBalanceThan(BankAccount other) {
        assert (other != null) : "Precondition: Effective bank account to compare with";
        return this.hasHigherBalanceThan(other.getBalance());
    }

    /**
     * Return a boolean reflecting whether this bank account can
     * accept the given amount for deposit.
     *
     * @param  amount
     *         The amount to be checked.
     * @return True if and only if the given amount is positive,
     *         if the balance of this bank account incremented
     *         with the given amount is a valid balance for any
     *         bank account, and if that sum does not cause an
     *         overflow.
     *       | result ==
     *       |    ( (amount > 0)
     *       |   && isValidBalance(getBalance() + amount)
     *       |   && (getBalance() <= Long.MAX_VALUE - amount) )
     */
    public boolean canAcceptForDeposit(long amount) {
        return ((amount > 0) && isValidBalance(getBalance() + amount) && (getBalance() <= Long.MAX_VALUE
                - amount));
    }

    /**
     * Deposit the given amount of money to this bank account.
     *
     * @param  amount
     *         The amount of money to be deposited.
     * @pre    This bank account can accept the given amount for
     *         deposit.
     *       | canAcceptForDeposit(amount)
     * @post   The new balance of this bank account is equal to
     *         the old balance of this bank account incremented
     *         with the given amount of money.
     *       | new.getBalance() == this.getBalance() + amount
     */
    public void deposit(long amount) {
        assert canAcceptForDeposit(amount) : "Precondition: Acceptable amount for deposit";
        setBalance(getBalance() + amount);
    }

    /**
     * Return a boolean reflecting whether this bank account can
     * accept the given amount for withdraw.
     *
     * @param  amount
     *         The amount to be checked.
     * @return True if and only if the given amount is positive,
     *         if this bank account is not blocked, if the
     *         balance of this bank account decremented with the
     *         given amount is a valid balance for any bank
     *         account, and if that subtraction does not cause an
     *         overflow.
     *       | result ==
     *       |    ( (amount > 0)
     *       |   && (! isBlocked())
     *       |   && isValidBalance(getBalance() - amount)
     *       |   && (getBalance() >= Long.MIN_VALUE + amount) )
     */
    public boolean canAcceptForWithdraw(long amount) {
        return ((amount > 0) && (!isBlocked())
                && isValidBalance(getBalance() - amount) && (getBalance() >= Long.MAX_VALUE
                + amount));
    }

    /**
     * Withdraw the given amount of money from this bank account.
     *
     * @param  amount
     *         The amount of money to be withdrawn.
     * @pre    This bank account can accept the given amount for
     *         withdraw.
     *       | canAcceptForWithdraw(amount)
     * @post   The new balance of this bank account is equal to
     *         the old balance of this bank account decremented
     *         with the given amount of money.
     *       | new.getBalance() == this.getBalance() - amount
     */
    public void withdraw(long amount) {
        assert canAcceptForWithdraw(amount) : "Precondition: Acceptable amount for withdraw";
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
     * @pre    The given destination account must be effective
     *         and different from this bank account.
     *       | (destination != null) && (destination != this)
     * @effect The given amount of money is withdrawn from this
     *         bank account, and deposited to the given
     *         destination account.
     *       |  ( this.withdraw(amount)
     *       | && destination.deposit(amount) )
     */
    public void transferTo(long amount, BankAccount destination) {
        assert (destination != null) : "Precondition: Effective destination account";
        assert (destination != this) : "Precondition: Destination different from source";
        this.withdraw(amount);
        destination.deposit(amount);
    }

    /**
     * Set the balance of this bank account to the given balance.
     *
     * @param  balance
     *         The new balance for this bank account.
     * @pre    The given balance must be a valid balance for any
     *         bank account.
     *       | isValidBalance(balance)
     * @post   The new balance of this bank account is equal to
     *         the given balance.
     *       | new.getBalance() == balance
     */
    @Raw
    private void setBalance(long balance) {
        assert (isValidBalance(balance)) : "Precondition: Acceptable balance";
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
    @Raw
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

    /**
     * Return the number of tokens ascribed to this bank account.
     */
    @Basic
    @Raw
    @Immutable
    public int getNbTokens() {
        if (tokens != null)
            return tokens.length;
        return 0;
    }

    /**
     * Check whether the given number of tokens is a valid number
     * of tokens for any bank account.
     *
     * @param  nbTokens
     *         The number of tokens to check.
     * @return True if and only if the given number of tokens is
     *         zero or positive.
     *       | result == (nbTokens >= 0)
     */
    public static boolean isValidNbTokens(int nbTokens) {
        return (nbTokens >= 0);
    }

    /**
     * Return the token ascribed to this bank account at the
     * given index.
     *   Tokens serve to identify end users in electronic banking
     *   applications.
     *
     * @param  index
     *         The index of the token to return.
     * @pre    The given index must be positive and may not
     *         exceed the number of tokens ascribed to this bank
     *         account.
     *       | (index > 0) && (index <= getNbTokens())
     */
    @Basic
    @Raw
    public String getTokenAt(int index) {
        assert (index > 0) && (index <= getNbTokens());
        return tokens[index - 1];
    }

    /**
     * Constant reflecting the length of each token.
     *   The length of a token is the number of symbols it
     *   consists of.
     *
     * @return Each token counts 6 symbols.
     *       | result == 6
     */
    public final static int TOKEN_LENGTH = 6;

    /**
     * Check whether the given token is a valid token for any
     * bank account.
     *
     * @param  token
     *         The token to check.
     * @return False if the given token is not effective.
     *       | if (token == null)
     *       |   then result == false
     *         Otherwise, false if the given token does not have
     *         the proper length.
     *       | else if (token.length != TOKEN_LENGTH)
     *       |   then result == false
     *         Otherwise, true if the given token only consists
     *         of letters (upper case and lower case) and digits.
     *       | else if (token.matches("[A-Za-z0-9]+"))
     *       |   result == true
     */
    public static boolean isValidToken(String token) {
        if (token == null)
            return false;
        if (token.length() != TOKEN_LENGTH)
            return false;
        return token.matches("[A-Za-z0-9]+");
    }

    /**
     * Check whether this bank account can have the given token
     * as a token at the given index.
     *
     * @param  token
     *         The token to check.
     * @param  index
     *         The index for the token to check.
     * @return False if the given token is not a valid token for
     *         any bank account.
     *       | if (! isValidToken(token))
     *       |   then result == false
     *         Otherwise, false if the given index is negative
     *         or zero, or if that index exceeds the number of
     *         tokens ascribed to this bank account.
     *       | else if ( (index <= 0)
     *       |        || (index > getNbTokens())) )
     *       |   then result == false
     *         Otherwise, true if and only if this bank account
     *         does not have an identical token at another index.
     *       | else result ==
     *       |   (for each I in 1..getNbTokens():
     *       |      ( (I == index)
     *       |     || (! token.equals(getTokenAt(I)) ) ) )
     */
    @Raw
    public boolean canHaveAsTokenAt(String token, int index) {
        if (!isValidToken(token))
            return false;
        if ((index <= 0) || (index > getNbTokens()))
            return false;
        for (int I = 1; I <= getNbTokens(); I++)
            if ((I != index) && token.equals(getTokenAt(I)))
                return false;
        return true;
    }

    /**
     * Set the token ascribed to this bank account at the given
     * index to the given token.
     *
     * @param  token
     *         The token to register.
     * @param  index
     *         The index for the token to register.
     * @pre    This bank account can have the given token as a
     *         token at the given index.
     *       | canHaveAsTokenAt(token,index)
     * @post   This bank account has the given token as its token
     *         at the given index.
     *       | new.getTokenAt(index) == token
     */
    @Raw
    public void setTokenAt(String token, int index) {
        assert canHaveAsTokenAt(token, index);
        this.tokens[index - 1] = token;
    }

    /**
     * Return all the tokens ascribed to this bank account.
     *
     * @return The length of the resulting array is equal to the
     *         number of tokens ascribed to this bank account.
     *       | result.length == getNbTokens()
     * @return Each element in the resulting array is equal to
     *         the token ascribed to this bank account at the
     *         corresponding index.
     *       | for each I in 0..result.length-1:
     *       |   result[I].equals(getTokenAt(I+1))
     */
    public String[] getTokens() {
        return tokens.clone();
    }

    /**
     * Variable referencing an array assembling all the tokens
     * ascribed to this bank account.
     */
    private final String tokens[];

}