package kr.co.fastcampus.eatgo.domain;

public class RestaurantNotFoundException extends RuntimeException {


    public RestaurantNotFoundException(long id) {
        super("Could not found restaurant id : " + id); 
        // 부모클래스인 RuntimeException에 보면 RuntimeException(메시지)를 넣을수 있는 형태가 있기 때문에 가능
    }
}
