package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.ReservationService;
import kr.co.fastcampus.eatgo.domain.Reservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    public void create() throws Exception {
        Long restaurantId = 1L;
        Long userId = 161L;
        String name = "zoey";
        String date = "2021-01-01";
        String time = "14:00";
        Integer partySize = 4;
        Reservation reservation = Reservation.builder().id(1L)
                                                    .restaurantId(restaurantId).userId(userId)
                                                    .name(name).date(date).time(time)
                                                    .partySize(partySize).build();
        given(reservationService.addReservation(any(),any(),any(),any(),any(),any())).willReturn(reservation);


        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE2MSwibmFtZSI6InpvZXkifQ.5VV_FcKG8cditWqc5J8d5rshR5-zSoOcfx2tm_qxp5U";
        mvc.perform(post("/restaurants/1/reservations")
                .header("Authorization","Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"date\":\"2021-01-01\",\"time\":\"14:00\",\"partySize\":4}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location" ,"/restaurants/1/reservations/1"));
        verify(reservationService).addReservation(eq(restaurantId), eq(userId), eq(name), eq(date), eq(time), eq(partySize));
    }

}