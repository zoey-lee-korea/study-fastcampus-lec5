package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


public class UserServiceTests {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository,passwordEncoder);
    }

    @Test
    public void registerUser(){
        String email = "zoey@example.com";
        String name ="zoey";
        String password = "zoey";
        User mockUser = User.builder().id(1L).email(email).name(name).password(password).build();
        given(userRepository.save(any())).willReturn(mockUser);

        User user = userService.registerUser(email,name,password);
        assertThat(user.getEmail(), is(email));

        verify(userRepository).save(any());
    }

    @Test(expected = EmailExistedException.class)
    public void registerUserWithExistedEmail(){
        String email = "zoey@example.com";
        String name ="zoey";
        String password = "zoey";
        User mockUser = User.builder().email(email).build();
        given(userRepository.findByEmail(any())).willReturn(Optional.of(mockUser));

        User user = userService.registerUser(email,name,password);

        verify(userRepository, never()).save(any());
    }

//    @Test
//    public void authenticateWithValidAttribute() {
//        String email = "zoey@example.com";
//        String password = "zoey";
//        User mockUser = User.builder().email(email).build();
//        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));
//        given(passwordEncoder.matches(any(),any())).willReturn(Boolean.TRUE);
//
//        User user = userService.authenticate(email,password);
//        assertThat(user.getEmail(), is(email));
//
//    }
//
//    @Test(expected = EmailNotExistedException.class)
//    public void authenticateWithEmailNotExisted() {
//        String email = "abc@example.com";
//        String password = "zoey";
//        User mockUser = User.builder().email(email).build();
//        given(userRepository.findByEmail(email)).willReturn(Optional.empty());
//
//        userService.authenticate(email,password);
//    }
//
//    @Test(expected = PasswordWrongException.class)
//    public void authenticateWithWrongPassword() {
//        String email = "zoey@example.com";
//        String password = "abc";
//        User mockUser = User.builder().email(email).build();
//        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));
//        given(passwordEncoder.matches(any(),any())).willReturn(Boolean.FALSE);
//
//        userService.authenticate(email,password);
//    }

}