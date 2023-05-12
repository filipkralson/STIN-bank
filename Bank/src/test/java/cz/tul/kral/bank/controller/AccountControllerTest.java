package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.Account;
import cz.tul.kral.bank.model.User;
import cz.tul.kral.bank.service.AccountService;
import cz.tul.kral.bank.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @Mock
    private UserService userService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessCreateAccount() {
        // Arrange
        String currency = "USD";
        User user = new User();
        user.setId(1);
        when(session.getAttribute("user")).thenReturn("1");
        when(userService.getUserById(1)).thenReturn(user);

        // Act
        String result = accountController.processCreateAccount(currency, session);

        // Assert
        assertNotNull(result);
        assertEquals("redirect:/home", result);
        verify(userService, times(1)).getUserById(1);

        Account expectedAccount = new Account();
        expectedAccount.setCurrency(currency);
        expectedAccount.setBalance(0);
        expectedAccount.setUser(user);
        verify(accountService, times(1)).createAccount(expectedAccount);
        verify(userService, times(1)).createUser(user);
    }
}