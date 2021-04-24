package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<User> list() {
        return userService.getUsers();
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody User resource) throws URISyntaxException {
        User user = userService.addUser(resource.getEmail(), resource.getName());
        URI uri = new URI("/users/" + user.getId());
        return ResponseEntity.created(uri).body("{}");
    }

    @PatchMapping("/users/{id}")
    public String update(@RequestBody User resource, @PathVariable("id") Long id) {
        User user = userService.updateUser(id, resource.getEmail(), resource.getName(), resource.getLevel());
        return "{}";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable Long id) {
        userService.deactivateUser(id);
        return "{}";
    }

}
