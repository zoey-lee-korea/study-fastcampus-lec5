package kr.co.fastcampus.eatgo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id @GeneratedValue
    private Long id;

    private Long restaurantId;
    private Long userId;
    private String name;
    private String date;
    private String time;
    private Integer partySize;
}
