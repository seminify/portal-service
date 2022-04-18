package kr.ac.jejunu;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserDaoTests {
    private static UserDao userDao;

    @BeforeAll
    public static void setup() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        userDao = applicationContext.getBean("userDao", UserDao.class);
    }

    @Test
    public void findById() {
        Integer id = 1;
        String name = "hulk";
        String password = "1234";
        User user = userDao.findById(id);
        assertThat(user.getId(), is(id));
        assertThat(user.getName(), is(name));
        assertThat(user.getPassword(), is(password));
    }

    @Test
    public void insert() {
        String name = "hulk";
        String password = "1111";
        User user = User.builder().name(name).password(password).build();
        userDao.insert(user);
        assertThat(user.getId(), greaterThan(0));
        User insertedUser = userDao.findById(user.getId());
        assertThat(insertedUser.getName(), is(user.getName()));
        assertThat(insertedUser.getPassword(), is(user.getPassword()));
    }

    @Test
    public void update() {
        String updatedName = "허윤호";
        String updatedPassword = "1234";
//        사용자 추가;
        User user = User.builder().name("hulk").password("1111").build();
        userDao.insert(user);
//        수정
        user.builder().name(updatedName).password(updatedPassword).build();
        userDao.update(user);
        User updatedUser = userDao.findById(user.getId());
        assertThat(updatedUser.getName(), is(user.getName()));
        assertThat(updatedUser.getPassword(), is(user.getPassword()));
    }

    @Test
    public void delete() {
//        사용자 추가
        User user = User.builder().name("hulk").password("1234").build();
        userDao.insert(user);
//        삭제
        userDao.delete(user.getId());
        User deletedUser = userDao.findById(user.getId());
        assertThat(deletedUser, nullValue());
    }
}