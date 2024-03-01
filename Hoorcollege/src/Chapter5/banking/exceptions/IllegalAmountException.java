package Chapter5.banking.exceptions;

import be.kuleuven.cs.som.annotate.*;
import Chapter5.banking.BankAccount;
import Chapter5.banking.money.*;

/**
 * A class of exceptions signaling illegal money amounts in
 * transactions for bank accounts.
 *   Each illegal amount exception involves the illegal money amount
 *   and the bank account.
 *
 * @version 2.0
 * @author  Eric Steegmans
 */
@SuppressWarnings("serial")
public class IllegalAmountException extends RuntimeException {

  /**
   * Initialize this new illegal amount exception with given
   * amount and given bank account.
   *
   * @param  amount
   *         The amount for this new illegal amount exception.
   * @param  account
   *         The bank account for this illegal amount
   *         exception.
   * @post   The amount of this new illegal amount exception
   *         is equal to the given amount.
   *         | new.getAmount() == amount
   * @post   The bank account of this new illegal amount
   *         exception is the same as the given bank account.
   *         | new.getAccount() == account
   * @effect This new illegal amount exception is further
   *         initialized as a new runtime exception involving no
   *         diagnostic message and no cause.
   *         | super()
   */
  public IllegalAmountException
      (MoneyAmount amount, BankAccount account)
  {
    this.amount = amount;
    this.account = account;
  }

  /**
   * Return the amount of this illegal amount exception.
   */
  @Basic @Immutable
  public MoneyAmount getAmount() {
    return amount;
  }

  /**
   * Variable registering the amount of this illegal amount
   * exception.
   */
  private final MoneyAmount amount;

  /**
   * Return the bank account of this illegal amount exception.
   */
  @Basic @Immutable
  public BankAccount getAccount ( ) {
    return account;
  }

  /**
   * Variable referencing the account of this illegal amount
   * exception.
   */
  private final BankAccount account;

}