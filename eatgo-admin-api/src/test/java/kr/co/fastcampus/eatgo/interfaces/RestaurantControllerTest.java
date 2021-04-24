package kr.co.fastcampus.eatgo.interfaces;

import jdk.jfr.Description;
import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @Description("레스토랑 목록 조회")
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder().id(1L).categoryId(1L).name("bob zip").address("seoul").build();
        restaurants.add(restaurant);
        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurant"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1")))
                .andExpect(content().string(containsString("\"name\":\"bob zip\"")));
    }

    @Test
    @Description("레스토랑조회withExisted")
    public void detailwithExisted() throws Exception {

        Restaurant restaurant1 = Restaurant.builder().id(1L).categoryId(1L).name("bob zip").address("seoul").build();
//        restaurant1.addMenuItem(new MenuItem("kimchi"));
//        restaurant1.setMenuItems(Arrays.asList(MenuItem.builder().name("kimchi").build()));
//        restaurant1.setReviews(Arrays.asList(Review.builder().name("zoey").score(5).description("yummy").build()));

        Restaurant restaurant2 = Restaurant.builder().id(2L).categoryId(2L).name("cyber food").address("seoul").build();

        given(restaurantService.getRestaurantById(1L)).willReturn(restaurant1);
        given(restaurantService.getRestaurantById(2L)).willReturn(restaurant2);


        mvc.perform(get("/restaurant/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1")))
                .andExpect(content().string(containsString("\"name\":\"bob zip\"")));
//                .andExpect(content().string(containsString("kimchi")))
//                .andExpect(content().string(containsString("yummy")));
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

    @Test
    public void createWithValidData() throws Exception {
//        Restaurant restaurant = Restaurant.builder().id(3L).categoryId(3L).name("china food").address("seoul").build();
//
//        mvc.perform(post("/restaurants")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"category\":3, \"name\" : \"china food\" , \"address\" : \"seoul\" }"))
//                .andExpect(status().isCreated())
//                .andExpect(header().string("location","/restaurant/0")) // 헤더의 key-value값을 검증한다
//                .andExpect(content().string("{}"));
//
//        // 리턴된 결과를 확인하는게 중요한게 아니라, 제대로 추가되었는지가 중요한 것
//        verify(restaurantService).addRestaurant(any()); // 제대로 들어갔는지만 확인하는 것

    }

    @Test
    public void 레스토랑추가withInvalidData() throws Exception {
//        given(restaurantService.addRestaurant(any())).will(invocation -> {
//            Restaurant restaurant = invocation.getArgument(0);
//            return Restaurant.builder()
//                                .id(1L)
//                                .name(restaurant.getName())
//                                .address(restaurant.getAddress())
//                                .build();
//        });

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"category\":, \"name\" : \"\" , \"address\" : \"\" }"))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void 레스토랑수정withValidData() throws Exception {
//        mvc.perform(patch("/restaurant/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"name\" : \"bob house\", \"address\" : \"iksan\"}"))
//                .andExpect(status().isOk());
//        verify(restaurantService).updateRestaurant(1L,"bob house","iksan");

    }

    @Test
    public void 레스토랑수정withInvalidData() throws Exception {
        mvc.perform(patch("/restaurant/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"category\":,\"name\" : \"\", \"address\" : \"\"}"))
                .andExpect(status().isBadRequest());
    }


//    @SpyBean(RestaurantService.class) // ()안에 어떤 구현체를 사용할지 명시한다 // 진짜로 넣어주는 방법 <-> 가짜로 넣어주는 방법(MockBean)
//    private RestaurantService restaurantService; // 이걸 주입해주지 않으면, restaurantRepository 객체가 없다고 에러 발생
//
//    @SpyBean(RestaurantRepositoryImpl.class)
//    private RestaurantRepository restaurantRepository;
//
//    @SpyBean(MenuItemRepositoryImpl.class)
//    private MenuItemRepository menuItemRepository;

}