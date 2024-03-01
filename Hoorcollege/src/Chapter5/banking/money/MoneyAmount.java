package Chapter5.banking.money;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import be.kuleuven.cs.som.annotate.Value;

/**
 * A class of money amounts involving a numeral and a currency.
 *
 * @invar   The numeral of each money amount must be a valid
 *          numeral.
 *          | isValidNumeral(getNumeral())
 * @invar   The currency of each money amount must be a valid
 *          currency.
 *          | isValidCurrency(getCurrency())
 *
 * @version 2.0
 * @author  Eric Steegmans
 */
@Value
public class MoneyAmount implements Comparable<MoneyAmount> {

  /**
   * Initialize this new money amount with given numeral and
   * given currency.
   *
   * @param   numeral
   *          The numeral for this new money amount.
   * @param   currency
   *          The currency for this new money amount.
   * @post    The numeral of this new money amount is equal to
   *          the given numeral rounded (using half down) to a
   *          decimal number with 2 fractional digits.
   *          | let
   *          |   roundedNumeral =
   *          |     numeral.round(getScale2ContextFor(numeral))
   *          | in
   *          |   new.getNumeral().equals(roundedNumeral)
   * @post    The currency for this new money amount is the
   *          same  as the given currency.
   *          | new.getCurrency() == currency
   * @throws  IllegalArgumentException
   *          The given numeral is not effective.
   *          | numeral == null
   * @throws  IllegalArgumentException
   *          The given currency is not a valid currency for
   *          any money amount.
   *          | ! isValidCurrency()
   */
  @Raw
  public MoneyAmount(BigDecimal numeral, Currency currency)
      throws IllegalArgumentException
  {
    if (numeral == null)
      throw new IllegalArgumentException(
                    "Non-effective numeral");
    if (! isValidCurrency(currency))
      throw new IllegalArgumentException("Invalid currency");
    if (numeral.scale() != 2)
      numeral = numeral.round(getScale2ContextFor(numeral));
    this.numeral = numeral;
    this.currency = currency;
  }

  /**
   * Initialize this new money amount with given numeral and
   * currency EUR.
   *
   * @param   numeral
   *          The numeral for this new money amount.
   * @effect  This new money amount is inialized in the same
   *          way a new money amount would be initialized with
   *          the most extended constructor involving the given
   *          numeral and the currency EUR.
   *          | this(numeral,Currency.EUR)
   */
  @Raw
  public MoneyAmount(BigDecimal numeral)
      throws IllegalArgumentException
  {
    this(numeral,Currency.EUR);
  }

  /**
   * Variable referencing a money amount of 0.00 EUR;
   * 
   * @return  The money amount EUR_0 is equal to a money
   *          amount initialized with numeral BigDecimal.ZERO
   *          and with currency Currency.EUR.
   *          | EUR_0.equals(new MoneyAmount
   *          |     (BigDecimal.ZERO,Currency.EUR))
   */
  public final static MoneyAmount EUR_0 =
      new MoneyAmount(BigDecimal.ZERO,Currency.EUR);

  /**
   * Variable referencing a money amount of 1.00 EUR;
   * 
   * @return  The money amount EUR_1 is equal to a money
   *          amount initialized with numeral BigDecimal.ONE
   *          and with currency Currency.EUR.
   *          | EUR_1.equals(new MoneyAmount
   *          |     (BigDecimal.ONE,Currency.EUR))
   */
  public final static MoneyAmount EUR_1 =
      new MoneyAmount(BigDecimal.ONE,Currency.EUR);

  /**
   * Return the numeral of this money amount.
   */
  @Basic @Raw @Immutable
  public BigDecimal getNumeral() {
    return this.numeral;
  }

  /**
   * Check whether the given numeral is a valid numeral for
   * any money amount.
   *
   * @param   numeral
   *          The numeral to check.
   * @return  True if and only if the given numeral is
   *          effective and if that numeral has a scale of 2.
   *          | result ==
   *          |   (numeral != null) &&
   *          |   (numeral.scale() == 2)
   */
  public static boolean isValidNumeral(BigDecimal numeral) {
    return (numeral != null) && (numeral.scale() == 2);
  }

  /**
   * Variable referencing the numeral of this money amount.
   */
  private final BigDecimal numeral;

  /**
   * Return the currency of this money amount.
   */
  @Basic @Raw @Immutable
  public Currency getCurrency() {
    return this.currency;
  }

  /**
   * Check whether the given currency is a valid currency for
   * any money amount.
   *
   * @param   currency
   *          The currency to check.
   * @return  True if and only if the given currency is
   *          effective.
   *          | result == (currency != null)
   */
  public static boolean isValidCurrency(Currency currency) {
    return currency != null;
  }

