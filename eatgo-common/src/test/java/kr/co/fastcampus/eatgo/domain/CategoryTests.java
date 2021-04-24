package kr.co.fastcampus.eatgo.domain;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CategoryTests {

    @Test
    public void creation() {
        Category category = Category.builder().name("korean food").build();
        assertThat(category.getName() , is("korean food"));
    }

}
