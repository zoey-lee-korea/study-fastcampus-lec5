package kr.co.fastcampus.eatgo.application;

public class EmailNotExistedException extends RuntimeException {

    EmailNotExistedException(String email) {
        super("email is not existed : " + email);
    }
}
