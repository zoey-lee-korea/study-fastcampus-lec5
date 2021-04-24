package kr.co.fastcampus.eatgo.interfaces;

import io.jsonwebtoken.Claims;
import kr.co.fastcampus.eatgo.application.ReviewService;
import kr.co.fastcampus.eatgo.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/restaurant/{restaurantId}/reviews")
    public ResponseEntity<?> createReview(
            Authentication authentication, //우리가 이전에 만든 Authentication을 바~로 쓸 수 있다
            @PathVariable("restaurantId") Long restaurantId, @Valid @RequestBody Review resource) throws URISyntaxException {
        System.out.println("Authentication : " + authentication);

        Claims claims = (Claims) authentication.getPrincipal();
        String name = claims.get("name", String.class);

        Integer score = resource.getScore();
        String description = resource.getDescription();
        Review review = reviewService.addReview(restaurantId, name, score, description);

        String url = "/restaurant/" + restaurantId + "/review/" + review.getId();
        return ResponseEntity.created(new URI(url)).body("");
    }

}
