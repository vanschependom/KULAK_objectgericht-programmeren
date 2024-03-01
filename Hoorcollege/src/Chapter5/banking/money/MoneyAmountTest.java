package Chapter5.banking.money;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.math.*;
import org.junit.*;

public class MoneyAmountTest {

	private static MoneyAmount eur100_45, usd234_78, usd123_20;

	@BeforeAll
	public static void setUpBeforeAll() throws Exception {
		eur100_45 = new MoneyAmount(
				new BigDecimal(BigInteger.valueOf(10045), 2));
		usd234_78 = new MoneyAmount(
				new BigDecimal(BigInteger.valueOf(23478), 2), Currency.USD);
		usd123_20 = new MoneyAmount(
				new BigDecimal(BigInteger.valueOf(1232), 1), Currency.USD);
	}

	@Test
	public void extendedConstructor_LegalCaseWithoutRounding() {
		MoneyAmount theAmount = new MoneyAmount(new BigDecimal(
				BigInteger.valueOf(12345), 2), Currency.JPY);
		assertEquals(new BigDecimal(BigInteger.valueOf(12345), 2),
				theAmount.getNumeral());
		assertSame(Currency.JPY, theAmount.getCurrency());
	}

	@Test
	public void extendedConstructor_LegalCaseWithRounding() {
		MoneyAmount theAmount = new MoneyAmount(new BigDecimal(
				BigInteger.valueOf(12345), 3), Currency.USD);
		assertEquals(new BigDecimal(BigInteger.valueOf(1234), 2),
				theAmount.getNumeral());
		assertSame(Currency.USD, theAmount.getCurrency());
	}

	@Test
	public void extendedConstructor_LegalCaseWithRoundingExtra() {
		MoneyAmount theAmount = new MoneyAmount(new BigDecimal(
				BigInteger.valueOf(13579), 4), Currency.USD);
		assertEquals(new BigDecimal(BigInteger.valueOf(136), 2),
				theAmount.getNumeral());
		assertSame(Currency.USD, theAmount.getCurrency());
	}

	@Test(expected = IllegalArgumentException.class)
	public void extendedConstructor_IllegalNumeral() {
		new MoneyAmount(null, Currency.USD);
	}

	@Test(expected = IllegalArgumentException.class)
	public void extendedConstructor_IllegalCurrency() {
		new MoneyAmount(BigDecimal.TEN, null);
	}

	@Test
	public void shortConstructor_SingleCase() {
		MoneyAmount theAmount = new MoneyAmount(new BigDecimal(
				BigInteger.valueOf(-12346), 3));
		assertEquals(new BigDecimal(BigInteger.valueOf(-1235), 2),
				theAmount.getNumeral());
		assertSame(Currency.EUR, theAmount.getCurrency());
	}

	@Test
	public void EUR0_SingleCase() {
		assertEquals(new MoneyAmount(BigDecimal.ZERO), MoneyAmount.EUR_0);
	}

	@Test
	public void EUR1_SingleCase() {
		assertEquals(new MoneyAmount(BigDecimal.ONE), MoneyAmount.EUR_1);
	}

	@Test
	public void isValidNumeral_TrueCase() {
		assertTrue(MoneyAmount.isValidNumeral(new BigDecimal(BigInteger
				.valueOf(1267), 2)));
	}

	@Test
	public void isValidNumeral_NonEffectiveNumeral() {
		assertFalse(MoneyAmount.isValidNumeral(null));
	}

	@Test
	public void isValidNumeral_InvalidScale() {
		assertFalse(MoneyAmount.isValidNumeral(new BigDecimal(BigInteger
				.valueOf(1267), 1)));
	}

	@Test
	public void isValidCurrency_TrueCase() {
		assertTrue(MoneyAmount.isValidCurrency(Currency.USD));
	}

	@Test
	public void isValidNumeral_NonEffectiveCurrency() {
		assertFalse(MoneyAmount.isValidCurrency(null));
	}

	@Test
	public void toCurrency_LegalCase() {
		MoneyAmount amountInUSD = eur100_45.toCurrency(Currency.USD);
		BigDecimal expectedNumeral = new BigDecimal(BigInteger.valueOf(14252),
				2);
		assertEquals(Currency.USD, amountInUSD.getCurrency());
		assertEquals(expectedNumeral, amountInUSD.getNumeral());
	}

	@Test(expected = IllegalArgumentException.class)
	public void toCurrency_IllegalCase() {
		eur100_45.toCurrency(null);
	}

	@Test
	public void negate_SingleCase() {
		MoneyAmount negation = usd234_78.negate();
		assertSame(Currency.USD, negation.getCurrency());
		BigDecimal expectedNumeral = new BigDecimal(
				BigInteger.valueOf(-23478), 2);
		assertEquals(expectedNumeral, negation.getNumeral());
	}

	@Test
	public void times_SingleCase() {
		MoneyAmount negation = usd234_78.times(2);
		assertSame(Currency.USD, negation.getCurrency());
		BigDecimal expectedNumeral = new BigDecimal(
				BigInteger.valueOf(46956), 2);
		assertEquals(expectedNumeral, negation.getNumeral());
	}

