package kr.co.fastcampus.eatgo.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@Getter
@NoArgsConstructor @AllArgsConstructor
public class Region {

    @Id
    @GeneratedValue
    private long id;

    private String name;

}
