package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

//    @Autowired
//    private MenuItemRepository menuItemRepository;
//
//    @Autowired
//    private ReviewRepository reviewRepository;
//
//    public RestaurantService(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository, ReviewRepository reviewRepository) {
//        this.restaurantRepository = restaurantRepository;
//        this.menuItemRepository = menuItemRepository;
//        this.reviewRepository = reviewRepository;
//    }
    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                                                     .orElseThrow(() -> new RestaurantNotFoundException(id));

//        List<MenuItem> menuItems = menuItemRepository.findByRestaurantId(id);
//        menuItems.add(new MenuItem("kimchi"));
//        restaurant.addAllMenuItem(menuItems);
//
//        List<Review> reviews = reviewRepository.findByRestaurantId(1L);
//        reviews.add(Review.builder().name("zoey").score(5).description("yummy").build());
//        restaurant.addAllReview(reviews);

        return restaurant;
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public Restaurant updateRestaurant(long id, String name, String address) {
        Restaurant restaurant = restaurantRepository.findById(1L).orElse(null);
        restaurant.updateInformation(name, address);
//        Restaurant restaurant = restaurantRepository.updateRestaurant(id, name, address);
        return restaurant;
    }
}
