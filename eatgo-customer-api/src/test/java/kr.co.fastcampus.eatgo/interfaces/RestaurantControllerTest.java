package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder().id(1L).categoryId(1L).name("bob zip").address("seoul").build();
        restaurants.add(restaurant);
        given(restaurantService.getRestaurants("seoul", 1L)).willReturn(restaurants);

        mvc.perform(get("/restaurant?region=seoul&category=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1")))
                .andExpect(content().string(containsString("\"name\":\"bob zip\"")));
    }


    @Test
    public void 레스토랑조회withExisted() throws Exception {

        Restaurant restaurant1 = Restaurant.builder().id(1L).name("bob zip").address("seoul").build();
        restaurant1.addMenuItem(new MenuItem("kimchi"));
        restaurant1.setMenuItems(Arrays.asList(MenuItem.builder().name("kimchi").build()));
        restaurant1.setReviews(Arrays.asList(Review.builder().name("zoey").score(5).description("yummy").build()));

        Restaurant restaurant2 = Restaurant.builder().id(2L).name("cyber food").address("seoul").build();

        given(restaurantService.getRestaurantById(1L)).willReturn(restaurant1);
        given(restaurantService.getRestaurantById(2L)).willReturn(restaurant2);


        mvc.perform(get("/restaurant/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1")))
                .andExpect(content().string(containsString("\"name\":\"bob zip\"")))
                .andExpect(content().string(containsString("kimchi")))
                .andExpect(content().string(containsString("yummy")));
        mvc.perform(get("/restaurant/2"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":2")));
//                .andExpect(content().string(containsString("\"name\":\"cyber food\"")));
    }

    @Test
    public void 레스토랑조회withNotExisted() throws Exception {
        given(restaurantService.getRestaurantById(404L)).willThrow(
                new RestaurantNotFoundException(404L)
        );

        mvc.perform(get("/restaurant/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }

}