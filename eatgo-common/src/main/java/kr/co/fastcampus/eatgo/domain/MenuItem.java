package kr.co.fastcampus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity @Builder
@Getter
@NoArgsConstructor @AllArgsConstructor
public class MenuItem {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Setter
    private Long restaurantId;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean destory;

    public MenuItem(String name){
        this.name = name;
    }

}
