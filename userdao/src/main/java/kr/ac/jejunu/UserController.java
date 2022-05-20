package kr.ac.jejunu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping
    public ModelAndView user(@RequestParam("id") Integer id, @RequestParam("name") String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject(user);
        return modelAndView;
    }

    @GetMapping(value = "modelattribute")
    public String modelAttribute(User user) {
        user.setName("허윤호");
        return "forward:/upload";
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView error(Exception e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("e", e);
        return modelAndView;
    }

    @RequestMapping("/upload")
    public void upload() {
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public ModelAndView upload(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        File path = new File(request.getServletContext()
                .getRealPath("/") + "/WEB-INF/static/" + file.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(file.getBytes());
        bufferedOutputStream.close();
        ModelAndView modelAndView = new ModelAndView("upload");
        modelAndView.addObject("url"
                , "/images/" + file.getOriginalFilename());
        return modelAndView;
    }
}
