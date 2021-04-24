package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String email, String name, String password) {
        //Email 유효성검사
        if (userRepository.findByEmail(email).isPresent()){
            throw new EmailExistedException(email);
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = User.builder().email(email).name(name).password(encodedPassword).level(1L).build();
        return userRepository.save(user);
    }

//    public User authenticate(String email, String password) {
//        User user = userRepository.findByEmail(email).orElseThrow(() -> new EmailNotExistedException(email));
//
//        if ( ! passwordEncoder.matches(password, user.getPassword())){
//            throw new PasswordWrongException();
//        }
//        return user;
//    }
}
