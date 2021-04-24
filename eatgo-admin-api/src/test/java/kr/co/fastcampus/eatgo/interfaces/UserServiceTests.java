package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class UserServiceTests {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void getUsers() {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(User.builder().email("tester@example.com").name("tester").level(1L).build());
        given(userRepository.findAll()).willReturn(mockUsers);

        List<User> users = userService.getUsers();
        User user = users.get(0);
        assertThat(user.getEmail(), is("tester@example.com"));
        assertThat(user.getName(), is("tester"));

    }

    @Test
    public void addUser() {
        String email = "admin@example.com";
        String name = "admin";
        Long level = 3L;
        User mockUser = User.builder().id(0L).email(email).name(name).level(level).build();
        given(userRepository.save(any())).willReturn(mockUser);

        User user = userService.addUser(email,name);
        assertThat(user.getEmail(), is(email));
        assertThat(user.getName(), is(name));
    }

    @Test
    public void updateUser() {
        Long id = 0L;
        String email = "updatedAdmin@example.com";
        String name = "updatedAdmin";
        Long level = 3L;
        User mockUser = User.builder().id(id)
                        .email("admin@example.com").name("admin").level(level).build();

        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));

        User user = userService.updateUser(id,email,name,level);
        assertThat(user.getId(), is(id));
        assertThat(user.getEmail(), is(email));
        assertThat(user.getName(), is(name));
        assertThat(user.isAdmin(), is(true));

        verify(userRepository).findById(eq(id));

    }

    @Test
    public void deleteUser() {
        String email = "admin@example.com";
        String name = "admin";
        User mockUser = User.builder().id(1L).email(email).name(name).level(3L).build();
        given(userRepository.findById(1L)).willReturn(Optional.of(mockUser));

        User user = userService.deactivateUser(1L);
        assertThat(user.getLevel(), is(0L));
        assertThat(user.isAdmin(), is(false));
        assertThat(user.isActive(), is(false));

        verify(userRepository).findById(eq(1L));

    }

}