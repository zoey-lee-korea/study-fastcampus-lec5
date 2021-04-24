package kr.co.fastcampus.eatgo.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserTests {

    @Test
    public void creation() {
        User user = User.builder()
                .email("tester@gmail.com")
                .name("테스터")
                .level(3L)
                .password("ahjkdfkjsj;fda")
                .build();
        assertThat(user.getEmail(), is("tester@gmail.com"));
        assertThat(user.isAdmin(), is(true));
        assertThat(user.isActive(), is(true));

        user.deactivate();
        assertThat(user.isActive(), is(false));
//        assertThat(user.getAcessToken(),is("ahjkdfkjsj"));
    }

    @Test
    public void isRestaurantOwner() {
        User user = User.builder().level(1L).build();
        assertThat(user.isOwner(), is(Boolean.FALSE));
        user.setRestaurantId(50L);
        assertThat(user.isOwner(), is(Boolean.TRUE));
    }
}