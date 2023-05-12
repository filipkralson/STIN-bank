package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.Account;
import cz.tul.kral.bank.model.User;
import cz.tul.kral.bank.service.AccountService;
import cz.tul.kral.bank.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig
public class BankControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private BankController bankController;

    @BeforeEach
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        mockMvc = MockMvcBuilders.standaloneSetup(bankController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testShowHomePage() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", "1");
        User user = new User();
        user.setId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        List<Account> accounts = new ArrayList<>();
        Account account1 = new Account();
        account1.setId(1);
        Account account2 = new Account();
        account2.setId(2);
        accounts.add(account1);
        accounts.add(account2);
        user.setAccounts(accounts);

        when(userService.getUserById(anyInt())).thenReturn(user);

        mockMvc.perform(get("/home").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("name", "John"))
                .andExpect(model().attribute("surname", "Doe"))
                .andExpect(model().attribute("id", 1))
                .andExpect(model().attribute("ucty", accounts));
    }

    @Test
    public void testShowCreateAccount() throws Exception {
        mockMvc.perform(get("/create-account"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-account"));
    }

    @Test
    public void testShowCreateAccountRedirect() throws Exception {
        mockMvc.perform(get("/createAcc"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/create-account"));
    }

    @Test
    public void testShowLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testShowVerificationPage() throws Exception {
        mockMvc.perform(get("/verification"))
                .andExpect(status().isOk())
                .andExpect(view().name("verification"));
    }
}
