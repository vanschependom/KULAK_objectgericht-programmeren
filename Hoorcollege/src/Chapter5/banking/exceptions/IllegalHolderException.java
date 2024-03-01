package Chapter5.banking.exceptions;

import be.kuleuven.cs.som.annotate.*;
import Chapter5.banking.BankAccount;
import Chapter5.state.Person;

/**
 * A class of exceptions signaling illegal holders for bank accounts.
 *   Each illegal holder exception involves the illegal holder
 *   and the bank account.
 *
 * @version 2.0
 * @author  Eric Steegmans
 */
@SuppressWarnings("serial")
public class IllegalHolderException extends RuntimeException {

  /**
   * Initialize this new illegal holder exception with given
   * holder and given bank account.
   *
   * @param  holder
   *         The holder for this new illegal holder exception.
   * @param  account
   *         The bank account for this illegal holder
   *         exception.
   * @post   The holder of this new illegal holder exception
   *         is the same as the given holder.
   *         | new.getholder() == holder
   * @post   The bank account of this new illegal holder
   *         exception is the same as the given bank account.
   *         | new.getAccount() == account
   * @effect This new illegal holder exception is further
   *         initialized as a new runtime exception involving no
   *         diagnostic message and no cause.
   *         | super()
   */
  public IllegalHolderException
      (Person holder, BankAccount account)
  {
    this.holder = holder;
    this.account = account;
  }

  /**
   * Return the holder of this illegal holder exception.
   */
  @Basic @Immutable
  public Person getHolder() {
    return holder;
  }

  /**
   * Variable registering the holder of this illegal holder
   * exception.
   */
  private final Person holder;

  /**
   * Return the bank account of this illegal holder exception.
   */
  @Basic @Immutable
  public BankAccount getAccount ( ) {
    return account;
  }

  /**
   * Variable referencing the account of this illegal holder
   * exception.
   */
  private final BankAccount account;

}