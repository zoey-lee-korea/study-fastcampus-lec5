package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.EmailNotExistedException;
import kr.co.fastcampus.eatgo.application.PasswordWrongException;
import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.utils.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    @Test
    public void createWithValidAttribute() throws Exception {
        User mockUser = User.builder().id(1L).name("zoey").password("ACCESSTOKEN").level(1L).build();
        given(userService.authenticate("zoey@example.com","zoey")).willReturn(mockUser);
        given(jwtUtil.createToken(1L,"zoey", null)).willReturn("header.payload.signature");

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"zoey@example.com\",\"password\":\"zoey\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/session/1"))
                .andExpect(content().string("{\"accessToken\":\"header.payload.signature\"}"))
                .andExpect(content().string(containsString(".")));

        verify(userService).authenticate(eq("zoey@example.com"),eq("zoey"));
    }

    @Test
    public void createRestaurantOwner() throws Exception {
        User mockUser = User.builder().id(1L).name("zoey").password("ACCESSTOKEN").level(50L).restaurantId(1L).build();
        given(userService.authenticate("zoey@example.com","zoey")).willReturn(mockUser);
        given(jwtUtil.createToken(1L,"zoey", 1L)).willReturn("header.payload.signature");

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"zoey@example.com\",\"password\":\"zoey\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/session/1"))
                .andExpect(content().string("{\"accessToken\":\"header.payload.signature\"}"))
                .andExpect(content().string(containsString(".")));

        verify(userService).authenticate(eq("zoey@example.com"),eq("zoey"));
    }

    @Test
    public void createWithWrongPassword() throws Exception {
        given(userService.authenticate("zoey@example.com","abc")).willThrow(PasswordWrongException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"zoey@example.com\",\"password\":\"abc\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("zoey@example.com"),eq("abc"));
    }

    @Test
    public void createWithNotExistedEmail() throws Exception {
        given(userService.authenticate("abc@example.com","abc")).willThrow(EmailNotExistedException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"abc@example.com\",\"password\":\"abc\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("abc@example.com"),eq("abc"));
    }

}