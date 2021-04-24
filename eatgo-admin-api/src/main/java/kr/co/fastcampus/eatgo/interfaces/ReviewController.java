package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.ReviewService;
import kr.co.fastcampus.eatgo.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/reviews")
    public List<Review> list() {
        return reviewService.getReviews();
    }

//    @PostMapping("/restaurant/{restaurantId}/reviews")
//    public ResponseEntity<?> createReview(@PathVariable("restaurantId") Long restaurantId, @Valid @RequestBody Review resource) throws URISyntaxException {
////        Review review = Review.builder()
////                .name(resource.getName())
////                .score(resource.getScore())
////                .description(resource.getDescription())
////                .build();
//        Review review = reviewService.addReview(restaurantId, resource);
//
//        String url = "/restaurant/" + restaurantId + "/review/" + review.getId();
//        return ResponseEntity.created(new URI(url)).body("");
//    }

}
