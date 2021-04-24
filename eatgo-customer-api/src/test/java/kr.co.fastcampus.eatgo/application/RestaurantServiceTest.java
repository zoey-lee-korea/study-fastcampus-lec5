package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
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

public class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private MenuItemRepository menuItemRepository;
    @Mock
    private ReviewRepository reviewRepository;

    @Before // 사전에 의존성 주입
    public void setup(){
//        restaurantRepository = new RestaurantRepositoryImpl();
//        menuItemRepository = new MenuItemRepositoryImpl();
        MockitoAnnotations.openMocks(this); // 해당 클래스에 있는 @Mock선언된 객체들을 초기화해 준다. 하지 않는경우 초기화되지 않아 null exception이 난다

        mockRestaurantRepository();
        mockMenuItemRepository();
        mockReviewRespository();

        restaurantService = new RestaurantService(restaurantRepository, menuItemRepository,reviewRepository);
    }


    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(Restaurant.builder().id(1L).categoryId(1L).name("bob zip").address("seoul").build());
        restaurants.add(Restaurant.builder().id(2L).categoryId(2L).name("cyber food").address("seoul").build());
        given(restaurantRepository.findAllByAddressContainingAndCategoryId("seoul", 1L)).willReturn(restaurants);

        Restaurant restaurant = Restaurant.builder().id(1L).name("bob zip").address("seoul").build();;
        restaurant.addMenuItem(MenuItem.builder().name("kimchi").build());
        given(restaurantRepository.findById(1L)).willReturn(Optional.of(restaurant));

    }

    private void mockMenuItemRepository() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(MenuItem.builder().name("kimchi").build());

        given(menuItemRepository.findByRestaurantId(1L)).willReturn(menuItems);
    }

    private void mockReviewRespository() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(Review.builder().name("zoey").score(5).description("yummy").build());

        given(reviewRepository.findByRestaurantId(1L)).willReturn(reviews);
    }

    @Test
    public void 레스토랑목록조회(){
        List<Restaurant> restaurants = restaurantService.getRestaurants("seoul", 1L);
        Restaurant restaurant = restaurants.get(0);
        assertThat(restaurant.getName(), is("bob zip"));
    }

    @Test
    public void 레스토랑정보조회withExisted(){
        Restaurant restaurant = restaurantService.getRestaurantById(1L);
        assertThat(restaurant.getName(), is("bob zip"));

        verify(menuItemRepository).findByRestaurantId(eq(1L));
        verify(reviewRepository).findByRestaurantId(eq(1L));

        MenuItem menuItem = restaurant.getMenuItems().get(0);
        assertThat(menuItem.getName(),is("kimchi"));

        Review review = restaurant.getReviews().get(0);
        assertThat(review.getName(),is("zoey"));

    }

    @Test(expected = RestaurantNotFoundException.class)
    public void 레스토랑정보조회withNotExisted(){
        Restaurant restaurant = restaurantService.getRestaurantById(404L);
    }

    @Test
    public void 레스토랑추가(){
        Restaurant restaurant = Restaurant.builder().name("china food").address("seoul").build();
        Restaurant saved_restaurant = Restaurant.builder().id(3L).name("china food").address("seoul").build();;

        given(restaurantRepository.save(any())).willReturn(saved_restaurant);

        Restaurant created_restaurant = restaurantService.addRestaurant(restaurant);
        assertThat(created_restaurant.getId(), is(3L));
        assertThat(created_restaurant.getName(), is("china food"));
    }

    @Test
    public void 레스토랑수정(){
        Restaurant restaurant = Restaurant.builder().id(1L).name("bob zip").address("seoul").build();
        given(restaurantRepository.findById(1L)).willReturn(Optional.of(restaurant));

        restaurantService.updateRestaurant(1L,"bob house","iksan");
        assertThat(restaurant.getName(),is("bob house"));
        assertThat(restaurant.getAddress(),is("iksan"));

    }


}