package cz.tul.kral.bank.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setCurrency("CZK");
        account.setUser(new User());
        account.setBalance(1000);
    }

    @Test
    void testPay() {
        boolean result = account.pay(500);
        Assertions.assertTrue(result);
        Assertions.assertEquals(500, account.getBalance());
    }

    @Test
    void testPayInvalidAmount() {
        boolean result = account.pay(2000);
        Assertions.assertFalse(result);
        Assertions.assertEquals(1000, account.getBalance());
    }

    @Test
    void testSetTransactions() {
        Transaction transaction = new Transaction();
        account.setTransactions(transaction);
        Assertions.assertEquals(1, account.getTransactions().size());
    }
}
