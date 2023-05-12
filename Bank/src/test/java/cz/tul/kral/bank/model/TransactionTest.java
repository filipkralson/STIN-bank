package cz.tul.kral.bank.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;

public class TransactionTest {

    private Account account;
    private Transaction transaction;

    @Before
    public void setUp() {
        account = new Account(123456, "John Doe", 1000.0);
        transaction = new Transaction("Deposit", 500.0, account);
    }

    @Test
    public void testGetId() {
        assertEquals(0, transaction.getId());
    }

    @Test
    public void testGetType() {
        assertEquals("Deposit", transaction.getType());
    }

    @Test
    public void testGetRecipient_account() {
        assertEquals(0, transaction.getRecipient_account());
    }

    @Test
    public void testGetValue() {
        assertEquals(500.0, transaction.getValue(), 0.01);
    }

    @Test
    public void testGetTransaction_date() {
        assertEquals(LocalDateTime.now().getDayOfYear(), transaction.getTransaction_date().getDayOfYear());
    }

    @Test
    public void testSetId() {
        transaction.setId(1);
        assertEquals(1, transaction.getId());
    }

    @Test
    public void testSetType() {
        transaction.setType("Withdrawal");
        assertEquals("Withdrawal", transaction.getType());
    }

    @Test
    public void testSetRecipient_account() {
        transaction.setRecipient_account(123456);
        assertEquals(123456, transaction.getRecipient_account());
    }

    @Test
    public void testSetValue() {
        transaction.setValue(1000.0);
        assertEquals(1000.0, transaction.getValue(), 0.01);
    }

    @Test
    public void testSetTransaction_date() {
        LocalDateTime newDate = LocalDateTime.now().minusDays(1);
        transaction.setTransaction_date(newDate);
        assertEquals(newDate.getDayOfYear(), transaction.getTransaction_date().getDayOfYear());
    }

    @Test
    public void testSetAccount() {
        Account newAccount = new Account(654321, "Jane Smith", 2000.0);
        transaction.setAccount(newAccount);
        assertEquals(newAccount, transaction.getAccount());
    }

    @Test
    public void testConstructorWithRecipientAccount() {
        Transaction newTransaction = new Transaction("Transfer", 200.0, 654321, account);
        assertEquals("Transfer", newTransaction.getType());
        assertEquals(200.0, newTransaction.getValue(), 0.01);
        assertEquals(654321, newTransaction.getRecipient_account());
        assertEquals(account, newTransaction.getAccount());
    }
}
