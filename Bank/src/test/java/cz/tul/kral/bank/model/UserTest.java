package cz.tul.kral.bank.model;

    import org.junit.jupiter.api.Assertions;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
    }

    @Test
    void testGetFirstName() {
        Assertions.assertEquals("John", user.getFirstName());
    }

    @Test
    void testSetFirstName() {
        user.setFirstName("Jane");
        Assertions.assertEquals("Jane", user.getFirstName());
    }

    @Test
    void testGetLastName() {
        Assertions.assertEquals("Doe", user.getLastName());
    }

    @Test
    void testSetLastName() {
        user.setLastName("Smith");
        Assertions.assertEquals("Smith", user.getLastName());
    }

    @Test
    void testGetEmail() {
        Assertions.assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    void testSetEmail() {
        user.setEmail("jane.smith@example.com");
        Assertions.assertEquals("jane.smith@example.com", user.getEmail());
    }

    @Test
    void testGetId() {
        Assertions.assertEquals(0, user.getId());
    }

    @Test
    void testGetAccounts() {
        List<Account> accounts = new ArrayList<>();
        Account account1 = new Account();
        account1.setCurrency("USD");
        Account account2 = new Account();
        account2.setCurrency("EUR");
        accounts.add(account1);
        accounts.add(account2);
        user.setAccounts(account1);
        user.setAccounts(account2);
        Assertions.assertEquals(accounts, user.getAccounts());
    }

    @Test
    void testSetAccounts() {
        List<Account> accounts = new ArrayList<>();
        Account account1 = new Account();
        account1.setCurrency("USD");
        Account account2 = new Account();
        account2.setCurrency("EUR");
        accounts.add(account1);
        accounts.add(account2);
        user.setAccounts(account1);
        user.setAccounts(account2);
        Assertions.assertEquals(accounts, user.getAccounts());
    }

    @Test
    void testGetPassword() {
        Assertions.assertEquals("password", user.getPassword());
    }

    @Test
    void testSetPassword() {
        user.setPassword("newpassword");
        Assertions.assertEquals("newpassword", user.getPassword());
    }
}

