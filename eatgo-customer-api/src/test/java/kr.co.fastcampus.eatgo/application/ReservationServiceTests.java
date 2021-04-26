package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Reservation;
import kr.co.fastcampus.eatgo.domain.ReservationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


public class ReservationServiceTests {

    @Autowired
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationService = new ReservationService(reservationRepository);
    }

    @Test
    public void addReservation() {
        Long restaurantId = 1L;
        Long userId = 161L;
        String name = "zoey";
        String date = "2021-01-01";
        String time = "14:00";
        Integer partySize = 4;

        Reservation mockReservation = Reservation.builder().id(1L).restaurantId(restaurantId).userId(userId)
                .name(name).date(date).time(time).partySize(partySize).build();
        given(reservationRepository.save(any(Reservation.class))).willReturn(mockReservation);

        Reservation reservation = reservationService.addReservation(restaurantId,userId,name,date,time,partySize);
        assertThat(reservation.getName(), is("zoey"));
    }

}