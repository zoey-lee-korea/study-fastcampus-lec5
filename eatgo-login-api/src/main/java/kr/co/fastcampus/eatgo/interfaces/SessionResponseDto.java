package kr.co.fastcampus.eatgo.interfaces;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionResponseDto { //Dto : 순수하게 데이터처리를 위해서만 쓰이는 클래스

    private String accessToken;

}
