package cz.tul.kral.bank.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {

    private Account account;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        account = new Account();
        account.setCurrency("CZK");
        account.setUser(user);
        account.setBalance(1000);
    }


    @Test
    void testSetTransactions() {
        Transaction transaction = new Transaction();
        account.setTransactions(transaction);
        assertEquals(1, account.getTransactions().size());
    }

    @Test
    public void testPaySufficientBalance() throws Exception {
        // Arrange
        double amount = 50.0;

        // Act
        account.pay(amount);

        // Assert
        assertEquals(950.0, account.getBalance());
    }

    @Test
    public void testPayInsufficientBalance() {
        // Arrange
        double amount = 1050.0;

        // Assert
        assertThrows(Exception.class, () -> account.pay(amount));
    }

    @Test
    public void testGetUser() {
        // Act
        User result = account.getUser();

        // Assert
        assertEquals(user, result);
    }

    @Test
    public void testGetId() {
        // Act
        int result = user.getId();

        // Assert
        assertEquals(1, result);
    }

    @Test
    public void testGetCurrency() {
        // Act
        String result = account.getCurrency();

        // Assert
        assertEquals("CZK", result);
    }
}
