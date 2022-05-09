package kr.ac.jejunu;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class UserServlet extends GenericServlet {
    private UserDao userDao;
    @Override
    public void destroy() {
        System.out.println("********* destroy ***********");
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext("kr.ac.jejunu");
        this.userDao=applicationContext.getBean("userDao",UserDao.class);
        System.out.println("*********  init ***********");
        super.init();
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        User user=userDao.findById(1);
        res.setContentType("text/html;charset=UTF-8");
        String html = String.format("<html><h1>Hi %s</h1></html>",user.getName());
        res.getWriter().println(html);
        System.out.println("********* service ***********");
    }
}
