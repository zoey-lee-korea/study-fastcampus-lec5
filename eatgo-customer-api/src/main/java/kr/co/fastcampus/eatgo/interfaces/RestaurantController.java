package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurant")
    public List<Restaurant> list(@RequestParam("region") String region, @RequestParam("category") Long categoryId) {
        return restaurantService.getRestaurants(region, categoryId);
    }


    @GetMapping("/restaurant/{id}")
    public Restaurant detail(@PathVariable("id") Long id) {
        return restaurantService.getRestaurantById(id);
    }
//
//    @PostMapping("/restaurants")
//    public ResponseEntity<?> create(@Valid @RequestBody Restaurant resource) throws URISyntaxException {
//
//        Restaurant restaurant = Restaurant.builder()
//                                        .name(resource.getName())
//                                        .address(resource.getAddress())
//                                        .build();
//        restaurantService.addRestaurant(restaurant);
//
//        URI location = new URI("/restaurant/" + restaurant.getId());
//        return ResponseEntity.created(location).body("{}");
//    }
//
//    @PatchMapping("/restaurant/{id}")
//    public String update(@PathVariable("id") Long id, @Valid @RequestBody Restaurant resource) {
//        restaurantService.updateRestaurant(id, resource.getName(), resource.getAddress());
//        return "{}";
//    }
}
