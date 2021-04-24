package kr.co.fastcampus.eatgo.application;

public class PasswordWrongException extends RuntimeException {

    PasswordWrongException() {
        super("password is wrong");
    }
}
