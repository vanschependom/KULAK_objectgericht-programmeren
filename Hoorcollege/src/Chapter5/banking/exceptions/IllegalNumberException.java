package Chapter5.banking.exceptions;

import be.kuleuven.cs.som.annotate.*;
import Chapter5.banking.BankAccount;

/**
 * A class of exceptions signaling illegal numbers for
 * bank accounts.
 *   Each illegal number exception involves the illegal number
 *   and the bank account.
 *
 * @version 2.0
 * @author  Eric Steegmans
 */
@SuppressWarnings("serial")
public class IllegalNumberException extends RuntimeException {

  /**
   * Initialize this new illegal number exception with given number
   * and given bank account.
   *
   * @param  number
   *         The number for this new illegal number exception.
   * @param  account
   *         The bank account for this new illegal number exception.
   * @post   The number for this new illegal number exception
   *         is equal to the given number.
   *         | new.getNumber() == number
   * @post   The bank account for this new illegal number exception
   *         is the same as the given bank account.
   *         | new.getAccount() == account
   * @effect This new illegal number exception is further
   *         initialized as a new runtime exception involving no diagnostic
   *         message and no cause.
   *         | super()
   */
  public IllegalNumberException (int number, BankAccount account) {
    this.number = number;
    this.account = account;
  }

  /**
   * Return the number of this illegal number exception.
   */
  @Basic @Immutable
  public long getNumber() {
    return number;
  }

  /**
   * Variable registering the number of this illegal number
   * exception.
   */
  private final long number;

  /**
   * Return the bank account of this illegal number exception.
   */
  @Basic @Immutable
  public BankAccount getAccount ( ) {
    return account;
  }

  /**
   * Variable referencing the bank account of this illegal
   * number exception.
   */
  private final BankAccount account;

}