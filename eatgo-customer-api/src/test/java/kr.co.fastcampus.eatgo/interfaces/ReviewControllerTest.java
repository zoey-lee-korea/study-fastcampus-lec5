package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.ReviewService;
import kr.co.fastcampus.eatgo.domain.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {


    @Autowired
    private MockMvc mvc;
    @MockBean
    private ReviewService reviewService;

    @Test
    public void createWithValid() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE2MSwibmFtZSI6InpvZXkifQ.5VV_FcKG8cditWqc5J8d5rshR5-zSoOcfx2tm_qxp5U";

        given(reviewService.addReview(eq(1L), any(), any(), any())).willReturn(Review.builder().id(1L).build());
        mvc.perform(post("/restaurant/1/reviews")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"score\":5,\"description\":\"so delicious\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/restaurant/1/review/1"));

        verify(reviewService).addReview(eq(1L), any(), any(), any());
    }

    @Test
    public void createWithInvalid() throws Exception {
        mvc.perform(post("/restaurant/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());

        verify(reviewService, never()).addReview(eq(1L), any(), any(), any());
    }
}