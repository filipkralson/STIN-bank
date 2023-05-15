package cz.tul.kral.bank.controller;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import cz.tul.kral.bank.model.Account;
import cz.tul.kral.bank.service.AccountService;
import cz.tul.kral.bank.service.TransactionService;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig
class TransactionControllerTest {

    private TransactionController controller;
    private MockMvc mockMvc;

    private HttpSession session;

    @BeforeEach
    void setUp() {
        AccountService accountService = new AccountService();
        TransactionService transactionService = new TransactionService();
        controller = new TransactionController();
        controller.setAccountService(accountService);
        controller.setTransactionService(transactionService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void deposit() throws Exception {
        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("user", "testUser");

        mockMvc.perform(post("/transaction-deposit")
                        .session(mockSession)
                        .param("insert", "100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    void pay() throws Exception {
        mockMvc.perform(post("/transaction-pay")
                        .session((MockHttpSession) session.getAttribute("user"))
                        .param("insert", "50")
                        .param("recipient_account", "123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    void processTransactionDeposit() throws Exception {
        mockMvc.perform(post("/deposit")
                        .param("idUcet", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transaction-deposit"));
    }

    @Test
    void processTransactionPay() throws Exception {
        mockMvc.perform(post("/pay")
                        .param("idUcet", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transaction-pay"));
    }

    @Test
    void processTransactions() throws Exception {
        mockMvc.perform(post("/transactions")
                        .param("idUcet", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transactions"));
    }
}