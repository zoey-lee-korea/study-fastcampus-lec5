package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;


    @Test
    public void list() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(User.builder().email("tester@gmail.com").name("tester").level(1L).build());
        given(userService.getUsers()).willReturn(users);

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("tester@gmail.com")));
    }

    @Test
    public void create() throws Exception {
        String email = "admin@example.com";
        String name = "admin";
        User user = User.builder().id(0L).email(email).name(name).level(1L).build();
        given(userService.addUser(email,name)).willReturn(user);

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"admin@example.com\",\"name\":\"admin\"}"))
                        .andExpect(status().isCreated())
                        .andExpect(header().string("location","/users/0"));

        verify(userService).addUser(email,name);
    }

    @Test
    public void update() throws Exception {
        Long id = 0L;
        String email = "updatedAdmin@example.com";
        String name = "updatedAdmin";
        Long level = 100L;
//        User user = User.builder().id(0L).email(email).name(name).level(level).build();
//        given(userService.updateUser(id,email,name,level)).willReturn(user);

        mvc.perform(patch("/users/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"updatedAdmin@example.com\",\"name\":\"updatedAdmin\",\"level\":100}"))
                .andExpect(status().isOk());

        verify(userService).updateUser(eq(id),eq(email),eq(name),eq(level));
    }

    @Test
    public void deactivate() throws Exception {
        mvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
        verify(userService).deactivateUser(any());
    }

}