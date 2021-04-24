package kr.co.fastcampus.eatgo.domain;

import jdk.jfr.Description;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RestaurantTest {

    @Test
    @Description("레스토랑객체생성")
    public void create() {
        Restaurant restaurant = Restaurant.builder().id(1L).name("bob zip").address("seoul").build();
        assertThat(restaurant.getName(),is("bob zip"));
        assertThat(restaurant.getAddress(),is("seoul"));
        assertThat(restaurant.getInformation(),is("bob zip in seoul"));
    }

}