  /**
   * Return a money amount that has the same value as this
   * money amount expressed in the given currency.
   *
   * @param   currency
   *          The currency to convert to.
   * @return  The resulting money amount is effective.
   *          | result != null
   * @return  The resulting money amount has the given currency
   *          as its currency.
   *          | result.getCurrency() == currency
   * @return  The numeral of the resulting money amount is
   *          equal to the numeral of this money amount
   *          multiplied with the exchange rate from the
   *          currency of this money amount to the given
   *          currency rounded half-down to a scale of 2.
   *          | let
   *          |   exhangeRate =
   *          |     this.getCurrency().toCurrency(currency),
   *          |   numeralInCurrency =
   *          |     this.getNumeral().multiply(exchangeRate)
   *          | in
   *          |   result.getNumeral().equals
   *          |     (numeralInCurrency.round(
   *          |        getScale2ContextFor(numeralInCurrency))
   * @throws  IllegalArgumentException
   *          The given currency is not effective.
   *          | currency == null
   */
  public MoneyAmount toCurrency(Currency currency) 
      throws IllegalArgumentException
  {
    if (currency == null)
      throw new IllegalArgumentException(
                    "Non-effective currency");
    if (this.getCurrency() == currency)
      return this;
    BigDecimal exchangeRate = 
        this.getCurrency().toCurrency(currency);
    BigDecimal numeralInCurrency = 
        getNumeral().multiply(exchangeRate);
    numeralInCurrency =
      numeralInCurrency.round(
        getScale2ContextFor(numeralInCurrency));
    return new MoneyAmount(numeralInCurrency,currency);
  }

  /**
   * Variable referencing the currency of this money amount.
   */
  private final Currency currency;

  /**
   * Compute the negation of this money amount.
   *
   * @return  The resulting money amount is effective.
   *          | result != null
   * @return  The resulting money amount has the same currency
   *          as this money amount.
   *          | result.getCurrency() == this.getCurrency()
   * @return  The numeral of the resulting money amount is
   *          equal to the negation of the numeral of this
   *          money amount.
   *          | result.getNumeral().equals(
   *          |     this.getNumeral().negate())
   */
  public MoneyAmount negate() {
    return
      new MoneyAmount(this.getNumeral().negate(),
                      getCurrency());
  }

  /**
   * Compute the product of this money amount with the
   * given factor.
   *
   * @param   factor
   *          The factor to multiply with.
   * @return  The resulting money amount is effective.
   *          | result != null
   * @return  The resulting money amount has the same currency
   *          as this money amount.
   *          | result.getCurrency() == this.getCurrency()
   * @return  The numeral of the resulting money amount is
   *          equal to the product of the numeral of this
   *          money amount and the given factor.
   *          | result.getNumeral().equals(
   *          |     this.getNumeral().multiply(
   *          |         new BigDecimal(factor)))
   */
  public MoneyAmount times(long factor) {
    BigDecimal factorDec = new BigDecimal(factor);
    return
      new MoneyAmount(this.getNumeral().multiply(factorDec),
                      getCurrency());
  }

  /**
   * Compute the sum of this money amount and the other money
   * amount.
   *
   * @param   other
   *          The other money amount to add.
   * @return  The resulting money amount is effective.
   *          | result != null
   * @return  The resulting money amount has the same currency
   *          as this money amount.
   *          | result.getCurrency() == this.getCurrency()
   * @return  If both money amounts use the same currency, the
   *          numeral of the resulting money amount is equal to
   *          the sum of the numeral of both money amounts.
   *          | if (this.getCurrency() == other.getCurrency())
   *          |   then result.getNumeral().equals
   *          |     this.getNumeral().add(other.getNumeral())
   * @return  If both money amounts use different currencies,
   *          the resulting money amount is equal to the sum of
   *          this money amount and the other money amount
   *          expressed in the currency of this money amount.
   *          | if (this.getCurrency() != other.getCurrency())
   *          |   then result.equals(
   *          |     this.add(other.toCurrency(getCurrency())))
   * @throws  IllegalArgumentException
   *          The other money amount is not effective.
   *          | other == null
   */
  public MoneyAmount add(MoneyAmount other) 
      throws IllegalArgumentException
  {
    if (other == null)
      throw new IllegalArgumentException(
                    "Non-effective money amount");
    if (getCurrency() == other.getCurrency())
      return
        new MoneyAmount(getNumeral().add(other.getNumeral()),
                        getCurrency());
    return add(other.toCurrency(getCurrency()));
  }

