package lone.wolf.house.controller;

import lone.wolf.house.common.model.User;
import lone.wolf.house.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Description
 * @Author hechunhui
 * @CreatedBy 2018/7/26 17:53
 */
@Controller
public class HelloController {
    @Autowired
    private UserService userService;
    @RequestMapping("hello")
    public String hello(ModelMap modelMap){
        List<User> users = userService.getAllUsers();
        User user = users.get(0);
        modelMap.put("user",user);
        return "hellow";
    }
}
