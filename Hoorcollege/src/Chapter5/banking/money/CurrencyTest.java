package Chapter5.banking.money;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.*;

public class CurrencyTest {
	
	@Test
	public void toCurrency_SameCurrency() {
		BigDecimal result = Currency.EUR.toCurrency(Currency.EUR);
		assertEquals(BigDecimal.ONE,result);
		
	}

	@Test
	public void toCurrency_DifferentCurrency() {
		BigDecimal result = Currency.EUR.toCurrency(Currency.USD);
		assertTrue(result.signum() == 1);
		assertSame(Currency.currencyContext.getPrecision(),result.precision());
		BigDecimal inverse = Currency.USD.toCurrency(Currency.EUR);
		assertEquals(BigDecimal.ONE.divide(inverse,Currency.currencyContext),result);
	}

	@Test
	public void toCurrency_NonEffectiveCurrency() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Currency.EUR.toCurrency(null);
		});
	}
	
	@Test
	public void currencyContext_SingleCase() {
		assertEquals(6,Currency.currencyContext.getPrecision());
		assertSame(RoundingMode.HALF_DOWN,Currency.currencyContext.getRoundingMode());
	}

}