  /**
   * Compute the subtraction of the other money amount from
   * this money amount.
   *
   * @param   other
   *          The money amount to subtract.
   * @return  The resulting money amount is equal to the sum of
   *          this money amount and the negation of the other
   *          money amount.
   *          | result.equals(this.add(other.negate())
   * @throws  IllegalArgumentException
   *          The other money amount is not effective.
   *          | other == null
   */
  public MoneyAmount subtract(MoneyAmount other) 
      throws IllegalArgumentException
  {
    if (other == null)
      throw new IllegalArgumentException(
                    "Non-effective money amount");
    if (getCurrency() == other.getCurrency())
      return
        new MoneyAmount(
                getNumeral().subtract(other.getNumeral()),
                getCurrency());
    return subtract(other.toCurrency(getCurrency()));
  }

  /**
   * Compare this money amount with the other money amount.
   *
   * @param   other
   *          The other money amount to compare with.
   * @return  The result is equal to the comparison of the
   *          numeral of this money amount with the numeral of
   *          the other money amount.
   *          | result ==
   *          |     getNumeral().compareTo(other.getNumeral())
   * @throws  ClassCastException
   *          The other money amount is not effective or this
   *          money amount and the given money amount use
   *          different currencies.
   *          | (other == null) ||
   *          | (this.getCurrency() != other.getCurrency())
   */
  @Override
  public int compareTo(MoneyAmount other) 
      throws ClassCastException
  {
    if (other == null)
      throw new ClassCastException(
                    "Non-effective money amount");
    if (getCurrency() != other.getCurrency())
      throw new ClassCastException(
                    "Incompatable money amounts");
    return getNumeral().compareTo(other.getNumeral());
  }

  /**
   * Return the signum of this money amount.
   *
   * @return  The signum of the numeral of this money amount.
   *          | result == getNumeral().signum()
   */
  public int signum() {
    return getNumeral().signum();
  }

  /**
   * Check whether this money amount has the same value as the
   * other money amount.
   *
   * @param   other
   *          The other money amount to compare with.
   * @return  True if and only if this money amount is equal
   *          to the other money amount expressed in the
   *          currency of this money amount.
   *          | result ==
   *          |   this.equals(other.toCurrency(getCurrency()))
   * @throws  IllegalArgumentException
   *          The other money amount is not effective.
   *          | other == null
   */
  public boolean hasSameValueAs(MoneyAmount other) 
      throws ClassCastException
  {
    if (other == null)
      throw new IllegalArgumentException(
                    "Non-effective money amount");
    return this.equals(other.toCurrency(this.getCurrency()));
  }

  /**
   * Check whether this money amount is equal to the given
   * object.
   *
   * @return  True if and only if the given object is
   *          effective, and if this money amount and the given
   *          object belong to the same class, and if this
   *          money amount and the other object interpreted
   *          as a money amount have equal numerals and equal
   *          currencies.
   *          | result ==
   *          |   (other != null) &&
   *          |   (this.getClass() == other.getClass()) &&
   *          |   (this.getNumeral().equals(
   *          |       ((MoneyAmount other).getNumeral())) &&
   *          |   (this.getCurrency() ==
   *          |       ((MoneyAmount other).getCurrency()) &&
   */
  @Override
  public boolean equals(Object other) {
    if (other == null)
      return false;
    if (this.getClass() != other.getClass())
      return false;
    MoneyAmount otherAmount = (MoneyAmount) other;
    return
      this.getNumeral().equals(otherAmount.getNumeral()) &&
      (this.getCurrency() == otherAmount.getCurrency());
  }

  /**
   * Return the hash code for this money amount.
   *
   */
  @Override
  public int hashCode() {
    return getNumeral().hashCode() + getCurrency().hashCode();
  }

  /**
   * Return a textual representation of this money amount.
   *
   * @return  A string consisting of the textual representation
   *          of the numeral of this money amount, followed by
   *          the textual representation of its currency,
   *          separated by a space and enclosed in square
   *          brackets.
   *          | result ==
   *          |   "[" + getNumeral().toString() + " " +
   *          |       getCurrency().toString() + "]"
   */
  @Override
  public String toString() {
    return "[" + getNumeral().toString() + " " +
               getCurrency().toString() + "]";
  }

  /**
   * Return a mathematical context to round the given big
   * decimal to 2 fractional digits.
   * 
   * @param   value
   *          The value to compute a mathematical context for.
   * @pre     The given value must be effective.
   *          | value != null
   * @return  The resulting mathematical context is effective.
   *          | result != null
   * @return  The precision of the resulting mathematical
   *          context is equal to the precision of the given
   *          value diminished with its scale and incremented
   *          by 2.
   *          | result.getPrecision() ==
   *          |   value.precision()-value.scale()+2
   * @return  The resulting mathematical context uses HALF_DOWN
   *          as its rounding mode.      
   *          | result.getRoundingMode() ==
   *          |   RoundingMode.HALF_DOWN
   */
  @Model
  static MathContext getScale2ContextFor(BigDecimal value) {
    assert value != null;
    return new MathContext(value.precision()-value.scale()+2,
                           RoundingMode.HALF_DOWN);
  }

}