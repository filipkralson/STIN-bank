package cz.tul.kral.bank.controller;

import cz.tul.kral.bank.model.User;
import cz.tul.kral.bank.service.UserService;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JavaMailSender javaMailSender;

    @Test
    public void testProcessRegisterWithValidData() throws Exception {
        mockMvc.perform(post("/register")
                        .param("name", "John")
                        .param("surname", "Doe")
                        .param("email", "john.doe@example.com")
                        .param("password", "secret"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?userId=0"));

        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    public void testProcessLoginWithValidCredentials() throws Exception {
        User user = new User();
        user.setId(1);
        user.setEmail("john.doe@example.com");
        user.setPassword("secret");
        when(userService.getUserById(1)).thenReturn(user);

        mockMvc.perform(post("/login")
                        .param("id", "1")
                        .param("password", "secret"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/verification"));

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    /*
    @Test
    public void testProcessLoginWithInvalidCredentials() throws Exception {
        when(userService.getUserById(1)).thenReturn(null);

        mockMvc.perform(post("/login")
                        .param("id", "1")
                        .param("password", "secret123"))
                .andExpect(status().isOk())
                .andExpect(view().name((Matcher<? super String>) null))
                .andExpect(model().attribute("warningLogin", "Špatně zadané údaje!"))
                .andExpect(redirectedUrl("/login"));
    }
     */


    @Test
    public void testProcessVerificationWithValidCode() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("code", 1234);

        mockMvc.perform(post("/verification")
                        .param("verification", "1234")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    /*
    @Test
    public void testProcessVerificationWithInvalidCode() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("code", 1234);

        mockMvc.perform(post("/verification")
                        .param("verification", "5678")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("verification"))
                .andExpect(model().attribute("warningLogin", "Zadali jste špatný kód"))
                .andExpect(redirectedUrl("/verification"));
    }
     */

}
