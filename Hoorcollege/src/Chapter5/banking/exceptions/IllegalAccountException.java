package Chapter5.banking.exceptions;

import be.kuleuven.cs.som.annotate.*;
import Chapter5.banking.*;

/**
 * A class of exceptions signaling illegal bank accounts
 * for bank cards.
 *   Each illegal account exception involves the illegal bank account
 *   and the bank card.
 *
 * @version 2.0
 * @author  Eric Steegmans
 */
@SuppressWarnings("serial")
public class IllegalAccountException extends RuntimeException {

  /**
   * Initialize this new illegal account exception with given
   * bank account and given bank card.
   *
   * @param  account
   *         The bank account for this new illegal account exception.
   * @param  card
   *         The bank card for this illegal account
   *         exception.
   * @post   The account of this new illegal account exception
   *         is the same as the given account.
   *         | new.getAccount() == account
   * @post   The bank card of this new illegal account
   *         exception is the same as the given bank card.
   *         | new.getBankCard() == card
   * @effect This new illegal account exception is further
   *         initialized as a new runtime exception involving no
   *         diagnostic message and no cause.
   *         | super()
   */
  public IllegalAccountException
      (BankAccount account, BankCard card)
  {
    this.account = account;
    this.card = card;
  }

  /**
   * Return the account of this illegal account exception.
   */
  @Basic @Immutable
  public BankAccount getAccount() {
    return account;
  }

  /**
   * Variable referencing the account of this illegal account
   * exception.
   */
  private final BankAccount account;

  /**
   * Return the bank card of this illegal account exception.
   */
  @Basic @Immutable
  public BankCard getBankCard( ) {
    return card;
  }

  /**
   * Variable referencing the account of this illegal account
   * exception.
   */
  private final BankCard card;

}