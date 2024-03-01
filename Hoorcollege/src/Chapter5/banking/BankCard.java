package Chapter5.banking;

import be.kuleuven.cs.som.annotate.*;
import Chapter5.banking.exceptions.*;
import Chapter5.banking.money.*;

/**
 * A class for dealing with bank cards involving a pin code and
 * a bank account to which they are attached.
 *
 * @invar   Each bank card must have exactly one valid pin
 *          code.
 *        | for one code in int:
 *        |   (isValidPinCode(code) && hasAsPinCode(code))
 * @invar   Each bank card must have a proper bank account to
 *          which it is attached.
 *        | hasProperAccount()
 *
 * @version 2.0
 * @author  Eric Steegmans
 */
public class BankCard {

	/**
	 * Initialize this new bank card with given pin code and
	 * attached to the given bank account.
	 *
	 * @param  account
	 *         The bank account to which this new bank card must
	 *         be attached.
	 * @param  code
	 *         The pin code for this new bank card.
	 * @effect This new bank card is transferred to the given
	 *         bank account.
	 *       | transferTo(account)
	 * @effect The given pin code is set as the pin code for this
	 *         new bank card.
	 *       | setPinCode(code)
	 */
	@Raw
	public BankCard(BankAccount account, int code)
			throws IllegalAccountException, IllegalPinCodeException {
		transferTo(account);
		setPinCode(code);
	}

	/**
	 * Terminate this bank card.
	 *
	 * @post  This bank card is terminated.
	 *      | new.isTerminated()
	 * @post  This bank card no longer references a bank account
	 *        as the account to which it is attached.
	 *      | new.getAccount() == null
	 * @post  If this bank card was not already terminated, the
	 *        bank account to which this bank card was attached
	 *        no longer has a bank card attached to it.
	 *      | if (! isTerminated())
	 *      |   then (! (new getAccount()).hasBankCard() )
	 */
	public void terminate() {
		if (!isTerminated()) {
			BankAccount formerAccount = getAccount();
			this.isTerminated = true;
			setAccount(null);
			formerAccount.setBankCard(null);
		}
	}

