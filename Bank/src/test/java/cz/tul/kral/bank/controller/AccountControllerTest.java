package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.Account;
import cz.tul.kral.bank.model.User;
import cz.tul.kral.bank.service.AccountService;
import cz.tul.kral.bank.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class AccountControllerTest {

    @Mock
    private AccountService accountService;
    @Mock
    private UserService userService;
    @Mock
    private HttpSession session;

    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accountController = new AccountController();
        accountController.setAccountService(accountService);
        accountController.setUserService(userService);
    }

    @Test
    void testProcessCreateAccount() {
        // Arrange
        int userId = 1;
        User user = new User();
        user.setId(userId);
        Account existingAccount = new Account();
        existingAccount.setCurrency("EUR");
        existingAccount.setBalance(100);
        user.setAccounts(existingAccount);
        when(session.getAttribute("user")).thenReturn(String.valueOf(userId));
        when(userService.getUserById(userId)).thenReturn(user);

        // Act
        String viewName = accountController.processCreateAccount(existingAccount.getCurrency(), session);

        // Assert
        assertEquals("redirect:/home", viewName);
        verify(userService).createUser(user);
        verify(accountService).createAccount(any(Account.class));
        assertEquals("EUR", existingAccount.getCurrency());
        assertEquals(100, existingAccount.getBalance());
    }

}