package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Reservation;
import kr.co.fastcampus.eatgo.domain.ReservationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class ReservationServiceTests {

    @Mock
    private ReservationRepository reservationRepository;

    private ReservationService reservationService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationService = new ReservationService(reservationRepository);
    }

    @Test
    public void getReservations() {

        List<Reservation> reservations = reservationService.getReservations(1L);

        verify(reservationRepository).findAllByRestaurantId(any());
    }

}