	/**
	 * Check whether this bank card is terminated.
	 */
	@Basic
	@Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}

	/**
	 * Variable registering whether this bank card is
	 * terminated.
	 */
	private boolean isTerminated;

	/**
	 * Return the bank account to which this bank card is
	 * attached.
	 */
	@Basic
	@Raw
	public BankAccount getAccount() {
		return account;
	}

	/**
	 * Check whether this bank card can have the given bank
	 * account as its bank account.
	 *
	 * @param  account
	 *         The bank account to check.
	 * @return If this bank card is terminated, true if and only
	 *         if the given bank account is not effective.
	 *       | if (isTerminated())
	 *       |   then result == (account == null)
	 *         Otherwise, true if and only if the given bank
	 *         account is effective, and not yet terminated.
	 *       | else result ==
	 *       |   ( (account != null)
	 *       |  && (! account.isTerminated()) )
	 */
	@Raw
	public boolean canHaveAsAccount(@Raw BankAccount account) {
		if (isTerminated())
			return account == null;
		else
			return ((account != null) && (!account.isTerminated()));
	}

	/**
	 * Check whether this bank card has a proper bank account to
	 * which it is attached.
	 *
	 * @return True if and only if this bank card can have the
	 *         bank account to which it is attached as its bank
	 *         account, and either this bank card is not attached
	 *         to a bank account, or that bank account references
	 *         this bank card as its bank card.
	 *       | result ==
	 *       |   ( canHaveAsAccount(getAccount())
	 *       |  && ( (getAccount() == null)
	 *       |    || (getAccount().getBankCard() == this) ) )
	 */
	@Raw
	public boolean hasProperAccount() {
		return (canHaveAsAccount(getAccount()) && ((getAccount() == null) || (getAccount()
				.getBankCard() == this)));
	}

	/**
	 * Transfer this bank card to the given bank account.
	 *
	 * @param  account
	 *         The bank account to which this bank card must be
	 *         attached.
	 * @post   This bank card is attached to the given bank
	 *         account.
	 *       | new.getAccount() == account
	 * @post   The given bank account has this bank card as its
	 *         bank card.
	 *       | (new account).getBankCard() == this
	 * @post   If this bank card was attached to some other bank
	 *         account, that account no longer references this
	 *         bank card as the bank card attached to it.
	 *       | if ( (getAccount() != null)
	 *       |   && (getAccount() != account) )
	 *       |   then (! (new getAccount()).hasBankCard() )
	 * @throws IllegalStateException
	 *         This bank card is already terminated.
	 *       | this.isTerminated()
	 * @throws IllegalAccountException(account,this)
	 *         This bank card cannot have the given account as
	 *         the bank account to be attached to, or the given
	 *         account already has another bank card attached to
	 *         it.
	 *        |  ( (! canHaveAsAccount(account))
	 *        | || ( account.hasBankCard()
	 *        |   && (account.getBankCard() != this) ) )
	 */
	@Raw
	public void transferTo(BankAccount account) throws IllegalStateException,
			IllegalAccountException {
		if (this.isTerminated())
			throw new IllegalStateException("Terminated card!");
		if (!this.canHaveAsAccount(account))
			throw new IllegalAccountException(account, this);
		if (account.hasBankCard() && (account.getBankCard() != this))
			throw new IllegalAccountException(account, this);
		if (this.getAccount() != null)
			this.getAccount().setBankCard(null);
		setAccount(account);
		account.setBankCard(this);
	}

	/**
	 * Set the bank account to which this bank card is attached
	 * to the given bank account.
	 *
	 * @param  account
	 *         The bank account to which this bank card must be
	 *         attached.
	 * @post   This bank card is attached to the given bank
	 *         account.
	 *       | new.getAccount() == account
	 * @throws IllegalAccountException(account,this)
	 *         This bank card cannot have the given account as
	 *         the bank account to be attached to.
	 *       | ! canHaveAsAccount(account)
	 */
	@Raw
	private void setAccount(@Raw BankAccount account)
			throws IllegalAccountException {
		if (!canHaveAsAccount(account))
			throw new IllegalAccountException(account, this);
		this.account = account;
	}

	/**
	 * Variable referencing the bank account to which this bank
	 * card is attached.
	 */
	private BankAccount account;

	/**
	 * Check whether this bank card has the given code as its
	 * pin code.
	 *
	 * @param  code
	 *         The code to verify.
	 */
	@Basic
	@Raw
	public boolean hasAsPinCode(int code) {
		return getPinCode() == code;
	}

	/**
	 * Check whether the given code is a valid pin code for any
	 * bank card.
	 *
	 * @param  code
	 *         The code to check.
	 * @return False if the given code is negative or if it
	 *         exceeds 9999.
	 *       | if ( (code < 0) || (code > 9999) )
	 *       |   then result == false
	 */
	public static boolean isValidPinCode(int code) {
		return (code >= 0) && (code <= 9999);
	}

	/**
	 * Set the given code as the pin code for this bank card.
	 *
	 * @param  code
	 *         The new pin code for this bank card.
	 * @post   This bank card has the given code as its pin code.
	 *        | new.hasAsPinCode(code)
	 * @throws IllegalPinCodeException(code,this)
	 *         The given code is not a valid pin code for any
	 *         bank card.
	 *       | ! isValidPinCode(code)
	 */
	@Raw
	public void setPinCode(int code) throws IllegalPinCodeException {
		if (!isValidPinCode(code))
			throw new IllegalPinCodeException(code, this);
		pinCode = code;
	}

	/**
	 * Return the pin code registered for this bank card.
	 */
	private int getPinCode() {
		return this.pinCode;
	}

	/**
	 * Variable registering the pin code for this bank card.
	 */
	private int pinCode;

	/**
	 * Withdraw the given amount from the bank account to which
	 * this bank card is attached.
	 *
	 * @param  amount
	 *         The amount to be withdrawn.
	 * @param  code
	 *         The code to be checked against the pin code of
	 *         this bank card.
	 * @effect The given amount is withdrawn from the bank
	 *         account to which this bank card is attached.
	 *       | getAccount().withdraw(amount)
	 * @throws IllegalStateException
	 *         This bank card is already terminated.
	 *       | isTerminated()
	 * @throws IllegalPinCodeException(code)
	 *         This bank card does not have the given code as its
	 *         pin code.
	 *       | ! hasAsPinCode(code)
	 */
	public void withdraw(MoneyAmount amount, int code)
			throws IllegalPinCodeException, IllegalStateException,
			IllegalAmountException {
		if (this.isTerminated())
			throw new IllegalStateException("Terminated card!");
		if (!hasAsPinCode(code))
			throw new IllegalPinCodeException(code, this);
		getAccount().withdraw(amount);
	}

}
