package kr.co.fastcampus.eatgo.interfaces;

import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class SessionRequestDto {

    private String email;

    private String password;
}