	@Test
	public void add_SameCurrency() {
		MoneyAmount sum = usd234_78.add(usd123_20);
		assertSame(Currency.USD, sum.getCurrency());
		BigDecimal expectedNumeral = new BigDecimal(
				BigInteger.valueOf(35798), 2);
		assertEquals(expectedNumeral, sum.getNumeral());
	}

	@Test
	public void add_DifferentCurrency() {
		MoneyAmount sum = usd234_78.add(eur100_45);
		assertSame(Currency.USD, sum.getCurrency());
		BigDecimal expectedNumeral = new BigDecimal(
				BigInteger.valueOf(37730), 2);
		assertEquals(expectedNumeral, sum.getNumeral());
	}

	@Test(expected = IllegalArgumentException.class)
	public void add_NonEffectiveOperand() {
		eur100_45.add(null);
	}

	@Test
	public void subtract_LegalCase() {
		MoneyAmount difference = usd234_78.subtract(eur100_45);
		assertSame(Currency.USD, difference.getCurrency());
		BigDecimal expectedNumeral = new BigDecimal(
				BigInteger.valueOf(9226), 2);
		assertEquals(expectedNumeral, difference.getNumeral());
	}

	@Test(expected = IllegalArgumentException.class)
	public void subtract_NonEffectiveOperand() {
		eur100_45.subtract(null);
	}
	
	@Test
	public void compareTo_EqualAmounts() {
		MoneyAmount eur100_45BIS = new MoneyAmount(new BigDecimal(BigInteger.valueOf(10045),2));
		assertEquals(0,eur100_45.compareTo(eur100_45BIS));		
	}
	
	@Test
	public void compareTo_AmountLessThan() {
		assertTrue(usd123_20.compareTo(usd234_78) < 0);		
	}
	
	@Test
	public void compareTo_AmountGreatherThan() {
		assertTrue(usd234_78.compareTo(usd123_20) > 0);		
	}

	@Test(expected = ClassCastException.class)
	public void compareTo_DifferentCurrencies() {
		eur100_45.compareTo(usd123_20);
	}

	@Test(expected = ClassCastException.class)
	public void compareTo_NonEffectiveOperand() {
		eur100_45.compareTo(null);
	}
	
	@Test
	public void signum_NegativeAmount() {
		MoneyAmount negativeAmount = new MoneyAmount(new BigDecimal(BigInteger.valueOf(-10045),2));
		assertEquals(-1,negativeAmount.signum());		
	}
	
	@Test
	public void signum_ZeroAmount() {
		assertEquals(0,MoneyAmount.EUR_0.signum());		
	}
	
	@Test
	public void signum_PositiveAmount() {
		assertEquals(1,eur100_45.signum());		
	}
	
	@Test
	public void hasSameValueAs_EqualAmounts() {
		MoneyAmount usd142_52 = new MoneyAmount(new BigDecimal(BigInteger.valueOf(14252),2),Currency.USD);
		assertTrue(eur100_45.hasSameValueAs(usd142_52));		
	}
	
	@Test
	public void hasSameValueAs_NonEqualAmounts() {
		assertFalse(usd123_20.hasSameValueAs(usd234_78));		
	}

	@Test(expected = IllegalArgumentException.class)
	public void hasSameValueAs_NonEffectiveAmount() {
		eur100_45.hasSameValueAs(null);
	}

	@Test
	public void equals_EqualAmounts() {
		MoneyAmount eur100_45BIS = new MoneyAmount(new BigDecimal(BigInteger.valueOf(10045),2));
		assertTrue(eur100_45.equals(eur100_45BIS));		
	}

	@Test
	public void equals_NonEffectiveAmount() {
		assertFalse(eur100_45.equals(null));		
	}

	@Test
	public void equals_NonAmount() {
		assertFalse(eur100_45.equals(new Integer(12)));		
	}

	@Test
	public void equals_DifferentNumerals() {
		assertFalse(usd123_20.equals(usd234_78));		
	}

	@Test
	public void equals_DifferentCurrencies() {
		MoneyAmount usd100_45 = new MoneyAmount(new BigDecimal(BigInteger.valueOf(10045),2),Currency.USD);
		assertFalse(eur100_45.equals(usd100_45));		
	}

	@Test
	public void hasCode_EqualAmounts() {
		MoneyAmount eur100_45BIS = new MoneyAmount(new BigDecimal(BigInteger.valueOf(10045),2));
		assertTrue(eur100_45.hashCode() == eur100_45BIS.hashCode());		
	}
	
	@Test
	public void toString_SingleCase() {
		assertEquals("[100.45 EUR]",eur100_45.toString());
	}

	@Test
	public void getContextForScale2_SingleCase() {
		MathContext context = MoneyAmount.getScale2ContextFor(usd123_20.getNumeral());
		assertEquals(5,context.getPrecision());
		assertSame(RoundingMode.HALF_DOWN,context.getRoundingMode());
	}
}
