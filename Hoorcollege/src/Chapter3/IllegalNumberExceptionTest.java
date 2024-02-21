package Chapter3;

import be.kuleuven.cs.som.annotate.*;
import org.junit.jupiter.api.*;

public class IllegalNumberExceptionTest {

    @Test
    public void constructor_SingleCase() {
        BankAccount theAccount = new BankAccount(7777);
        IllegalNumberException exception = new IllegalNumberException(2000,theAccount);
        Assertions.assertEquals(2000,exception.getNumber());
        Assertions.assertSame(theAccount,exception.getAccount());
        Assertions.assertNull(exception.getMessage());
        Assertions.assertNull(exception.getCause());
    }
}
