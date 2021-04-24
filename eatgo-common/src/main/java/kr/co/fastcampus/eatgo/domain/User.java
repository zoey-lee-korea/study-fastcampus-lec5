package kr.co.fastcampus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String email;

    @NotEmpty
    private String name;

    @NotNull
    private Long level;

    private String password;

    private Long restaurantId; // 가게주인인 경우

    public boolean isAdmin() {
        return level >= 3;
    }

    public boolean isActive() {
        return level != 0;
    }

    public void deactivate() {
        level = 0L;
    }

    public boolean isOwner() {
        return level == 50L;
    }

    public void setRestaurantId(Long restaurantId) {
        this.level = 50L;
        this.restaurantId = restaurantId;
    }

    @JsonIgnore
    public String getAcessToken(){
        return password.substring(0, 10);
    }
}
