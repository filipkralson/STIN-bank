package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.Account;
import cz.tul.kral.bank.model.CurrencyExchangeRate;
import cz.tul.kral.bank.model.Transaction;
import cz.tul.kral.bank.service.AccountService;
import cz.tul.kral.bank.service.CurrencyExchangeRateService;
import cz.tul.kral.bank.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;
    @Mock
    private AccountService accountService;
    @Mock
    private CurrencyExchangeRateService currencyExchangeRateService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private Model model;

    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionController = new TransactionController();
        transactionController.setTransactionService(transactionService);
        transactionController.setAccountService(accountService);
        transactionController.setCurrencyExchangeRateService(currencyExchangeRateService);
    }

    @Test
    void testDeposit() {
        // Arrange
        double insert = 100;
        String currency = "USD";
        int id = 1;
        Account account = new Account();
        account.setCurrency("EUR");
        account.setBalance(500);
        when(request.getParameter("currency")).thenReturn(currency);
        when(session.getAttribute("idAcc")).thenReturn(String.valueOf(id));
        when(accountService.getAccountById(id)).thenReturn(account);
        when(currencyExchangeRateService.getExchangeRateByCode(currency)).thenReturn(new CurrencyExchangeRate());
        when(currencyExchangeRateService.getExchangeRateByCode(account.getCurrency())).thenReturn(new CurrencyExchangeRate());

        // Act
        String viewName = transactionController.deposit(insert, request, session);

        // Assert
        assertEquals("redirect:/home", viewName);
        verify(accountService).createAccount(account);
        verify(transactionService).createTransaction(any(Transaction.class));
    }

    @Test
    void testPay() {
        // Arrange
        double amount = 50;
        String currency = "EUR";
        String recipientAccount = "123456789";
        int id = 1;
        Account account = new Account();
        account.setCurrency("EUR");
        account.setBalance(500);
        when(request.getParameter("currency")).thenReturn(currency);
        when(session.getAttribute("idAcc")).thenReturn(String.valueOf(id));
        when(accountService.getAccountById(id)).thenReturn(account);
        when(currencyExchangeRateService.getExchangeRateByCode(currency)).thenReturn(new CurrencyExchangeRate());
        when(currencyExchangeRateService.getExchangeRateByCode(account.getCurrency())).thenReturn(new CurrencyExchangeRate());

        // Act
        String viewName = transactionController.pay(amount, request, recipientAccount, session, model);

        // Assert
        assertEquals("redirect:/home", viewName);
        verify(accountService).createAccount(account);
        verify(transactionService).createTransaction(any(Transaction.class));
    }
}
