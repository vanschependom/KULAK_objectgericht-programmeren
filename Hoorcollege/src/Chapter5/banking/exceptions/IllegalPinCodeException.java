package Chapter5.banking.exceptions;

import be.kuleuven.cs.som.annotate.*;
import Chapter5.banking.*;

/**
 * A class of exceptions signaling illegal pin codes
 * for bank cards.
 *   Each illegal amount exception involves the illegal pin code
 *   and the bank card.
 *
 * @version 2.0
 * @author  Eric Steegmans
 */
@SuppressWarnings("serial")
public class IllegalPinCodeException extends RuntimeException {

  /**
   * Initialize this new illegal pin code exception with given
   * pin code and given bank card.
   *
   * @param  pinCode
   *         The pin code for this new illegal pin code exception.
   * @param  card
   *         The bank card for this illegal pin code
   *         exception.
   * @post   The pin code of this new illegal pin code exception
   *         is equal to the given pin code.
   *         | new.getPinCode() == pinCode
   * @post   The bank card of this new illegal pin code
   *         exception is the same as the given bank card.
   *         | new.getBankCard() == card
   * @effect This new illegal pin code exception is further
   *         initialized as a new runtime exception involving no
   *         diagnostic message and no cause.
   *         | super()
   */
  public IllegalPinCodeException
      (int pinCode, BankCard card)
  {
    this.pinCode = pinCode;
    this.card = card;
  }

  /**
   * Return the pin code of this illegal pin code exception.
   */
  @Basic @Immutable
  public int getPinCode() {
    return pinCode;
  }

  /**
   * Variable registering the pin code of this illegal pin code
   * exception.
   */
  private final int pinCode;

  /**
   * Return the bank card of this illegal pin code exception.
   */
  @Basic @Immutable
  public BankCard geBankCard( ) {
    return card;
  }

  /**
   * Variable referencing the bank card of this illegal pin code
   * exception.
   */
  private final BankCard card;

}