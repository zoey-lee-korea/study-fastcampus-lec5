package kr.co.fastcampus.eatgo.application;

public class EmailExistedException extends RuntimeException {

    EmailExistedException(String email) {
        super("email is already registed" + email);
    }
}
