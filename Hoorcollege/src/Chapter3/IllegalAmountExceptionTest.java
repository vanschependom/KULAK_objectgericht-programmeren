package Chapter3;

import org.junit.jupiter.api.*;
import be.kuleuven.cs.som.annotate.*;

public class IllegalAmountExceptionTest {

    @Test
    public void constructor_SingleCase() {
        BankAccount theAccount = new BankAccount(222);
        IllegalAmountException exception = new IllegalAmountException(2000L,theAccount);
        Assertions.assertEquals(2000,exception.getAmount());
        Assertions.assertSame(theAccount,exception.getAccount());
        Assertions.assertNull(exception.getMessage());
        Assertions.assertNull(exception.getCause());
    }
}