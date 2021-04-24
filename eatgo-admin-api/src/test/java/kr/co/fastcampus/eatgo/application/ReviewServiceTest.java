package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Review;
import kr.co.fastcampus.eatgo.domain.ReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        reviewService = new ReviewService(reviewRepository);
    }

//    @Test
//    public void addReview() {
//        Review review = Review.builder()
//                                .name("zoey")
//                                .score(5)
//                                .description("delicious")
//                                .RestaurantId(1L)
//                                .build();
//        verify(reviewRepository).save(review);
//    }

    @Test
    public void getReview() {
        List<Review> mockReviews = new ArrayList<>();
        mockReviews.add(Review.builder().description("cool!").build());
        given(reviewRepository.findAll()).willReturn(mockReviews);

        List<Review> reviews = reviewRepository.findAll();
        assertThat(reviews.get(0).getDescription(), is("cool!"));

    }
}