package kr.ac.jejunu.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserDao userDao;
    @GetMapping("/user/{id}")
    public User get(@PathVariable("id")Integer id){
        return userDao.findById(id).get();
    }
}
